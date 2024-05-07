# be-was-2024

코드스쿼드 백엔드 교육용 WAS 2024 개정판
<br/><br/>
## ✅ 구현 내용
# 웹서버 1단계 - index.html 응답
### Java Concurrent 패키지를 사용해서 멀티스레딩 구현
- 고수준 스레드 관리: Executor, ThreadPoolExecutor 등의 클래스를 제공하여 스레드 풀을 관리하고 스레드 생성 및 관리를 추상화한다. 개발자가 직접 스레드를 생성하고 관리할 필요가 없다.
- 제어권이 라이브러리에 있음: 스레드 풀을 통해 스레드의 생명주기와 동작을 라이브러리가 관리하므로, 개발자는 비즈니스 로직에 집중할 수 있다.
- 간단하고 안전한 사용: 여러가지 동시성 문제를 해결하기 위한 다양한 클래스와 메서드를 제공하여 코드의 복잡성을 줄이고 안전성을 높인다.
~~~
// ExecutorService를 사용하여 스레드 풀을 10개 생성한다.
ExecutorService executorService = Executors.newFixedThreadPool(10);
~~~

### HTTP 요청 메시지를 적절하게 파싱해서 log.debug로 출력
- 클라이언트로부터 HTTP 요청은 InputStream을 통해 받고, InputStream => InputStreamReader => BufferedReader 래핑을 거친다.
- BufferedReader의 readLine()으로 한 줄씩 데이터를 읽어들이고, HTTP 메시지의 헤더와 바디는 빈줄(CRLF)로 구분되므로, 이를 기준으로 구분하여 처리한다.
- 각 헤더는 헤더 이름과 값으로 이루어져 있으며 콜론(:)으로 구분되고, 바디는 Content-Length의 길이만큼 읽어들인다.
- 읽은 내용을 적절하게 파싱해서 HttpRequest 객체로 만든 후, log.debug를 이용해 출력한다.
~~~
23:29:55.420 [DEBUG] [pool-1-thread-1] [webserver.request.HttpRequest] - [Request-Line] httpMethod='GET', requestPath='/', httpVersion='HTTP/1.1'
23:29:55.423 [DEBUG] [pool-1-thread-1] [webserver.request.HttpRequest] - [Request-Header]
Host: localhost:8080
Connection: keep-alive
sec-ch-ua: "Chromium";v="124", "Google Chrome";v="124", "Not-A.Brand";v="99"
sec-ch-ua-mobile: ?0
sec-ch-ua-platform: "macOS"
...
~~~

### 정적인 html 파일 응답하기
- Java NIO의 기능인 Files 클래스의 readAllBytes()를 사용하지 않고 구현한다.
- 파일의 내용을 byte 단위로 읽기 위해 FileInputStream의 read()를 사용한다.
- fis.read(bytesArray)는 FileInputStream이 열려있는 파일에서 byte를 읽어 bytesArray에 연속적으로 저장한다.
~~~
public static byte[] getFileBytes(String requestPath) throws IOException {
    File file = new File(DEFAULT_PATH.getPath() + requestPath);
    if (!file.exists() || !file.isFile()) {
        return null;
    }

    byte[] bytesArray = new byte[(int) file.length()];
    try (FileInputStream fis = new FileInputStream(file)) {
        fis.read(bytesArray);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    return bytesArray;
}
~~~

# 웹서버 2단계 - 다양한 컨텐츠 타입 지원
- 다양한 Content-Type을 처리하기 (html,css,js,ico,png,jpg,svg)
- Content-Type은 파일의 확장자를 통해 구분할 수도 있고, 요청 헤더의 Accept를 활용할 수도 있다.
- MimeType ENUM을 만들고, 요청 url의 확장자를 확인해서 HTTP 응답 헤더에 Content-Type 필드를 지정해주었다.

# 웹서버 3단계 - GET으로 회원가입
- HTTP GET 프로토콜에 대해 이해하기
- GET으로 회원가입 기능 구현하기
- Header 첫번째 라인에서 요청 URL 추출하기
- Request Parameter를 추출해서 모델 데이터에 담기
- AssertJ로 테스트코드 작성하기
- HTTP Status 코드 알아보기

### HTTP Status

- 200 OK: 요청이 성공적으로 되었습니다.
- 404 NOT FOUND: 서버는 요청받은 리소스를 찾을 수 없습니다. 브라우저에서는 알려지지 않은 URL을 의미합니다.
- 301 Moved Permanently (또는 308 Permanent Redirect): 리소스의 위치가 영구적으로 변경되었을 때 사용됩니다.
- 302 Found (또는 307 Temporary Redirect): 리소스의 위치가 일시적으로 변경되었을 때 사용됩니다.
- HTTP 응답 헤더에 Location 헤더의 값을 통해 클라이언트가 이동해야할 위치를 나타낸다.

# 웹서버 4단계 - POST로 회원가입
- HTML form 태그의 method를 get에서 post로 수정하기
- POST method로 데이터를 전달할 경우 전달하는 데이터는 HTTP Body에 담긴다.
- 가입 후 페이지 이동을 위해 HTTP redirection 기능 구현하기

# 웹서버 5단계 - 쿠키를 이용한 로그인
- 가입한 회원정보로 로그인이 성공하면 HTTP 헤더의 쿠키값을 **SID=세션ID**로 응답한다.
- 로그인이 성공하면 응답 헤더의 Set-Cookie를 설정하고, 요청 헤더에 Cookie가 전달되는지 확인한다.
- 서버는 세션ID에 해당하는 User 정보에 접근할 수 있어야 한다.
