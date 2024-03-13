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

    @Test
    @DisplayName("회원정보를 입력받아 쿼리 파라미터를 올바르게 가져온다.")
    void getQueryParams_test() throws Exception {
        String requestUrl = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        String[] split = requestUrl.split("\\?");
        assertThat(split[0]).isEqualTo("/user/create");

        String queryParams = split[1];
        int length = queryParams.split("&").length;
        assertThat(length).isEqualTo(4);
    }

}