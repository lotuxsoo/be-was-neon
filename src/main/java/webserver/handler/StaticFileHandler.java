package webserver.handler;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticFileHandler implements RequestHandler{
    private static final Logger logger = LoggerFactory.getLogger(StaticFileHandler.class);
    private static final String DEFAULT_PATH = "./src/main/resources/static";

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        File file = new File(DEFAULT_PATH + request.getUri());
        logger.debug(DEFAULT_PATH + request.getUri());

        if (file.exists() && !file.isDirectory()) {
            response.setStatus(HttpStatus.OK);
            String ext = request.getUri().split("\\.")[1];
            for (ContentType type : ContentType.values()) {
                if (type.getName().equals(ext)) {
                    String contentType = type.getContentType();
                    response.setContentType(contentType);
                }
            }

            byte[] body = readFileContent(file);
            response.setBody(body);
            response.setContentLength(body.length);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        logger.debug(response.getHeaders());
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
