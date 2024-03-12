package utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringUtilsTest {
    @Test
    @DisplayName("Header 첫번째 라인에서 요청 URL을 올바르게 추출한다.")
    void getUrl_test() throws Exception {
        String headers = "GET /index.html HTTP/1.1";
        String url = headers.split(" ")[1];
        assertThat(url).isEqualTo("/index.html");
    }

}