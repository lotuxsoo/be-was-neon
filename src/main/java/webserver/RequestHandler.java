package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            RequestReader reader = new RequestReader(in);
            //HttpRequest httpRequest = new HttpRequest(in);

            HttpResponse httpResponse = new HttpResponse();

            RequestHandlerMapper handlerMapper = new RequestHandlerMapper();

            RequestHandler handler = handlerMapper.getHandler(resource);

            handler.handle(httpRequest, httpResponse);

            httpResponse.send(out);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}