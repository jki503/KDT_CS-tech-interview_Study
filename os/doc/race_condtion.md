# Race condition

</br>

> 두 개 이상의 프로스세가 concurrent 환경에서, critical section 영역에 접근 할 때  
> 특정한 순서로 싱핼 될 경우, 데이터 무결성이 깨지는 현상이 발생.

</br>

## 해결 충족 조건

</br>

- 상호 배제

> 프로세스가 critial section을 점유하고 있는 상태일 경우  
> 다른 프로세스는 접근 불가해야한다.

</br>

- 진행

> 프로세스가 critical section을 들어가려고 할 때 다른 프로세스가  
> critical section에 들어가는 것을 방해하면 안된다.

</br>

- 한정 대기

> 무한정 대기를 피해, 점유하지 못하는 프로세스가 없어야 한다.

</br>

## Mutex

> critical section에 들어가기 전에 lock을 얻고, 나올때 lock을 반환해야 한다.  
> 즉, lock의 획득과 해제의 주체가 동일해야한다.
>
> Priority inheritance가 있지만 semaphore는 없다.  
> 그러니까 엄밀히 말하면 binary Semaphore와 mutex는 같은 것은 아님.

</br>

- Priority inheritance

> 예를 들어 p2(low)이 lock을 점유하여 실행 중일때,  
> p1[high)이 우선순위가 높음에도 대기하는 상황이 발생.  
> p1이 p2를 의존하고 있는 대기 상태일 경우,  
> mutex에서는 p2의 우선순위를 p1만큼 올려버린다.

</br>

## Semaphore

> 세마포어 변수를 n으로 초기화 할때 n개의 프로세스들이 critical section에 접근 가능  
> Mutex와 다른 점은 현재 수행중인 프로세스가 아닌 다른 프로세스가 세마포어를 해제 가능  
> -> 작업들간의 순서 장점

## Spin Lock

> loop를 통해 thread가 lock을 얻을 때까지 대기 하는 것  
> CPU를 낭비한다는 단점이 있지만,  
> 멀티코어 환경이면서, 짧은 작업일 경우 context switching보다 유리할 수 있다.

</br>

> Process A가 Critical Section에 들어가 있고 B가 spinlock 대기  
> 싱클코어는 코어가 하나이기때문에 스핀락 방식으로 대기한다 하더라도  
> 결국 코어 하나가 Lock을 해제하고 B로 context swtiching이 발생해서 무의미

</br>

> 상호배제만 필요하다면 뮤텍스  
> 작업간의 실행 순서 동기화가 필요하면 세마포어
