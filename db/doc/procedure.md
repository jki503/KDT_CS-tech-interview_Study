# 프로시저

</br>

> 서버로 보내서 처리, 리턴값 선택, 리턴값 여러개 반환 가능

</br>

- 프로시저는 처리성능과 재사용면에서 좋지 않다.
  - 하나의 요청으로 여러 SQL문을 실행 가능
  - 네트워크 소요 시간을 줄일 수 있음(여러개의 쿼리를 처리하는 시점에서 네트워크 부하 줄임)

</br>

- 생성

```SQL

DELIMETER $$
CREATE PROCEDURE TEST_PROC
(
  PARAM_NAME VARCHAR(20),
  PARAM_AGE INT
)
BEGIN

  DECLARE PARAM_NUM INTEGER;

  SELECT COUNT(*) + 1
    INTO PARAM_NUM
    FROM TABLE1;

  INSERT INTO TABLE1(TOTAL_COUNT, USER_NAME, USER_AGE) VALUES(PARAM_NUM, PARAM_NAME, PARAM_AGE)
END $$
DELIMITER;
```

</br>

```SQL
CALL TEST_PROC('테스트 이름', 21);
```

- DB 카탈로그
- SQL 컴파일
- 메모리 저장
- 실행

</br>

```SQL
DELIMITER $$
CREATE PROCEDURE 'TEST_PROC2'(
  IN loopCount1 INT,
  IN loopCount2 INT,
  OUT rst1 INT,
  OUT rst2 INT,
  INOUT rst3 INT
)
BEGIN
  DELCLATE NUM1 INTEGER DEFAULT 0;
  DELCLATE NUM2 INTEGER DEFAULT 0;
  DELCLATE NUM3 INTEGER DEFAULT 0;

  WHILE NUM1 < loopCount1 D0
    WHILE NUM2 < loopCount2 D0
        SET NUM3 = NUM3 + 1;
        SET NUM2 = NUM2 + 1;
    END WHILE;

    SET NUM1 = NUM1 +;
    SET NUM2 = 0;
  END WHILE;

  SET rst1 = NUM1;
  SET rst2 = NUM3;
  SET rst3 = rst1 + rst2 + rst3;
END $$
DELIMITER;

```

- IN
  - 프로시저에 값을 전달하며, 프로시저 내부에서 값을 수정할 수 있지만, 프로시저가 반환되고 나서 호출자가 수정은 불가
  - 즉 원본 값은 프로시저가 끝난 후에도 유지되며, 프로시저는 IN 파라미터의 복사본 허용
- OUT
  - 프로시저의 값을 호출자에게 다시 Return.
  - 초기값은 프로시저 내에서 Null이며 프로시저가 반환 될 때 새로운 값이 호출자에게 Return
  - 프로그램이 시작 될 때, Out 파라미터의 초기값에 접근 할 수 없다.
- INOUT
  - 호출자에 의해 하나의 변수가 초기화 되고 프로시저에 의해 수정
  - 프로시저가 return 될 때 프로시저가 변경한 사항은 호출자에게 return
