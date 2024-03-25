package http;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    public void response200Header(DataOutputStream dos, byte[] body,
                                  String contentType) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK\r\n");
        dos.writeBytes("Content-Type: " + contentType + "\r\n");
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void response302Header(DataOutputStream dos) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found\r\n");
        dos.writeBytes("Location: /index.html");
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void response404Header(DataOutputStream dos) throws IOException {
        String message = "404 Not Found";
        dos.writeBytes("HTTP/1.1" + message + "r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + message.length() + "\r\n" +
                "\r\n" +
                message);
        dos.flush();
    }
}
