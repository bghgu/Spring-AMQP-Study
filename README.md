# Spring AMQP Study

2018 소프트웨어 캡스톤 디자인 Spring AMQP Stduy

1. Spring AMQP Rabbit MQ 연동 
2. Spring AMQP Apache Kafka 연동



## 기본개념

**AMQP(Advanced Message Queing Protocol)**

메시지 지향 미들웨어를 위한 개방형 표준 응용 계층 프로토콜

**AMQP의 정의 기능**

메지시 지향, 큐잉, 라우팅(P2P 및 발행-구독), 신뢰성, 보안

**메시지 지향 미들웨어 (Message Oriented Middleware, MOM)**

분산 시스템 간 메시지를 주고 받는 기능을 지원하는 소프트웨어/하드웨어 인프라

**메시지 큐 (Message Queue, MQ)**

MOM을 구현한 시스템

비동기 메시지 처리 방식

**브로커 (Broker)**

MQ 시스템

![mq.PNG](https://github.com/bghgu/Spring-AMQP-Study/blob/master/image/mq.PNG)

Producer(생산자)가 Message를 Queue에 넣어두면 Comsumer(소비자)가 Message를 가져와 처리하는 방식

클라이언트와 동기방식으로 많은 데이터 통신을 하게 되면 병목 현상이 생기게 되고, 서버의 성능이 저하된다. 이러한 현상을 막고자 하나의 미들웨어에서 메시지를 순차적으로 처리한다.

단점으로, 즉각적인 서비스는 불가능하다.



## RabbitMq

AMQP를 지원하는 오픈소스 메시징 시스템

RabbitMQ는 우체국이자, 우체통이자, 우편부이다.

**장점**

실시간 모니터링 및 관리 용이 - 플러그인 추가 필요

다양한 언어 지원

클러스터링 가능

**단점**

windows는 Erlang, OpenSSL를 설치해야 한다.

### 기본 개념

1. Message 

   일정한 구조를 지닌다.

   * header : message_id, time_stamp, content_type
   * body : data

2. Exchange

   Producer가 메시지를 보낼 때, AMQP로 교환하는 과정

   * Direct(기본값) : Routing Key와 Exchange이 같을 때
   * Topic : Routing Key와 Exchange이 일정 패턴이 동일 할 때
   * Fanout : 무관할 때

3. Queue

   메시지가 쌓이는 요소, Comsumer가 Message를 받는 구성 요소

   * Name : Queue 이름
   * Durable : 대기열을 유지할 지를 결정할 플래그
   * Exclusive : 선언된 연결에 의해서만 사용할지 결정할 플래그
   * Auto-Delete : 더이상 사용되지 않는 큐를 삭제할 플래그

4. Binding

   메시지를 주고 받을 수 있게 도와주는 교환기

5. Connection

   실질적인 TCP 연결

6. Channel

   가상연결, 메시지에 대한 작업 단위





## Apache Kafka

분산 메시징 시스템, 

대용량의 실시간 로그처리에 특화된 아키텍쳐 설계

**장점**

AMQP나 JMS를 사용하지 않고 단순 메시지 헤더를 지닌 TCP 통신

개별 전송이 아닌 다수 전송 가능(Batch 처리 가능)

파일 시스템에 저장(데이터의 영속성 보장)

대기중인 메시지로 인한 시스템 성능 감소 줄임

분산 시스템이 기본적으로 설계

**단점**

큐의 기능은 기존 JMS나 Broker보다 부족



## 시작하기

모든 소스코드는 IntelliJ + Window10 + JAVA 8 환경에서 작성되었습니다.

### MAVEN을 이용한 의존성 프로젝트 추가

이 프로젝트에서는 아래 같은 **의존성 프로젝트**가 포함되어있습니다. 

**pom.xml** 파일에 아래와 같이 **의존성 프로젝트**를 추가해 주세요.

```
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
    </dependencies>
```
## 실행하기

window 10 환경

- `jdk8` 과 `maven` 을 설치합니다.
- `JAVA_JOME` 환경변수 설정을 합니다.
- `Path`에 `maven` 환경변수 설정을 합니다.spring boot 앱 실행

```
mvn spring-boot:run
```

- 중지하려면, 키보드에서 `Crtl + C`를 누릅니다.
- `application.properties` 파일이 필요합니다.

AWS EC2 Ubuntu 환경

- `jdk8` 과 `maven` 을 설치합니다.

백 그라운드 spring boot 앱 실행

```
nohup mvn spring-boot:run&
```

- 중지하려면,  `netstat -tnlp` 명령어를 통해 프로세스를 kill 하십시오.
- `application.properties` 파일이 필요합니다.

## 사용된 도구

* [Spring-boot](https://projects.spring.io/spring-boot/) - Spring-boot Web Framework
* [Maven](https://maven.apache.org/) - 의존성 관리 프로그램
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDEA
* [lombok](https://projectlombok.org/) - IDEA
* [MySql](https://www.mysql.com/) - 데이터베이스
* [AWS RDS](https://aws.amazon.com/ko/rds/getting-started/) - 클라우드 환경 데이터베이스 관리 시스템
* [RabbitMQ](https://www.rabbitmq.com/) - 메시지 큐 시스템
* [Apache Kafka](http://kafka.apache.org/downloads.html) - 메시지 큐 시스템

## 저자

* **배다슬** - [bghgu](https://github.com/bghgu)


[기여자 목록](https://github.com/bghgu/Spring-WebSocket-Study/contributors)을 확인하여 이 프로젝트에 참가하신 분들을 보실 수 있습니다.

## 감사 인사

* [허원철의 개발 블로그](http://heowc.tistory.com/35?category=677973) - 메시지 큐
* [허원철의 개발 블로그](http://heowc.tistory.com/36) - Spring AMQP
* http://epicdevs.com/17 - Apache Kafka

---


