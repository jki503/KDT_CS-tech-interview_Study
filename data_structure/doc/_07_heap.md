---
Title: Heap & PriorityQueue
Category: Data Structure
Author: Jung
---

- [Priority Queue](#priority-queue)
- [Heap](#heap)
  - [힙 주요 동작(위의 그림에서 진행)](#힙-주요-동작위의-그림에서-진행)
  - [힙실습](#힙실습)
- [힙과 우선순위 큐의 관계](#힙과-우선순위-큐의-관계)
- [힙과 우선순위 큐 사용 사례](#힙과-우선순위-큐-사용-사례)

## Priority Queue

</br>

> 큐와 유사하지만 우선순위가 높은 아이템먼저 처리하는 큐  
> 자료구조 힙으로 주로 구현 된다.
> 하지만 배열로도 우선순위 큐를 구현할 수 있음을 인지해야한다.

- java.util.PriorityQueue
  - 배열을 인스턴스 변수로 가지고 있다.
  - 다른 클래스들과 마찬가지로 grow를 통해 배열을 확장시켜 나가서 초기 용량을 결정할 수 있는 상황이면 capacity를 지정하는 것이 좋다.
  - tree에서는 부모노드를 통해 비교하였는데 배열이니까, 자리를 shift 하면서 배열의 원소와 비교한 후 자리를 바꾸는 로직을 수행한다.

</br>

## Heap

</br>

> 힙은 주로 완전 이진 트리를 기반으로 구현

</br>

- maxHeap : 부모 노드의 키가 자식 노드들의 키보다 크거나 같은 트리

|               Max Heap               |
| :----------------------------------: |
| ![Max Heap](../res/_08_max_heap.png) |

- minHeap : 부모 노드의 키가 자식 노드들의 키보다 작거나 같은 트리

</br>

### 힙 주요 동작(위의 그림에서 진행)

- insert(17)

</br>

|               insert 17               |
| :-----------------------------------: |
| ![insert17](../res/_08_insert_17.png) |

</br>

- insert 17 이후 자기 부모와 비교를 시작한다.
- 3과 비교를 한 후 17이 더 크기때문에 `3과 17의 자리`를 바꾼다.
- 15와 비교한 후 17이 더크기때문에 `15와 17의 자리`를 바꾼다.
- 20과 비교 한 후 17이 더 작기 때문에 비교를 멈춘다.

</br>

|                        결과                         |
| :-------------------------------------------------: |
| ![insert17_result](../res/_08_insert_17_result.png) |

</br>

|               max heap               |
| :----------------------------------: |
| ![max heap](../res/_08_max_heap.png) |

- delete()

| root(20)을 삭제 한 후 가장 마지막 노드(2)를 root 위치로 이동하고, 원래 위치에서 삭제한다. |
| :---------------------------------------------------------------------------------------: |
|                             ![delete](../res/_08_delete.png)                              |

</br>

- 자녀 노드 15와 11 모두 2보다 크다 이 경우 15가 더 크기때문에 15와 2의 자리를 바꾼다
- 자녀노드 12와 3 모두 2보다 크다 이 경우 12가 3보다 더 크기때문에 12와 2의 자리를 바꾼다
- 자녀노드 7과 5 모두 2보다 크다. 7이 5보다 크기때문에 7과 2의 자리를 바꾼다.

</br>

|                 최종                  |
| :-----------------------------------: |
| ![최종](../res/_08_delete_result.png) |

</br>

### 힙실습

- insert 1
- insert 10
- insert 5
- insert 4
- insert 7
- delete(첫번째)
- insert 15
- delete(두번째)
- insert 12
- insert 9
- delete(세번째)
- delete(네번째)

</br>

- insert 1
- insert 10
  - 마지막 노드 위치에 10을 넣는다
  - 그 후 부모 노드인 1과 비교후 10이 더크기때문에 자리를 바꾼다.
  - 루트(부모가 없음으로)라서 비교를 멈춘다

</br>

|                   insert 10 이후                   |
| :------------------------------------------------: |
| ![insert 10 이후](../res/_08_insert_10_result.png) |

</br>

- insert 5
  - 마지막 노드 위치에 5를 넣는다
  - 그 후 부모 노드인 10과 비교후 5가 더 작기 때문에 비교를 멈춘다

</br>

|                  insert 5 이후                   |
| :----------------------------------------------: |
| ![insert 5 이후](../res/_08_insert_5_result.png) |

</br>

- insert 4
  - 마지막 위치에 4를 넣는다
  - 그 후 부모 노드인 1과 비교후 4가 더 크기때문에 자리를 바꾼다
  - 그 후 부모 노드인 10과 비교후 4가 더 작기때문에 비교를 멈춘다

|                  insert 4 이후                  |
| :---------------------------------------------: |
| ![insert 4이후](../res/_08_insert_4_result.png) |

</br>

- insert 7
  - 마지막 위치에 7을 넣는다
  - 그 후 부모 노드인 4와 비교후 7이 더 크기때문에 자리를 바꾼다
  - 그 후 부모 노드인 10과 비교후 7이 더 작기때문에 비교를 멈춘다

|                  insert 7 이후                   |
| :----------------------------------------------: |
| ![insert 7 이후](../res/_08_insert_7_result.png) |

</br>

- delete(첫번째)
  - root(10)을 지우고, 그 위치에 마지막 노드 4를 위치한다.
  - 4의 자식노드 7과 5가 있고 7이 더 큼으로 4와 7의 위치를 바꾼다.
  - 4의 자식노드 1이 있는데 더 작음으로 비교를 멈춘다.

|                 delete(첫번째) 이후                 |
| :-------------------------------------------------: |
| ![delete(첫번째) 이후](../res/_08_delete_first.png) |

</br>

- insert 15
  - 마지막 위치에 15를 넣는다
  - 부모노드인 4와 비교후 15가 더 크기때문에 자리를 바꾼다
  - 부모노드인 7과 비교후 15가 더 크기때문에 자리를 바꾼다.
  - 루트임으로 비교를 멈춘다

|                   insert 15이후                    |
| :------------------------------------------------: |
| ![insert 15 이후](../res/_08_insert_15_result.png) |

</br>

- delete(두번째)
  - root(15)의 위치와 4의 위치를 바꾸고 15를 지운다.
  - 4의 자식노드 7과 5 둘 중 7이 더 크기때문에 7과 자리를 바꾼다.
  - 4의 자식노드 1은 4보다 작음으로 비교를 멈춘다

|                 delete(두번째) 이후                  |
| :--------------------------------------------------: |
| ![delete(두번째) 이후](../res/_08_delete_second.png) |

</br>

- insert 12
  - 마지막 위치에 12를 넣는다
  - 부모인 4와 비교후 12가 더 크기때문에 자리를 바꾼다.
  - 부모인 7과 비교후 12가 더 크기때문에 자리를 바꾼다.
  - 루트임으로 비교를 멈춘다.

|                   insert 12 이후                   |
| :------------------------------------------------: |
| ![insert 12 이후](../res/_08_insert_12_result.png) |

</br>

- insert 9
  - 마지막 위치에 9를 넣는다
  - 부모 5와 비교후 9가 더 크기 때문에 자리를 바꾼다
  - 부모 12와 비교후 9가 더 작기 때문에 비교를 멈춘다

|                  insert 9 이후                   |
| :----------------------------------------------: |
| ![insert 9 이후](../res/_08_insert_9_result.png) |

</br>

- delete(세번째)
  - root(12)와 마지막 노드의 위치를 바꾼후 root를 삭제한다.
  - 5의 자식노드 7과 9 중 9가 더 크기때문에 9와 자리를 바꾼다
  - 이후 5의 자식노드가 없음으로 비교를 멈춘다

|                 delete(세번째) 이후                 |
| :-------------------------------------------------: |
| ![delete(세번째) 이후](../res/_08_delete_third.png) |

</br>

- delete(네번째)
  - root(9)와 마지막 노드(4)의 위치를 바꾼후 root를 삭제한다.
  - 4의 자식 노드 7과 5중 7이 더 크기때문에 7과 자리를 바꾼다.
  - 4의 자식노드 1이 더 작음으로 비교를 멈춘다

|                 delete(네번째) 이후                  |
| :--------------------------------------------------: |
| ![delete(네번째) 이후](../res/_08_delete_fourth.png) |

</br>

## 힙과 우선순위 큐의 관계

</br>

> 힙의 키를 우선순위로 사용한다면 `힙은 우선순위 큐의 구현체가 된다.`

- 우선순위 큐 : ADT
- Heap : data structure

</br>

## 힙과 우선순위 큐 사용 사례

- 프로세스 스케줄링
