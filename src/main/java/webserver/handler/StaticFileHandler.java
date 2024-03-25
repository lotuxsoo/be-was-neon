package webserver.handler;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StaticFileHandler implements RequestHandler{
    private static final String DEFAULT_PATH = "src/main/resources/static/";
    private static final String INDEX_FILE = "index.html";
    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        File file = new File(DEFAULT_PATH + INDEX_FILE);

        if (file.exists() && !file.isDirectory()) {
            String ext = request.getUri().split("\\.")[1];
            for (ContentType type : ContentType.values()) {
                if (type.getName().equals(ext)) {
                    String contentType = type.getContentType();
                    response.setContentType(contentType);
                }
            }
            byte[] body = readFileContent(file);
            response.setContentLength(body.length);
            response.setStatus(HttpStatus.OK);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND);
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
