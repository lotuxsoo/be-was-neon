package webserver.handler;

import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticFileHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(StaticFileHandler.class);
    private static final String DEFAULT_PATH = "./src/main/resources/static";

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        File file = new File(DEFAULT_PATH + httpRequest.getPath());
        logger.debug(DEFAULT_PATH + httpRequest.getPath());
        if (file.exists() && !file.isDirectory()) {
            httpResponse.setStatus(HttpStatus.OK);

            String ext = httpRequest.getPath().split("\\.")[1];
            for (ContentType type : ContentType.values()) {
                if (type.getName().equals(ext)) {
                    String contentType = type.getContentType();
                    httpResponse.setContentType(contentType);
                }
            }

            byte[] body = readFileContent(file);
            httpResponse.setBody(body);
            httpResponse.setContentLength(body.length);
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND);
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
