package http;

import java.io.DataOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void responseHeader(DataOutputStream dos, int lengthOfBodyContent, HttpStatus httpStatus,
                               String contentType) {
        try {
            dos.writeBytes("HTTP/1.1" + " " + httpStatus.getCode() + " " + httpStatus.getMessage() + "\r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
