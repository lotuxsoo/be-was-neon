package http;

public enum HttpStatus {
    OK(200, "OK"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    NOT_FOUND(404, "Not Found");

    private int code;
    private String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
