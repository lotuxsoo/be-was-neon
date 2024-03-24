package webserver.handler;

import http.ContentType;
import http.HttpRequest;
import http.HttpResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HomeHandler implements RequestHandler {
    private static final String DEFAULT_PATH = "./src/main/resources/static/";

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        String resource = request.getResource();
        File file = new File(DEFAULT_PATH + resource);

        String contentType = "text/html";

        if (file.exists() && !file.isDirectory()) {
            String ext = resource.split("\\.")[1];
            for (ContentType type : ContentType.values()) {
                if (type.getName().equals(ext)) {
                    contentType = type.getContentType();
                }
            }
            byte[] body = readFileContent(file);
            response.response200Header(dos, body, contentType);
        } else {
            response.response404Header(dos);
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
