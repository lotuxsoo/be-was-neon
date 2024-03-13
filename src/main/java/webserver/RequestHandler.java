package webserver;

import db.Database;
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
import utils.StringUtils;

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
            String requestUrl = StringUtils.getUrl(requestHeader); // 요청 url (2번째 토큰)

            if (requestUrl.contains("?")) {
                String[] tokens = StringUtils.getTokens(requestUrl, "\\?");
                // userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
                String queryParams = tokens[1];
                String[] params = StringUtils.getTokens(queryParams, "&");
                String[] tempdb = new String[params.length];
                for (int i = 0; i < params.length; i++) {
                    String[] param = StringUtils.getTokens(params[i], "=");
                    tempdb[i] = param[1]; // param[1]이 User 객체의 정보
                }
                User user = new User(tempdb[0], tempdb[1], tempdb[2]); // id, nickname, password 저장
                Database.addUser(user);
                User userById = Database.findUserById(tempdb[0]);
                logger.debug("User: " + user.getName());
            } else if (!requestUrl.endsWith(".html")) {
                requestUrl += "/index.html";
            }

            String filePath = DEFAULT_PATH + requestUrl;

            // 파일 내용을 읽어들임
            File file = new File(filePath);
            byte[] body;
            if (file.exists()) {
                body = readFileContent(file);
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
