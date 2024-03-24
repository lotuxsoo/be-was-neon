package webserver;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String DEFAULT_PATH = "./src/main/resources/static/";
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = new HttpRequest(in);

            String method = httpRequest.getMethod();
            String resource = httpRequest.getResource();

            String contentType = "text/html";

            // 클라이언트에게 응답을 전송
            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse();

            if (method.equals("GET")) {
                // 파일 내용을 읽어들임
                File file = new File(DEFAULT_PATH + resource);
                if (file.exists() && !file.isDirectory()) {
                    String ext = resource.split("\\.")[1];
                    for (ContentType type : ContentType.values()) {
                        if (type.getName().equals(ext)) {
                            contentType = type.getContentType();
                        }
                    }
                    byte[] body = readFileContent(file);
                    httpResponse.response200Header(dos, body, contentType);
                } else {
                    httpResponse.response404Header(dos);
                }
            } else if (method.equals("POST") && resource.startsWith("/create")) {
                httpResponse.response302Header(dos);
            } else if (method.equals("POST") && resource.startsWith("/login")) {
                String sessionId = "";
                httpResponse.response302HeaderWithCookie(dos, sessionId);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] readFileContent(File file) {
        byte[] buffer = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return buffer;
    }
}