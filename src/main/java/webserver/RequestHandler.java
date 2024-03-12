package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_PATH = "./src/main/resources/static";
    private Socket connection;

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

            // 요청 라인을 분리하여 파싱
            StringTokenizer tokenizer = new StringTokenizer(requestHeader);
            tokenizer.nextToken(); // "GET" 읽기
            String requestedFile = tokenizer.nextToken(); // 요청한 파일명 가져오기
            String filePath = DEFAULT_PATH + requestedFile;

            // 파일 내용을 읽어들임
            File file = new File(filePath);
            byte[] body;
            if (file.exists()) {
                body = Files.readAllBytes(file.toPath());
                logger.debug("File {} found.", filePath);
            } else {
                // 파일이 존재하지 않을 경우 404 에러 응답
                body = "File Not Found".getBytes(StandardCharsets.UTF_8);
                logger.debug("File {} not found.", filePath);
            }

            // 클라이언트에게 응답을 전송
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
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
