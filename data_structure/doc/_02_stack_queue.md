---
Title: Stack & Queue & Deque
Category: Data Structure
Author: Jung
---

- [Stack](#stack)
  - [java.util.Stack 살펴보기](#javautilstack-살펴보기)
  - [java.util.Stack은 어쩌면 이렇게 작성되어야 했을지도?](#javautilstack은-어쩌면-이렇게-작성되어야-했을지도)
  - [스택 사용 사례 : stack memory & stack frame](#스택-사용-사례--stack-memory--stack-frame)
- [Queue](#queue)
  - [큐 사용 사례 : producer / consumer architecture](#큐-사용-사례--producer--consumer-architecture)
  - [java.util.Queue 살펴보기](#javautilqueue-살펴보기)
  - [기술 문서에서 큐를 만났을 때 팁](#기술-문서에서-큐를-만났을-때-팁)
- [스택 / 큐 관련 에러와 해결 방법](#스택--큐-관련-에러와-해결-방법)
  - [StackOverFlow](#stackoverflow)
  - [OutOfMemoryError](#outofmemoryerror)
- [Deque](#deque)

</br>

- Abstract Data Type
  - 추상 자료형
  - 개념적으로 어떤 동작이 있는지만 정의
  - 구현에 대해서는 다루지 않음

</br>

- Data Structrue
  - 자료구조
  - ADT에서 정의된 동작을 실제로 구현한 것

## Stack

</br>

- Stack 주요 동작(LIFO)
  - push
  - pop
  - peek

</br>

### java.util.Stack 살펴보기

</br>

```java
public class Stack<E> extends Vector<E> {

    public Stack() {
    }

    public E push(E item) {
        addElement(item);

        return item;
    }

    public synchronized E pop() {
        E       obj;
        int     len = size();

        obj = peek();
        removeElementAt(len - 1);

        return obj;
    }

    public synchronized E peek() {
        int     len = size();

        if (len == 0)
            throw new EmptyStackException();
        return elementAt(len - 1);
    }

    public boolean empty() {
        return size() == 0;
    }

    public synchronized int search(Object o) {
        int i = lastIndexOf(o);

        if (i >= 0) {
            return size() - i;
        }
        return -1;
    }

    @java.io.Serial
    private static final long serialVersionUID = 1224463164541339165L;
}
```

</br>

> Java 1.0부터 출시된 Stack이다.  
> 제공해주는 operation은 push, pop(), peek(), empty(), search()다.  
> 자바를 잘 아시는 분이거나, 혹은 코테 좀 풀어보신분들은 조금 이상하다는 것을 알 것이다.
> 가끔 다른 분들 코드 보면 stack.isEmpty()를 사용하시는데  
> 이것은 엄밀히 stack이 제공해주는 operaiton이 아니다!!

</br>

```text
The Stack class represents a last-in-first-out (LIFO) stack of objects. It extends class Vector with five operations that allow a vector to be treated as a stack. The usual push and pop operations are provided, as well as a method to peek at the top item on the stack, a method to test for whether the stack is empty, and a method to search the stack for an item and discover how far it is from the top.
When a stack is first created, it contains no items.

A more complete and consistent set of LIFO stack operations is provided by the Deque interface and its implementations, which should be used in preference to this class. For example:
 Deque<Integer> stack = new ArrayDeque<Integer>();
Since:
1.0
Author:
Jonathan Payne
```

> 스택의 레퍼런스를 참고해보면  
> 완전하고 일관된 LIFO 구조의 스택 동작들이 제공되는 것은 dequeue 인터페이스라고 말한다.  
> 그러면 Stack은 완전한 구조가 아닌가..?

</br>

> Stack을 보면 Vector를 상속받는 구조이다.  
> 따라서 Vector의 상태와 행위를 사용할 수 있다.

```java
    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    @SuppressWarnings("unchecked")
    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }

    /**
     * Returns the element at the specified position in this Vector.
     *
     * @param index index of the element to return
     * @return object at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *            ({@code index < 0 || index >= size()})
     * @since 1.2
     */
    public synchronized E get(int index) {
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);

        return elementData(index);
    }
```

</br>

> vector에서 get 오퍼레이션이다.  
> vertor 내부에 elementData 배열의 인덱스 원소를 반환해주는데  
> stack에서도 이를 사용하여 원소를 얻을 수 있다.

</br>

```java
    public boolean remove(Object o) {
        return removeElement(o);
    }

    /**
     * Inserts the specified element at the specified position in this Vector.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index > size()})
     * @since 1.2
     */
    public void add(int index, E element) {
        insertElementAt(element, index);
    }

    /**
     * Removes the element at the specified position in this Vector.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).  Returns the element that was removed from the Vector.
     *
     * @param index the index of the element to be removed
     * @return element that was removed
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     * @since 1.2
     */
    public synchronized E remove(int index) {
        modCount++;
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);
        E oldValue = elementData(index);

        int numMoved = elementCount - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--elementCount] = null; // Let gc do its work

        return oldValue;
    }
```

</br>

> remove와 get을 사용한다면  
> 본래 우리가 의도하였던 스택, top에만 접근하는 LIFO 구조를  
> 무시하고도 stack을 사용할 수 있다.

</br>

### java.util.Stack은 어쩌면 이렇게 작성되어야 했을지도?

```java
public Stack<E>{
  private Vector<E> elements = new Vector<>();

  public E push(E item){
    elements.addElement(item);
    return item;
  }

  public synchronized E pop(){
    if(elements.isEmpty()){
      throw new EmptyStackException();
    }

    return elements.remove(elements.size()-1);
  }

  public synchronized E peek() {
    int     len = size()
    if (len == 0)
        throw new EmptyStackException();
    return elementAt(len - 1);
  }

  public boolean empty(){return elements.size() == 0;}

  public synchronized int search(Object o){
    int i = elements.lastIndexOf(o);

    if(i >= 0){
      return size() -i;
    }

    return -1;
  }
}
```

</br>

> 이렇게 구현하는 것이 완전한 문제를 해결한 것인지는 모르겠지만  
> 이전 방식에서 보여준 `상속`의 문제점을 해결하고  
> 사용자로부터 `LIFO`에서 필요한 오퍼레이션만 제공하여  
> 완전한 LIFO를 제공할 수 있다!

</br>

### 스택 사용 사례 : stack memory & stack frame

</br>

> stack memory는 함수가 호출 될 때마다  
> stack frame이 쌓이고 함수가 사라지면  
> 그에 해당하는 stack frame이 stack memory도 사라진다.

</br>

```python
def a() :
    b()

def b():
    c()

def c():
    print("wow")
```

> 위와 같이 함수를 호출하는  
> 파이썬 코드가 있다고 가정할때

</br>

| Stack Memeory |
| :-----------: |
| print("wow")  |
|      c()      |
|      b()      |
|      a()      |

</br>

> 스택 메모리의 스택 프레임은 아래와 같이 쌓이고  
> print("wow") -> a() 순으로 해제 된다.

</br>

## Queue

</br>

- Queue 주요 동작(FIFO)
  - enqueue
  - dequeue
  - peek

</br>

### 큐 사용 사례 : producer / consumer architecture

</br>

> producer가 Queue에 item을 쌓으면  
> Consumer가 들어온 순서대로 item을 사용하고  
> Queue를 비우는 구조!

</br>

### java.util.Queue 살펴보기

</br>

```java
public interface Queue<E> extends Collection<E> {

  boolean add(E e);

  boolean offer(E e);

  E remove();

  E poll();

  E element();

  E peek();


}
```

> Queue에서 제공해주는 오퍼레이션 목록이다.  
> 이외에 우리가 흔히 사용하는 isEmpty, size(), clear등은  
> Collection이 제공해준다.

</br>

> 자바 개발자들은 흔히 Queue의 구현체로 LinkedList로 선언하여 사용한다.

</br>

```java
Queue<Integer> queue = new LinkedList<>();
```

> 그러면 java에서 ArrayList는 안되나?

</br>

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable

public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

> 단순하다. ArrayList를 보면 Queue 타입이 아니기 때문이다.
> 물론 LinkedList 말고도 ArrayDequeue 구현체가 있다!
> 이건 뒤에서 살펴보자!
>
> 그리고 굳이 구현체가 아닌, 배열로도 큐를 구현하는 방법이 있지만  
> 배열의 첫번째 원소를 제거한 후, 1 ~ size - 1을 한 칸식 당기는  
> 시간복잡도가 발생함으로 선형 자료구조로 큐를 구현하는 방식은 좋지 않다.  
> 배열로 구현하려면 적어도 원형큐로 구현할 노력정도는 보여야 할 지도!

</br>

### 기술 문서에서 큐를 만났을 때 팁

</br>

> 항상 FIFO를 의미하지는 않는다!  
> Multitasking에서 하나의 싱글코어 CPU에  
> 3개의 process가 실행되어야 한다고 가정한다면  
> P1, P2, P3 스위칭이 발생 하면서 실행된다.
>
> 이 경우 p1이 실행될 때 p2, p3는 ready queue에 들어가게 되는데  
> ready queue는 priority queue동작 된다.  
> 약간 기술문서는 뭐가 들어가고 나간다는 느낌의  
> 대기열의 개념으로 말하는 경우도 있다

</br>

## 스택 / 큐 관련 에러와 해결 방법

</br>

### StackOverFlow

</br>

- 스택 메모리 공간을 다 썼을 때 발생하는 에러
  - 재귀 함수에서 탈출 못할 때 발생

> 탈출 조건을 명시 안했거나  
> 재귀의 뎁스가 점점 깊어질 때 발생함으로 이 문제를 해결해야한다.

</br>

### OutOfMemoryError

</br>

- Java의 heap(heap) 메모리를 다 썼을 때 발생
  - 큐에 데이터가 계속 쌓이기만 한다면 발생

</br>

> 이 경우에는 큐 사이즈를 고정해줘야 한다.  
> 이 경우 어떤 이슈가 발생을 고려해야하면

</br>

- 예외를 던지거나
- 특별한 값(null or false)을 반환해주거나
- 성공할 때까지 영원히 스레드를 블락(block)
  - 스레드 낭비 문제..
- 제한된 시간만 블락되고 그래도 안되면 포기

</br>

- LinkedBlockingQueue
  - 실제 자바에서 이 문제를 해결하기 위한 네가지 방식의 api가 제공된 queue

</br>

|         | Throws exception | Special value |     Blocks     |      times out      |
| :-----: | :--------------: | :-----------: | :------------: | :-----------------: |
| Insert  |      add(e)      |   offer(e)    |     put(e)     | offer(e, time,unit) |
| Remove  |      remove      |    poll()     |     take()     |   poll(time,unit)   |
| Examine |    element()     |    peek()     | not applicable |   not applicable    |

</br>

## Deque

</br>

</br>
