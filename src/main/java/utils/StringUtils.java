package utils;

public class StringUtils {
    public static String getUrl(String headers) {
        String[] tokens = headers.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("[ERROR] Invalid headers");
        }
        return tokens[1];
    }
}