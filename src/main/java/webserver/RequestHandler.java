package webserver;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String DEFAULT_PATH = "./src/main/resources/static/";
    private static final String INDEX_FILE = "index.html";
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
            String path = httpRequest.getPath();

            ContentType[] values = ContentType.values();
            String contentType = "text/html";

            if (path.contains("/create")) {
                User user = httpRequest.createUser();
                logger.debug(user.toString());
                redirectToIndexPage(out); // index.html 페이지로 리다이렉트
                return;
            } else {
                String ext = path.split("\\.")[1];
                for (ContentType type : values) {
                    if (type.getName().equals(ext)) {
                        contentType = type.getContentType();
                    }
                }
            }

            String filePath = DEFAULT_PATH + path;

            // 파일 내용을 읽어들임
            File file = new File(filePath);
            byte[] body;
            HttpStatus httpStatus;

            if (file.exists()) {
                httpStatus = HttpStatus.OK;
                body = readFileContent(file);
            } else {
                httpStatus = HttpStatus.NOT_FOUND;
                body = httpStatus.getMessage().getBytes(StandardCharsets.UTF_8);
            }
            logger.debug(httpStatus.getMessage(), filePath);

            // 클라이언트에게 응답을 전송
            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.responseHeader(dos, body.length, httpStatus, contentType);
            httpResponse.responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void redirectToIndexPage(OutputStream out) {
        try {
            String response = "HTTP/1.1 307 Temporary Redirect\r\n" +
                    "Location: " + INDEX_FILE + "\r\n" +
                    "\r\n";

            out.write(response.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            logger.error("Error while redirecting: {}", e.getMessage());
        }
    }

    private byte[] readFileContent(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }

}