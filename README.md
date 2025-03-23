# 광고 서비스_매일모으기
매일모으기 서비스는 유저가 광고에 참여하고 포인트를 적립받는 서비스입니다.
구현할 서버는 매일모으기 서비스에서 광고를 관리하는 서버입니다.

광고 등록, 광고 조회, 광고 참여, 광고 참여 이력 조회 API를 제공하는 서버를 개발합니다.
광고별로 참여 가능 횟수가 제한되며 전부 소진될 시 해당 광고로 포인트를 적립 받을 수 없습니다.

## 목차
- [기술 스택](#기술-스택)
- [프로젝트 디렉토리 구조](#프로젝트-디렉토리-구조)
- [설계 원칙 및 핵심 문제 해결 전략](#설계-원칙-및-핵심-문제-해결-전략)
- [API 명세](#api-명세)

---

## 기술 스택
- Server: Spring Boot, Java 11, Spring Data JPA
- Database: H2 for local test
- Cache & Lock: Redis (분산 락 적용)
- Testing: JUnit5, Mockito

---

## 프로젝트 디렉토리 구조
``` text
src/main/java/com/example/
 ├── common
 │   ├── domain/               # Domain 추상 클래스 (AggregateRoot, DomainEntity 등)
 │   ├── jpa/                  # JPA 관련 공통 설정 모음
 │       ├── hibernate/            #  Hibernate 관련 설정 및 ID 커스텀 타입 정의
 │  
 ├── adservice                 # 광고 서비스
     ├── application/              # 비즈니스 로직 처리
     ├── controller/               # API 컨트롤러
     ├── domain/                   # 광고 서비스 도메인 (광고, 유저, 광고 참여 이력)
     ├── persistance/              # 데이터베이스 상호작용
 
 
src/main/
...
 ├── resources/               # 설정 파일 및 리소스 파일 저장
     ├── application.yml      # Spring Boot 애플리케이션 환경 설정 파일

```
---

## 설계 원칙 및 이유
본 프로젝트는 DDD(Domain-Driven Design) 원칙을 기반으로 설계되었습니다.  
이를 통해 비즈니스 로직과 데이터의 일관성을 유지하고, 확장성을 고려한 구조를 구성하였습니다.

### DDD를 적용한 이유
- 비즈니스 로직 중심 설계: 광고 참여 횟수 감소, 광고 노출 조건 등의 핵심 로직을 도메인 계층에서 관리
- 유지보수성 향상: 도메인 모델과 애플리케이션 로직을 명확히 분리하여 코드 가독성 및 유지보수성을 높임
- 확장성 고려: 광고 참여 제한 조건이 추가될 가능성이 있어, 정책을 확장 가능하도록 설계

### 레이어드 아키텍처 적용
아래와 같은 4계층 구조를 적용하여 책임을 명확히 분리하고,  
서비스 확장 시 각 계층별 변경을 최소화할 수 있도록 설계하였습니다.

- 계층 및 역할
  - 도메인 계층 (`domain`): 핵심 비즈니스 로직과 엔티티를 정의하는 영역
  - 애플리케이션 계층 (`application`): 비즈니스 로직을 조합하고 트랜잭션을 관리하는 영역
  - 프레젠테이션 계층 (`presentation(controller)`): API 요청 처리 및 애플리케이션 계층과 상호작용하는 영역
  - 퍼시스턴트 계층 (`persistance`): 데이터베이스와 상호작용하는 영역

###  정책 기반 광고 참여 제한 적용
- `Policy` 엔티티를 통해 유저의 참여 제한 정책을 설정
- `Ad.canUserJoin()`에서 정책을 적용하여 참여 가능 여부를 검증

### 식별자(Identifier) 설계
- 광고 ID (`AdId`), 유저 ID (`UserId`)를 단순한 `Long` 값이 아닌 Value Object(`StringTypeIdentifier`)로 관리.
- ID를 값 객체로 만들면 사용되는 도메인 의미가 더 명확해지고, 잘못된 값 할당을 방지할 수 있음
- 고유한 규칙을 가진 식별자를 사용할 수 있어, 읽기 쉽고 의미전달이 용이

### Redis를 이용한 분산 락으로 동시성 제어
다수의 서버 인스턴스에서 동작하며, 여러 유저가 동시에 광고에 참여할 수 있는 구조
따라서, 광고 참여 가능 횟수(remainingJoinCount)가 0 이하로 내려가는 문제를 방지하기 위해 Redis 기반의 분산 락을 활용하여 동시성을 제어

---

### 1️⃣ 광고 등록 API
- Method: `POST /api/ads`
- Request:
  ```json  
  {  
    "adName": "광고1",  
    "rewardAmount": 100,  
    "remainingJoinCount": 10,  
    "description": "광고 설명",  
    "imageUrl": "https://example.com/ad.png",  
    "startDate": "2025-01-01",  
    "endDate": "2025-12-31"  
  }  
  ``` 
  - Response: `200 OK`  

### 2️⃣ 광고 조회 API
- Method: `GET /api/ads`
- Response:
  ```json  
  [  
    {      "adId": "ad123",  
      "adName": "광고1",  
      "rewardAmount": 100  
    }  
  ]
  ```  
  

### 3️⃣ 광고 참여 API
- Method: `POST /api/ads/join`
- Request:
  ```json  
  {  
    "userId": "user123",  
    "adId": "ad123"  
  }  
  ```
- Response: `200 OK`  

### 4️⃣ 광고 참여 이력 조회 API
- Method: `POST /api/ads/history`
- Request:
  ```json
  {
    "userId": "user123",
    "startDate": "2025-02-01T00:00:00",
    "endDate": "2025-02-28T23:59:59",
    "page": 0
  }
  ```
- Response:
  ```json
  {
    "content": [
      {
        "userId": "user123",
        "adId": "ad123",
        "joinAt": "2025-02-20T10:00:00",
        "adName": "광고1",
        "rewardAmount": 100
      }
    ],
    "totalElements": 1,
    "totalPages": 1
  }
  ```
---
