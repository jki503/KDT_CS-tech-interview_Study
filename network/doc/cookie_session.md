# 쿠키와 세션

> HTTP는 **비상태성(Stateless) 프로토콜**로 상태 정보를 유지 X  
> → 앞선 연결을 현재의 연결이 기록하지 않는다.  
> **비연결지향(Connectionless)** 프로토콜로 → 응답을 보내며 연결을 해제  
> 매 요청마다 사용자를 확인하는 과정이 필요  
> 이러한 문제를 해결하기 위해 쿠키와 세션이 등장

</br>

## Cookie

- **Client 단에 저장되는 정보 (웹 브라우저에 저장)**
- 사용자가 별도의 요청을 하지 않아도 브라우저는 request시 **Request Header**에 넣어 자동으로 서버에 전송
- Browser마다 저장되는 쿠키가 다름
  - Browser가 다르면 다른 사용자로 인식
  - 크롬에서 생성한 쿠키를 IE에서 사용할 수 없음
- 서버에서 사용자의 컴퓨터에 저장하는 **정보파일**
  - **⇒** key, value쌍의 **String** 형태
- 사용 목적
  - 세션 관리: 사용자 아이디, 접속시간, 장바구니 등의 서버가 알아야 할 정보 저장
  - 개인화: 맞춤 광고와 같이 개인에 맞춘 페이지
  - 트래킹: 사용자의 행동과 패턴을 분석하고 기록
    매번 로그인 없이 페이지를 돌아다닐 수 있는 이유는 사용자 정보를 유지해주는 쿠키 덕분
- 구성 요소
  - **key - value**
  - 유효기간
  - 도메인: 어떤 서버로 전송되어야 하는지를 지정
    - 현재 서버의 주소 혹은 상위 도메인 지정 가능
    - 따로 설정하지 않으면 생성된 서버로 설정됨
  - 경로: 쿠키를 전송할 요청 경로
    → `cookie.setPath("/user")` : /user로 요청시 쿠키 전송
- 동작 순서
  - Client가 페이지를 요청
  - WAS는 Cookie를 생성
  - HTTP Header에 Cookie를 넣어 응답
  - Browser는 넘겨받은 Cookie를 PC에 저장, 요청시 Cookie와 함께 전송
  - Browser가 종료되어도 만료기간이 남아있다면 Cookie 보관되어, 동일 사이트 재방문시 요청페이지와 쿠키 함께 전송 (Request header에 담아서 전송)
- 특징
  - 클라이언트에 총 300개의 쿠키 저장 가능
  - 하나의 도메인 당 20개의 쿠키 가질 수 있다
  - 하나의 쿠키에 4KB까지 저장 가능
- 단점
  - 방문한 웹 사이트에 대한 개인정보가 기록되기 때문에 사생활 침해의 소지가 있음 → 쿠키 거부 기능 제공
  - 클라이언트 단에 저장되는 정보로 보안 취약 → 대안으로 세션이 등장하였고, 취약한 정보를 담는 것은 위험
  - 세션 하이재킹의 위험이 있음
    이미 인증되어 발급 상태인 세션 식별자(SESSION-ID)를 가로채 인증 없이 사용자의 권한으로 서버에 접근하는 것 → 데이터 트래픽 암호화 (https)

</br>

- javax.servlet.http.Cookie
  - 생성: `Cookie cookie = new Cookie(String name, String value)`
  - 값 변경/얻기: `cookie.setValue(String value)/ cookie.getValue();`
  - 도메인, 경로 변경/얻기
  - 유효기간 지정/얻기: `cookie.setMaxAge(0);//삭제 cooke.getMaxAge();`
  - 생성된 cookie를 client에 전송: `response.addCookie(cookie);`
  - client에 저장된 cookie 얻기: `Cookie[] cookies[] = request.getCookies();`

</br>

## Session

> 사용자 정보파일을 브라우저가 아닌 서버측에서 관리

- **WAS의 memory에 Object의 형태**로 저장
- 동작 순서
  1. 클라이언트가 페이지를 요청
  2. 서버는 접근한 클라이언트의 Request-Header 필드인 Cookie를 확인하여, 클라이언트가 해당 session-id를 보냈는지 확인
  3. session-id가 존재하지 않는다면, 서버는 session-id를 생성해 클라이언트에게 돌려준다
  4. 서버에서 클라이언트로 돌려준 session-id를 쿠키를 사용해 서버에 저장 (JSESIONID: 세션 ID를 저장하는 쿠키 이름)
  5. 클라이언트는 재접속 시, JSESSIONID를 이용하여 session-id 값을 서버에 전달
- 특징
  - 웹 서버에 저장되는 쿠키 (=세션 쿠키)
  - 브라우저를 닫거나, 서버에서 세션을 삭제 했을 때 삭제가 되므로, 쿠키보다 보안이 좋다
  - 저장 데이터에 제한이 없다 (Object 형태로 저장, 메모리 공간만큼 저장 가능)
  - 동접자 수가 많으면 서버 메모리 과부하 가능성
  - **각 클라이언트에 고유 Session ID를 부여**하여 각 클라이언트 요구에 맞는 서비스 제공
  - 세션 유지 시간은 web.xml에서 설정 가능하고, 보통 기본 30분으로 설정되어 있다
- HttpSession
  - 생성: `HttpSession session = request.getSession();`
    → `getSession() == getSession(true)` : session-id가 존재하지 않는다면 새로 session을 생성하여 반환
    → `getSession(false)` : session-id가 존재하지 않는다면, null 반환
  - 값 저장: `session.setAttribute(String name, Object Value);`
  - 값 얻기: `Object obj = session.getAttribute(String name);`
  - 값 제거: `session.removeAttribute(String name), session.invalidate();`

</br>

## Cookie와 Session의 차이

- 쿠키는 만료시간이 지날때까지 파일 형태로 정보 저장 반면, 세션은 만료 시간은 서버측에서 설정하고 브라우저가 닫히면 만료시간에 상관없이 삭제
- 쿠키는 클라이언트 로컬에 저장되어 보안에 취약하지만, 세션은 sessionid만 쿠키에 저장하기 때문에 보안성이 더 좋음
- 세션은 서버의 처리가 필요하기 때문에 쿠키보다 속도가 느림
- 세션을 많이 사용하면 서버의 메모리가 감당할 수 없어질 때가 있고 속도가 느려질 수 있음

</br>

## 쿠키와 세션 사용 예

- 쿠키

  - 방문 사이트에서 로그인 시, "아이디와 비밀번호를 저장하시겠습니까?"
  - 쇼핑몰의 장바구니 기능
  - 자동 로그인, 팝업에서 "오늘 더이상 이창을 보지않음"
  - 민감한 데이터 및 보안과 연관되지 않을 경우

- 세션
  - 로그인과 같은 보안상의 중요한 작업 수행할 때 사용

</br>

## 라이프 사이클

</br>

- 쿠키

  - 만료시간이 있지만, 파일로 저장되어 브라우저를 종료해도 계속해서 정보 남아있을 수 있다.

- 세션

  - 세션도 만료시간이 있으나, 브라우저를 종료하면 삭제 된다.
  - 크롬에서 여러 탭으로 여러 계정으로 로그인 할 경우, 마지막 로그인으로 덮어 쓰게 된다.

- 캐시

  - 캐시는 이미지, css, js 등을 브라우저나 서버 앞단에 저장해놓고 사용
  - 캐시는 수동으로 지우거나,
  - 혹은 서버에서 클라이언트로 응답을 보낼 때 header에 캐시 만료 시간을 명시하는 방법.
