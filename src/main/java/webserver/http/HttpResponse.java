package webserver.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String CRLF = "\r\n";
    private static final String SP = " ";
    private final Map<String, String> headers;
    private byte[] body;

    public HttpResponse() {
        headers = new LinkedHashMap<>();
        body = new byte[0];
    }

    public void setStatus(HttpStatus status) {
        headers.put("Start-Line", "HTTP/1.1 " + status.getCode() + SP + status.getMessage() + CRLF);
    }

    public void setContentType(String contentType) {
        headers.put("Content-Type", "Content-Type: " + contentType + CRLF);
    }

    public void setContentLength(int contentLength) {
        headers.put("Content-Length", "Content-Length: " + contentLength + CRLF);
    }

    public void setLocation(String location) {
        headers.put("Location", "Location: " + location + CRLF);
    }

    public void setCookie(String cookie) {
        headers.put("Cookie", "Set-Cookie: " + cookie + SP + "Path=/" + CRLF);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getHeaders() {
        StringBuilder sb = new StringBuilder();
        headers.keySet().forEach(key -> sb.append(headers.get(key)));
        logger.debug("응답 헤더: {}", sb);
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
}
