---
Title: Load Balancing
Category: Network
Author: Jung
---

## 로드 밸런서 (Load Balancer)

- 로드밸런서는 **서버에 가해지는 부하( = Load )를 분산( = Balancing )해주는 장치 또는 기술**을 통칭한다.
- **클라이언트와 서버풀(Server Pool, 분산 네트워크를 구성하는 서버들의 그룹) 사이에 위치**하며, 한 대의 서버로 부하가 집중되지 않도록 트래픽을 관리해 각각의 서버가 최적의 퍼포먼스를 보일 수 있도록 한다.

## 로드 밸런싱이 필요한 경우?

> 로드밸런싱은 여러 대의 서버를 두고 서비스를 제공하는 **분산 처리 시스템에서 필요한 기술**입니다.  
> 서비스의 제공 초기 단계라면 적은 수의 클라이언트로 인해 서버 한 대로 요청에 응답하는 것이 가능합니다.  
> 하지만 사업의 규모가 확장되고 클라이언트의 수가 늘어나게 되면 기존 서버만으로는 정상적인 서비스가 불가능하게 됩니다.
> → 해당 문제를 해결하기 위해 2가지 방법이 가능(Scale-up, Scale-out)

## **로드밸런싱 알고리즘**

### **L4 로드밸런싱**

- 1. **라운드로빈 방식(Round Robin Method)**

> 서버에 **들어온 요청을 순서대로 돌아가며 배정**하는 방식  
>  클라이언트의 요청을 순서대로 분배하기 때문에 여러 대의 서버가 동일한 스펙을 갖고 있고, 
> 서버와의 연결(세션)이 오래 지속되지 않는 경우에 활용하기 적합합니다.

- 1. 가중 라운드로빈 방식(Weighted Round Robin Method)

> 각각의 서버마다 가중치를 매기고 가중치가 높은 서버에 클라이언트 요청을 우선적으로 배분  
> 주로 서버의 트래픽 처리 능력이 상이한 경우 사용되는 부하 분산 방식입니다.
>
> A라는 서버가 5라는 가중치를 갖고 B라는 서버가 2라는 가중치를 갖는다면  
> 로드밸런서는 라운드로빈 방식으로 A 서버에 5개 B 서버에 2개의 요청을 전달합니다.

- 2. IP 해시 방식(IP Hash Method)

> 클라이언트의 IP 주소를 특정 서버로 매핑하여 요청을 처리하는 방식  
> 사용자의 IP를 해싱해(Hashing, 임의의 길이를 지닌 데이터를 고정된 길이의 데이터로 매핑하는 것, 또는 그러한 함수) 로드를 분배하기 때문에 사용자가 항상 동일한 서버로 연결되는 것을 보장합니다.

- 3. 최소 연결 방식(Least Connection Method)

> 요청이 들어온 시점에 가장 적은 연결상태를 보이는 서버에 우선적으로 트래픽을 배분  
> 자주 세션이 길어지거나, 서버에 분배된 트래픽들이 일정하지 않은 경우에 적합한 방식입니다.

- 4. 최소 리스폰타임(Least Response Time Method)

> 서버의 현재 연결 상태와 응답시간(Response Time, 서버에 요청을 보내고 최초 응답을 받을 때까지 소요되는 시간)을 모두 고려하여 트래픽을 배분  
> 가장 적은 연결 상태와 가장 짧은 응답시간을 보이는 서버에 우선적으로 로드를 배분하는 방식입니다.

### **L7 로드 밸런싱**

> L7 로드밸런서는 위와 같은 L4 로드밸런서의 기능을 포함하는 것 뿐 만 아니라 OSI 7계층의 프로토콜 (예> HTTP, SMTP, FTP 등) 을 바탕으로도 분산 처리가 가능하다.  
> 예를 들어, 온라인 쇼핑몰의 장바구니에 물건들을 담아놓았는데 다른 서버에서의 처리가 어렵다.

- 1. URL 스위칭 방식 (URL Switching)

> 특정 하위 URL 들은 특정 서버로 처리하는 방식  
> `…/steven/image` 또는 `…/steven/video` 와 같은 특정 URL을 가진 주소들은  
> 서버가 아닌 별도의 스토리지에 있는 객체 데이터로 바로 연결되도록 구성할 수 있습니다.

- 컨텍스트 스위칭 방식 (Context Switching)

> 클라이언트가 요청한 **특정 리소스에 대해 특정 서버 등으로 연결**을 해줄 수 있습니다.
> 이미지 파일에 대해서는 확장자를 참조하여  
> 별도로 구성된 이미지 파일이 있는 서버/스토리지로 직접 연결해줄 수 있습니다.

- 쿠키 지속성 (Persistence with Cookies)\*\*

> 쿠키 정보를 바탕으로 클라이언트가 연결 했었던 동일한 서버에 계속 할당해주는 방식
> 특히 사설 네트워크에 있던 클라이언트의 IP 주소가 공인 IP 주소로 치환되어 전송 (X-Forwarded-For 헤더에 클라이언트 IP 주소를 별도 기록)하는 방식을 지원합니다.

## 로드밸런서의 기본 동작 방식

1. 클라이언트의 브라우저에서 [naver.com](http://naver.com) 이라고 입력
2. 클라이언트에 설정된 메인 DNS 서버로 naver.com 의 IP 주소를 문의
3. 메인 DNS 서버는 naver.com 주소를 관리하는 별도의 DNS 서버에 IP 주소 문의
4. 별도 관리 DNS 서버는 **로드밸런서의 IP (Virtual IP) 주소**를 메인 DNS 서버에게 알려줌
5. 메인 DNS 서버는 획득한 VIP 주소를 클라이언트에 전송
6. 클라이언트에서 로드밸런서의 VIP 주소로 http 요청
7. 로드밸런서는 별도 로드밸런싱 방법 (예. 라운드 로빈 등) 을 통하여 서버에게 요청을 전송
8. 서버의 작업 결과를 받은 로드밸런서는 전달받은 http 결과를 클라이언트에게 전송

## 로드 밸런서의 기본 기능

- Health Check

> 기본적으로 보통의 로드밸런서는 서버들 (또는 다음의 노드) 에 대한 주기적인 Health Check 를 통해  
> 서버들의 장애 여부를 판단할 수 있습니다.
>
> 이로 인해 로드밸런서가 있을 때 서버 몇 대에 이상이 생기더라도  
> 다른 정상 동작중인 서버로 트래픽을 보내주는 Fail-over가 가능하며,  
> 또한 TCP/UDP 분석이 가능하기 때문에 Firewall 의 역할도 수행할 수 있습니다.

- L3 체크
  - ICMP 를 이용하여 서버의 IP 주소가 통신 가능한 상태인지를 확인합니다.
- L4 체크
  - TCP 는 3 Way-Handshaking (전송 -> 확인/전송 -> 확인) 를 기반으로 통신합니다. 이러한 TCP 의 특성을 바탕으로 각 포트 상태를 체크하는 방식입니다. 예를 들어, HTTP 웹 서버의 경우 80 포트를 사용하므로 TCP 80 포트에 대한 체크를 통해 서버가 살아 있는 상태인지 확인합니다.
- L7 체크

  - 어플리케이션 계층에서 체크를 합니다. 즉, 실제 웹 페이지 (예> …/index.html) 에 통신을 시도하여 이상 유무를 파악합니다.

- Tunneling

> 눈에 보이지 않는 통로를 만들어 통신할 수 있게 하는 개념으로,  
> 로드밸런서는 클라이언트와 서버 간 중간에서 터널링을 제공해 줍니다.  
> 즉, 연결된 상호 간에만 캡슐화된 패킷을 구별해 캡슐화를 해제하게 합니다.

- NAT (Network Address Translation)

> IP 주소를 변환해주는 기능입니다. (목적지와 수신지의 IP 주소와 TCP/UDP 포트를 재기록하여 변환하며 네트워크 트래픽을 주고 받을 수 있습니다.)  
> 예를 들어, 내부 네트워크에서 사용하던 사설 IP 주소를 로드밸런서 외부의 공인 IP 주소로 변경해줍니다. (반대로도 가능)  
> 이렇게 하면 부족한 공인 IP 주소를 효율적으로 사용할 수 있지만,  
> 로드밸런싱 관점에서는 여러개의 호스트가 하나의 공인 IP 주소 (VIP) 를 통해 접속하는 것이 주 목적입니다.

- SNAT (Source Network Address Translation)

> 내부에서 외부로 트래픽이 나가는 경우, 내부 사설 IP 주소를 외부의 공인 IP 주소로 변환하는 방식입니다.  
> 집에서 사용하는 공유기가 대표적인 예 입니다.

- DNAT (Destination Network Address Translation)

> 외부에서 내부로 트래픽이 들어오는 경우, 외부 공인 IP 주소를 내부의 사설 IP 주소로 변환하는 방식입니다.  
> 본 포스트에서 설명하고 있는 로드밸런서가 대표적인 예 입니다.

- DSR (Direct Server Routing)

> 서버에서 클라이언트로 되돌아가는 경우, 목적지를 클라이언트로 설정한 다음 네트워크 장비나 로드밸런서를 거치지 않고  
> 바로 클라이언트를 찾아가는 방식입니다.  
> 이 경우, 로드밸런서의 부하를 줄여줄 수 있는 장점이 있습니다.

### L4? L7?

> 네트워크 통신 시스템은 크게 일곱 가지의 계층(OSI 7 layers, 개방형 통신을 위한 국제 표준 모델)으로 나뉩니다. 
> 각각의 계층(Layer)이 L1/L2/L3‥‥L7에 해당합니다. > 상위 계층에서 사용되는 장비는 하위 계층의 장비가 갖고 있는 기능을 모두 가지고 있으며, > 상위 계층으로 갈수록 더욱 정교한 로드밸런싱이 가능합니다.

- 부하 분산에는 L4 로드밸런서와 L7 로드밸런서가 가장 많이 활용된다.
  - 그 이유는 **L4 로드밸런서부터 포트(Port)정보를 바탕으로 로드를 분산하는 것이 가능하기 때문이다**
  - 한 대의 서버에 각기 다른 포트 번호를 부여하여 다수의 서버 프로그램을 운영하는 경우라면 최소 L4 로드밸런서 이상을 사용해야한다.

> L4 로드밸런서는 네트워크 계층(IP, IPX)이나 트랜스포트 계층(TCP, UDP)의 정보를 바탕으로 로드를 분산합니다. 
> IP주소나 포트번호, MAC주소, 전송 프로토콜에 따라 트래픽을 나누는 것이 가능합니다.

- 데이터 안을 보지 않고 패킷 레벨에서만 로드를 분산하기 때문에 **속도가 빠르고 효율이 높음**
- 섬세한 라우팅이 불가능하지만 **L7로드 밸런서보다 저렴**

- 애플리케이션 계층(HTTP, FTP, SMTP)에서 로드를 분산하기 때문에 HTTP 헤더
  - 쿠키 등과 같은 사용자의 요청을 기준으로 특정 서버에 트래픽을 분산하는 것이 가능합니다.
  - 쉽게 말해 패킷의 내용을 확인하고 그 내용에 따라 로드를 특정 서버에 분배하는 것이 가능한 것입니다.
  - 더 섬세한 라우팅이 가능하고, 비정상적인 트래픽을 필터링 할 수 있다.
  - 패킷의 내용을 복호화 해야하기 때문에 더 많은 비용이 든다.
