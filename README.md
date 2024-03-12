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
