# be-was-2024

코드스쿼드 백엔드 교육용 WAS 2024 개정판

## 웹서버 1단계 - index.html 응답

### 정적인 html 파일 응답

- InputStreamReader, BufferedReader의 readLine()을 통해서 Request Header를 한줄씩 읽어온다.
- 한줄씩 읽어온 Request Header를 logger.debug를 이용하여 출력한다.
- split()을 사용하여 두번째 토큰인 /index.html을 가져온다.
- 파일이 존재하면 byte[]에 파일을 읽어오고, 없으면 에러를 출력한다.
- DataOutputStream을 통해 클라이언트에 응답을 전송한다.

### Concurrent 패키지 사용

- 요청이 들어올 때마다 새로운 스레드를 생성하는 것보다 스레드 풀을 사용하여 스레드를 재사용하는 것이 효율적이다.
- Java의 Executors 클래스를 사용하여 스레드 풀을 생성하고, ExecutorService를 통해 스레드 풀을 관리한다.
- newFixedThreadPool(int nThreads): 지정된 수의 스레드로 고정된 크기의 스레드 풀을 생성한다. 고정된 수의 스레드가 유지된다.
- execute(Runnable command)를 통해 작업을 제출한다. execute()는 Runnable 인터페이스를 구현한 작업을 제출한다.
- shutdown()을 통해 ExecutorService를 종료할 때까지 대기한다. 현재 대기 중인 작업을 완료한 후 ExecutorService를 종료한다.

## 웹서버 2단계 - GET으로 회원가입

### Request Header
- HTTP 메시지 시작 줄과 HTTP 헤더를 묶어서 요청 헤더라고 부른다.
- 시작 줄은 **HTTP 메서드(GET, POST, PUT, DELETE)**와 **요청 타겟(URL)**, 응답 메시지에서 써야 할 **HTTP 버전**으로 이루어져 있다.

### Request Parameter 추출하기
- Header의 첫 번째 라인에서 요청 URL을 추출하기
- 요청 URL에서 접근 경로와 이름=값을 추출해 User 클래스에 담기

### HTTP GET 프로토콜
- 서버로부터 데이터를 받아올때 사용
- 전달되는 파라미터가 url 경로상에 보임
- URL 뒤에 ?와 함께 데이터를 연결하여 전송함

### HTTP Status
200 OK: 요청이 성공적으로 되었습니다.
- GET: 리소스를 불러와서 메시지 바디에 전송되었습니다. 
- PUT 또는 POST: 수행 결과에 대한 리소스가 메시지 바디에 전송되었습니다.
404 NOT FOUND: 서버는 요청받은 리소스를 찾을 수 없습니다. 브라우저에서는 알려지지 않은 URL을 의미합니다.

## 웹 서버 3단계 - 다양한 컨텐츠 타입 지원

### HTTP Response

### MIME 타입

