# be-was-2024

코드스쿼드 백엔드 교육용 WAS 2024 개정판

## 웹서버 1단계 - index.html 응답
- HTTP Request 내용 로거를 이용해서 출력하기
- http://localhost:8080/index.html 에 접근하면 정적인 html 파일 응답하기
- Concurrent 패키지를 사용하여 멀티 스레드 적용하기

## 웹서버 2단계 - GET으로 회원가입
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

## 웹서버 3단계 - 다양한 컨텐츠 타입 지원
- 다양한 Content-Type을 처리하기 (html,css,js,ico,png,jpg,svg)
- HTTP Response 헤더의 Content-Type을 text/html로 보내면 클라이언트는 html 파일로 인식한다.
- Content-Type은 확장자를 통해 구분할 수도 있고, Request Header의 Accept를 활용할 수도 있다.

## 웹서버 4단계 - POST로 회원가입
- HTML form 태그의 method를 get에서 post로 수정하기
- POST method로 데이터를 전달할 경우 전달하는 데이터는 HTTP Body에 담긴다.
- 가입 후 페이지 이동을 위해 HTTP redirection 기능 구현하기