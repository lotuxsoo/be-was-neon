package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String CRLF = "\r\n";
    private static final String SP = " ";
    private static final String HTTP_VERSION = "HTTP/1.1 ";
    private Map<String, String> headers;
    private byte[] body;

    public HttpResponse() {
        headers = new HashMap<>();
        body = new byte[0];
    }

    public void addStatus(HttpStatus status) {
        headers.put("Start-Line", HTTP_VERSION + status.getCode() + SP + status.getMessage() + CRLF);
    }

    public void addContentType(String contentType) {
        headers.put("Content-Type", "Content-Type: " + contentType + CRLF);
    }

    public void addContentLength(int contentLength) {
        headers.put("Content-Length", "Content-Length: " + contentLength + CRLF);
    }


    public void addLocation(String location) {
        headers.put("Location", "Location: " + location + CRLF);
    }

    public void addCookie(String cookie) {
        headers.put("Cookie", "Set-Cookie: " + cookie + " Path=/" + CRLF);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getHeaders() {
        StringBuilder sb = new StringBuilder();
        headers.keySet().forEach(key -> sb.append(headers.get(key)));
        return sb.toString();
    }

    public void send(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            dos.writeBytes(getHeaders());
            dos.writeBytes(CRLF);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /*
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

    public void response302HeaderWithCookie(DataOutputStream dos, String sessionId) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found\r\n");
        dos.writeBytes("Location: /index.html");
        dos.writeBytes("Set-Cookie: sid=" + sessionId + ";" + "Path=/");
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void response404Header(DataOutputStream dos) throws IOException {
        String message = "404 Not Found";
        dos.writeBytes("HTTP/1.1 404 Not Found\r\n");
        dos.writeBytes("Content-Type: text/plain\r\n");
        dos.writeBytes("Content-Length: " + message.length() + "\r\n");
        dos.writeBytes(message);
        dos.flush();
    }
    */
}
