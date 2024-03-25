package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        // ExecutorService를 사용하여 스레드 풀을 생성한다.
        ExecutorService executorService = Executors.newFixedThreadPool(100); // 최대 스레드 수:100

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                // 요청 처리를 담당할 Runnable 객체를 생성하여 스레드 풀에 제출한다.
                executorService.execute(new MainHandler(connection));
            }
        } finally {
            // 프로그램 종료 시 스레드 풀을 종료한다.
            executorService.shutdown();
        }
    }
}