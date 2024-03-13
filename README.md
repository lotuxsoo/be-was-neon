# be-was-2024

코드스쿼드 백엔드 교육용 WAS 2024 개정판

## 웹서버 1단계 - index.html 응답

### 정적인 html 파일 응답

- InputStreamReader, BufferedReader의 readLine()을 통해서 Request Header를 한줄씩 읽어온다.
- 한줄씩 읽어온 Request Header를 logger.debug를 이용하여 출력한다.
- StringTokenizer를 이용하여 토큰을 분리하고 /index.html을 가져온다.
- 파일이 존재하면 byte[]에 파일을 읽어오고, 없으면 에러를 출력한다.
- DataOutputStream을 통해 클라이언트에 응답을 전송한다.

### Concurrent 패키지 사용

- 요청이 들어올 때마다 새로운 스레드를 생성하는 것보다 스레드 풀을 사용하여 스레드를 재사용하는 것이 효율적이다.
- Java의 Executors 클래스를 사용하여 스레드 풀을 생성하고, ExecutorService를 통해 스레드 풀을 관리한다.
- newFixedThreadPool(int nThreads): 지정된 수의 스레드로 고정된 크기의 스레드 풀을 생성한다. 고정된 수의 스레드가 유지된다.
- execute(Runnable command)를 통해 작업을 제출한다. execute()는 Runnable 인터페이스를 구현한 작업을 제출한다.
- shutdown()을 통해 ExecutorService를 종료할 때까지 대기한다. 현재 대기 중인 작업을 완료한 후 ExecutorService를 종료한다.

### InputStream, OutputStream

- Socket 클래스의 getInputStream(), getOutputStream(): 소켓을 통해 데이터를 주고받기 위한 스트림을 가져오기 위해 사용한다.
- FileInputStream, FileOutputStream
- DataOutputStream

### Logger

1. **logger.debug**:

- 디버깅 목적으로 상세한 정보를 기록할 때 사용됩니다.
- 주로 개발 및 테스트 단계에서 사용된다.

2. **logger.info**:

- 애플리케이션의 실행 상태와 주요 이벤트에 대한 정보를 기록할 때 사용된다.
- 일반적으로 프로덕션 환경에서 사용된다.

3. **logger.error**:

- 에러와 예외 정보를 기록하고 처리할 때 사용된다.
- 프로그램이 비정상적인 상황에 진입했을 때 사용된다.

## 웹서버 2단계 - GET으로 회원가입

- HTTP 메시지 시작 줄과 HTTP 헤더를 묶어서 '요청 헤드'라고 부른다.
- 시작 줄에는 **HTTP 메서드(GET, POST, PUT, DELETE)**와 **요청 타겟(URL)**, 응답 메시지에서 써야 할 **HTTP 버전**으로 이루어져 있다.

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