# IPC

</br>

> 프로세스가 서로 협력하며 실행 될 경우  
> 각 프로세스들간의 영향을 미치는 것(shared data)을 고려하는 `프로세스들간의 통신`

## shaared memory

</br>

> 프로세스의 메모리 영역은 다른 프로세스가 접근 할 수 없도록 독립적인 공간을 보장해야 한다.  
> 따라서 각 프로세스들이 별도의 메모리 영역의 공유 메모리를 이용하여 사용 할 수 있도록 한다.  
> 커널은 프로세스로부터 공유메모리 할당 요청을 받은 이후, 어떤 프로세스든 해당 공유 메모리 영역에 접근 하여 사용할 수 있다.

</br>

## message passing

</br>

> 공유하는 메모리 영역 없이 프로세스 사이에 데이터를 송수신 및 동기화 작업
> OS는 send()와 receive() 명령어를 제공
> link를 구성할 때 direct or indirect 또 동기화와 비동기 통신 설정

|            수단            |                                설명                                |
| :------------------------: | :----------------------------------------------------------------: |
|           direct           | 송수신자를 명시하여 하나의 링크를 제공하여 두 개의 프로세스가 소통 |
|          indirect          |      mailbox(port)를 이용하여 두 개 이상의 프로세스들이 소통       |
|   synchronous(blocking)    |  sender(receiver) 보냈다(받았다)는 메시지를 받기 전까지 blocking   |
| asynchronous(non-blocking) |      sender(receiver)가 송수신하고 계속하여 다른 작업을 수행       |

</br>

## Pipe

## Socket

- Pipe

|        Ordinary pipe         |                 named pipe                  |
| :--------------------------: | :-----------------------------------------: |
| 부모 자식 관계에서 연결 가능 | 통신할 프로세스들을 모를 경우에도 연결 가능 |

> 두 pipe 방식다 한 쪽에서 쓰고, 한쪽에서 사용하는 단순한 흐름(반이중 통신)일 때 간편
> two - way 전이중 통신에서는 pipe를 2개 만들어 구현을 복잡하게 한다.

</br>

- Socket

> 전송을 주고 받을 프로세스들간의 ip와 port를 바인딩하여 데이터 공유

</br>

```java

import java.net.*;
import java.io.*;

public class DateServer {

    public static void main(String[] args) throws Exception{

    ServerSocket server = new ServerSocket(6013);

    /* Now listen for connections */
    while (true) {
      Socket client = server.accept();
      PrintWriter pout = new PrintWriter(client.getOutputStream(), true);

      /* write the Date to the socket */
      pout.println(new java.util.Date().toString());

      /* close the socket and resume listening for connections */
      client.close();
    }
  }
}

```

```java

 import java.net.*;
  import java.io.*;
  public class DateClient {

      public static void main(String[] args) throws Exception {

          /* make connection to server socket */
          Socket socket = new Socket("127.0.0.1", 6013);
          InputStream in = socket.getInputStream();
          BufferedReader br = new BufferedReader(new InputStreamReader(in));

          /* read date from the socket */
          String line = null;
          while ((line = br.readLine()) != null)
              System.out.println(line);

          /* close the socket connections */
          socket.close();
      }
}

```

> 서버는 소켓을 열고 클라이언트 소켓 연결 대기 - server.accept()  
> 서버는 클라이언트가 소켓 연결하면 client를 반환 받은 후 client outputStream에 데이터 입력  
> 서버는 클라이언트에게 전송 해준 후 client.close()
>
> 클라이언트는 ip와 port로 서버 바인딩
> 클라이언트는 inputStream으로 데이터를 받아온 후 client 종료.
