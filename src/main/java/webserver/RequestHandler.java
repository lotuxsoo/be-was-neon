package webserver;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpStatus;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_PATH = "./src/main/resources/static";
    private static final String INDEX_FILE = "/index.html";
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            // 요청 헤더를 한 줄씩 읽어옴
            String requestHeader = br.readLine();
            logger.debug("requestHeader = {}", requestHeader);
            HttpRequest request = new HttpRequest(requestHeader);
            String path = request.getPath();

            if (path.contains("/create")) {
                User user = request.createUser();
                logger.debug("User: " + user);
                redirectToIndexPage(out); // 회원가입 후 index.html 페이지로 리다이렉트
               // return; // 리다이렉트 후 작업 종료
            } else if (!path.endsWith(".html")) {
                path += INDEX_FILE;
            }
            String filePath = DEFAULT_PATH + path;

            // 파일 내용을 읽어들임
            File file = new File(filePath);
            byte[] body;
            HttpStatus httpStatus;

            if (file.exists()) {
                body = readFileContent(file);
                httpStatus = HttpStatus.OK;
            } else {
                // 파일이 존재하지 않을 경우 404 에러 응답
                body = HttpStatus.NOT_FOUND.getMessage().getBytes(StandardCharsets.UTF_8);
                httpStatus = HttpStatus.NOT_FOUND;
            }
            logger.debug(httpStatus.getMessage(), filePath);
            // 클라이언트에게 응답을 전송
            DataOutputStream dos = new DataOutputStream(out);
            responseHeader(dos, body.length, httpStatus);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void redirectToIndexPage(OutputStream out) {
        try {
            String response = "HTTP/1.1 302 Found\r\n" +
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

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, HttpStatus httpStatus) {
        try {
            dos.writeBytes("HTTP/1.1" + " " + httpStatus.getCode() + " " + httpStatus.getMessage() + "\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}