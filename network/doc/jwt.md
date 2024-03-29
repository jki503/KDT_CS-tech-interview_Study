---
Title: JWT
Category: Network
Author: Jung
---

> JWT는 유저를 식별하기 위한 Token 기반 인증  
> 토큰은 세션과 달리 서버가 아닌 클라이언트에 저장  
> 토큰 자체에 사용자의 권한 정보나 서비스를 사용하기 위한 정보가 포함

</br>

## JWT 방식과 쿠키/세션 방식의 차이

> 세션/쿠키는 저장소에 유저의 정보를 넣는 반면,  
> JWT는 토큰 안에 유저의 정보를 넣는다는 점이 가장 큰 차이
>
> HTTP 헤더에 세션 ID나 토큰을 실어서 보내는 점은 동일,  
> 서버측에서 인증을 위해 `암호화 하느냐, 별도의 저장소를 이용하느냐 라는 차이`

</br>

## JWT 진행 순서

- 1. 클라이언트 사용자가 아이디, 패스워드 통해 웹서비스 인증
- 2. 서버에서 singed 된 JWT 생성하여 클라이언트에 응답으로 돌려주기
- 3. 클라이언트가 서버에 데이터를 추가적으로 요구할때 HTTP 헤더에 JWT 첨부
- 4. 서버에서 클라이언트로 온 JWT를 검증

</br>

## JWT 구조

> Header, Payload, Signature로 구성되고 .으로 구분

- 1. HEADER : JWT에서 사용할 타입과 해시 알고리즘 종류
  - typ : 토큰 타입
  - alg : 해싱 알고리즘
    - 주로 HMAC, SHA256, RSA 사용
    - 서명에서 이 알고리즘을 사용
- 2. PAYLOAD : 서버에 첨부한 사용자 권한 정보와 데이터
- 3. SIGNATURE : HEADER, PAYLOAD를 Base64-URL-safe-Encoded 한 후, Header에 명시된 해시 함수를 적용하고, 개인키로 서명한 전자서명이 담겨있음

</br>

- ex : 전자 서명 알고리즘으로 타원 곡선 암호화(ECDSA) 사용

> SIG = ECDSA(SHA256(B64(HEADER).B64(PAYLOAD)),PRIVATE KEY)  
> JWT = B64(HEADER).B64(PAYLOAD).B64(SIG)  
> 전자 서명에는 `비대칭 알고리즘을 사용`  
> 따라서, 암호화를 위한 키와 복호화를 위한 키가 다름  
> 암호화 -> 개인키, 복호화 -> 공개키

### 클레임

- 등록된 클레임 : 토큰에 정보들을 담기 위하여 이름이 이미 정해진 클레임들.

  - iss : 토큰 발급자
  - sub : 토큰 제목
  - aud : 토큰 대상자
  - exp : 토큰 만료시간
  - nbf : Not before, 해당 날짜가 지나기 전까지 토큰 처리 X
  - iat : 토큰 발급된 시간, 이 값을 통해 `토큰 age 판단`
  - jti : JWT의 고유 식별자, 중복 처리 방지

- 공개 클레임 : 충동이 방지된 이름을 가지고 있어야함, uri 형식 네이밍

  - ex -> "https://xxxx.com/jwt_claims/is_admin" : true

- 비공개 클레임 : 클라이언트 <-> 서버간의 협의하에 따라 사용, 이름이 중복 되어 충돌 가능성.
  - ex -> "username" : "xxxx"

</br>

### 헤더 페이로드 유의 사항

- 페이로드에 민감한 정보는 담지 않아야한다.
  - header와 payload는 json이 인코딩 되어있을 뿐 특별한 암호화가 걸려있는 것이 아니기 때문에 jwt를 가지고 코딩하면 payload에 담긴 값을 알 수 있다.
  - JWT는 단순히 식별을 하기 위한 정보만을 담아야한다.

## 장점

- 별도의 세션 저장소 관리를 필요하는 세션/쿠키 보다 간편
  - 토큰 자체에 정보가 포함
  - JWT는 유효성과 sign을 이용하여 비교적 간단하게 사용 가능
- 확장성 측면에서 유리
  - 클라이언트 단에 저장 되어 무상태성이 가능하기 때문
  - 토큰 기반으로 인증하는 다른 인증시스템에도 접근 가능

</br>

## 단점

- 페이로드에 담긴 정보는 암호화 된 것이 아니기때문에 중요 데이터 넣으면 안된다.
  - 유효기간을 짧게, refresh 토큰 추가
- 페이로드 크기가 커지면, 서버에 부하를 줄 수 있음
- 토큰 기반 시스템은 무상태성 : 한 번 만들어지면 제어 불가능
  - 토큰 임의의 삭제 불가능, exp가 지나야 만료된다.
  - Access 토큰의 유효기간을 짧게 만들고, refresh token을 새로 발급
