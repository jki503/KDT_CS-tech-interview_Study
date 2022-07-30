---
Title: Map
Category: Data Structure
Author: Jung
---

- [Set](#set)
- [Set 구현체](#set-구현체)
- [import java.util.HashSet 살펴보기](#import-javautilhashset-살펴보기)

## Set

> 리스트와 비교했을 때 가장 큰 차이는 원소의 중복을 허용하지 않는다는 것이다.  
> 따라서 Set을 사용하는 사례는 주로 유일성을 보장해야 하는 상황,  
> 혹은 원소를 넣게 되는 상황에서 이미 원소가 있을 경우 특정 로직을 수행할 때 많이 사용한다.

</br>

## [Set 구현체](#java-구현체)

- HashTable
- Tree-based

> Map과 마찬가지로 거의 구현체는 비슷하다.  
> 해시를 사용할 경우 원소의 순서를 보장할 수 없다.  
> 개인적인 경험으로 게속적으로 딱히 순서를 보장해야할 필요가 많이 없고,  
> 마지막에 값을 도출하기 위한 경우라면 hash를 사용후  
> 오퍼레이션이 필요한 경우 그냥 key를 정렬해서 사용하는 편이긴 하다

</br>

## import java.util.HashSet 살펴보기

```java
public class HashSet<E>
    extends AbstractSet<E>
    implements Set<E>, Cloneable, java.io.Serializable
{
    @java.io.Serial
    static final long serialVersionUID = -5024744406713321676L;

    private transient HashMap<E,Object> map;

    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();

    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }

    public boolean contains(Object o) {
      return map.containsKey(o);
    }


    public boolean remove(Object o) {
      return map.remove(o)==PRESENT;
    }

```

> 생성자 정보를 파악하는 것도 굉장히 중요하지만

- hashMap을 인스턴스 변수로 가지는 것
- hashMap을 이용하여 어떻게 메서드를 사용하는지

> 에대하여 파악하겠다!

</br>

> 내부를 보면 간단하다.

- add

  - map.put(e, PRESENT) 를 실행시킨다.
  - 기존에 키를 삽입했다면, map에서 (e,PRESNET)가 존재할 것이다.
    - 이 경우에는 중복 원소임을 알려주기 위해 false를 반환한다.
  - 처음 삽입했다면 map.put()이 null을 반환하여 원소를 넣고 true를 반환한다.

- contains

  - map.containsKey를 그대로 사용하여 값의 존재 유무를 반환한다.

- remove
  - remove도 map.remove를 그대로 사용한다,
  - 단 없는 원소에 대하여 remove를 실행할 경우, null == PRESENT, false를 반환하고
  - 있는 원소에 대하여 remove를 실행할 경우 true를 반환한다.
