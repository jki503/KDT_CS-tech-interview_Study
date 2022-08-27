---
Title: Race Condtion, 동시성
Category: OS
Author: Jung
---

- [하나의 객체를 두 개의 스레드가 접근할 때 생기는 일](#하나의-객체를-두-개의-스레드가-접근할-때-생기는-일)
- [Race Condition](#race-condition)
- [동기화](#동기화)
- [임계 영역(ciritical section)](#임계-영역ciritical-section)
- [임계구역 해결 충족 조건](#임계구역-해결-충족-조건)
- [Thread - unsafe를 조심해라](#thread---unsafe를-조심해라)
- [임계구역 해결 방법](#임계구역-해결-방법)
  - [임계구역 예제](#임계구역-예제)
  - [Spin Lock](#spin-lock)
  - [Mutex](#mutex)
  - [Semaphore](#semaphore)

## 하나의 객체를 두 개의 스레드가 접근할 때 생기는 일

```java

for(귤 in 귤박스){
  if(귤 상태 is 불량){
    badCounter.increment();
  }
}

public class Counter{
  private int state = 0;

  public void increment(){state++;}

  public int get(){return state;}
}
```

</br>

## Race Condition

> 여러 프로세스/스레드가 동시에 같은 데이터를 조작할 때  
> 타이밍이나 접근 순서에 따라 결과가 달라질 수 있는 상황

</br>

## 동기화

> 여러 프로세스/ 스레드를 동시에 실행해도  
> 공유 데이터의 일관성을 유지하는 것  
> 그러면 위의 상황을 어떻게 동기화 시킬 것인가?

</br>

```java
public void increment(){state++;}
```

</br>

```assembly
LOAD state to R1
R1 = R1 + 1
STORE R1 to state
```

> 하나의 명령어가 아닌 여러개의 기계어가 실행되는 도중 context switcing이 발생한다.  
> 이 경우 위 기계어에서 컨텍스트 스위칭이 발생하지 않도록 하면 되지 않을까 할 수 있지만  
> 싱글코어에서는 가능하나 멀티코어에서는 불가능하다.  
> increment를 실행할 때 한 스레드만 실행할 수 있도록 하면 동시성 문제를 해결 할 수 있다.

</br>

## 임계 영역(ciritical section)

> 공유 데이터의 일관성을 보장하기 위해  
> 하나의 프로세스/스레드만 진입해서 실행 가능한 영역

</br>

## 임계구역 해결 충족 조건

</br>

- 상호 배제(mutual exclusion)

> 프로세스가 critial section을 점유하고 있는 상태일 경우  
> 다른 프로세스는 접근 불가해야한다.

</br>

- 진행(progress)

> 프로세스가 critical section을 들어가려고 할 때 다른 프로세스가  
> critical section에 들어가는 것을 방해하면 안된다.

</br>

- 한정 대기(bounded waiting)

> 무한정 대기를 피해, 점유하지 못하는 프로세스가 없어야 한다.

</br>

## Thread - unsafe를 조심해라

- SimpleDateFormat.class

> Date formats are not synchronized.  
> it is recommended to create separate format instances for each thread.  
> if multiple threads access a format concurrently, it must be synchronized externally.

</br>

## 임계구역 해결 방법

</br>

### 임계구역 예제

```java
volatile int lock = 0; // global

void critical(){
  while(test_and_set(&lock) == 1);
  ...critical section
  lock = 0;
}

int test_and_set(int* lockPtr){
  int oldLock = *lockPtr;
  *lockPtr = 1;
  return oldLock;
}
```

> T1이 접근할때 testAndSet이 return하는 값은 0이고 lock은 1이되고
> ciritical section에 접근한다.  
> 이때 t2가 접근하면 현재 lock 값이 1이고 testAndSet이 1되어 무한루프로 락을 기다린다.  
> 그후 t1이 ciritical section을 빠져나오고 lock을 0으로 반환하면  
> t2가 testAndSet을 호출하게 되면 0을 반환하여 t2가 critical section에 접근한다

</br>

- 둘다 동시에 접근하면 같이 critical section을 접근할 수 있지 않나?
  - testAndSet은 cpu atomic 명령어!
  - 실행 중간에 간섭받거나 중단되지 않는다
  - 같은 메모리 영역에 대해 동시에 실행되지 않는다

</br>

### Spin Lock

> 락을 가질 수 있을 때까지 반복해서 시도 하지만 락을 기다리는 동안 cpu를 낭비한다.  
> 하지만 멀티코어 환경이면서, critical section에서의 작업이 컨텍스트 스위칭보다 더 빨리 끝난다면  
> 스핀락이 뮤텍스보다 더 유리할 수 있다.

- 왜 멀티코어라는 상황이어야할까?

> Process A가 Critical Section에 들어가 있고 B가 spinlock 대기  
> 싱글코어는 코어가 하나이기때문에 스핀락 방식으로 대기한다 하더라도  
> 결국 코어 하나가 Lock을 해제하고 B로 context swtiching이 발생해서 무의미

### Mutex

</br>

> critical section에 들어가기 전에 lock을 얻고, 나올때 lock을 반환해야 한다.  
> 즉, lock의 획득과 해제의 주체가 동일해야한다.
>
> Priority inheritance가 있지만 semaphore는 없다.  
> 그러니까 엄밀히 말하면 binary Semaphore와 mutex는 같은 것은 아님.

</br>

- Priority inheritance

</br>

> 예를 들어 p2(low)이 lock을 점유하여 실행 중일때,  
> p1(high)이 우선순위가 높음에도 대기하는 상황이 발생.  
> p1이 p2를 의존하고 있는 대기 상태일 경우,  
> mutex에서는 p2의 우선순위를 p1만큼 올려버린다.  
> p2를 빨리 실행시키고 critical section을 빠져나오도록 해야한다.

</br>

### Semaphore

</br>

> signal mechanism을 가진, 하나 이상의 프로세스/스레드가  
> critical section에 접근 가능하도록 하는 장치
> 세마포어는 락의 획득과 해제의 주체가 다르다

</br>

</br>

</br>

- 결론!
  - 상호배제만 필요하다면 뮤텍스
  - 작업간의 실행 순서 동기화가 필요하면 세마포어
