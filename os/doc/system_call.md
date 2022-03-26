# 시스템 호출

</br>

> 운영체제의 커널이 제공하는 서비스를 응용프로그램이 요청하여 접근 할 수 있도록 하는 인터페이스  
> 직접접근을 하지 않고 고급언어에서 제공해주는 API를 사용하는 이유는 내부 시스템을 악의적인 의도로 부터 보호하기 위함.

</br>

## POSIX API

</br>

| Process Control() |                                                                         설명                                                                          |
| :---------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------: |
|      fork()       |                                     새로운 자식 프로세스를 생성 시키고, 부모 프로세스의 주소 공간을 복사하여 할당                                     |
|      exec()       |                                                exec()에 의해 호출된 프로세스가 메모리 공간을 할당 받음                                                |
|      exit()       |                                                               exit(0) 와 exit(1)의 차이                                                               |
|      wait()       | wait() 함수 발생 시 현재 프로세스는 wait queue로 이동, 자식 프로세스가 종료되면 부모 프로세스가 ready queue로 이동 하여 다시 실행 가능한 상태가 된다. |

</br>

> 자식 process의 pid는 0

</br>

- 고아 프로세스

</br>

> 부모 프로세스가 자식 프로세스보다 먼저 종료될 경우 init 프로세스가 자식 프로세스의 부모가 되는 경우

- 좀비 프로세스

> 자식 프로세스가 부모 프로세스가 먼저 종료 될 경우  
> exit() 함수 및 return문으로 반환되는 값을 운영체제는 부모 프로세스에게 전달 할 때까지 소멸 시키지 않는다.  
> 즉 좀비프로세스는 운영체제가 유지시키는 상태이고 부모 프로세스에게 return 및 exit으로  
> 좀비 프로세스의 종료 상태를 알려 소멸함으로써 주의 사항을 어길시 리소스가 낭비된다.

</br>

```cpp
// fork() 기본 예제

#include <stdio.h>
#include <unistd.h>
#include <wait.h>

int value = 5;

int main()
{
  pid_t pid;
  pid = fork();

  if(pid == 0){ // child
    value +=15;
    return 0;
  }
  else if(pid > 0){ // parent
    wait(NULL);
    printf("Parent: value = %d\n", value); // value=?
  }

}

```

</br>

| File management |
| :-------------: |
|     open()      |
|     read()      |
|     write()     |
|     close()     |

</br>

| Device Management |
| :---------------: |
|      ioctl()      |
|      read()       |
|      write()      |

</br>

| Information maintenance |
| :---------------------: |
|         pipe()          |
|       shm_open()        |
|         mmap()          |

</br>

| Protection |
| :--------: |
|  chmod()   |
|  umask()   |
|  chown()   |

## JAVA API

> 추후 팀원들과 Java study에서 정리할 예정
