# 김영한님의 spring Data JPA 강의 정리
<br>
<hr>
<br>

## ✔️ springDataJPA directory
### 학습 범위 : 4-1-1 - 4-1-4
- 프로젝트 생성 및 환경 설정

- jpa와 spring data jpa 간단한 멤버 생성 테스트
<br>

**➡️ 테스트 라이브러리**
- spring-boot-starter-test
  - junit
  - mockito
  - assertj
  - spring-test : 스프링 통합 테스트 지원
<br>

**➡️ 핵심 라이브러리**
- 스프링 MVC

- 스프링 ORM

- JPA, 하이버네이트

- 스프랑 데이터 JPA
<br>

**➡️ 기타 라이브러리**
- H2 데이터베이스 클라이언트

- 커넥션 풀: 부트 기본은 HikariCP

- 로깅 : SLF4J & LogBack

- 테스트
<br>
<br>

### 학습 범위 : 4-2-1 - 4-4-91
- jpa를 이용한 crud -> spring data jpa로 변환

- 쿼리 생성 방법
  - 쿼리 메서드 : name 관례를 지키며 메서드 생성
  - NamedQuery : 어노테이션 사용 (잘 사용하지 않음)
  - Query 어노테이션에 직접 쿼리 입력 (앱 실행 시점에 에러 캐치 가능)
    - dto를 통한 조회
    - 컬렉션 조회
<br>

- jpa 페이징

- spring data 페이징
  - 정렬 : `Sort`
  - 페이징 : `Pageable`
  - 반환 타입 : `Page`, `Slice`, `List`
  - 페이징 유지한채 dto로 변환하여 반환 : `page.map`
<br>

- 벌크성 수정 쿼리
  - `@Modifying(clearAutomatically = true)` : 벌크 수정 이후 추가적인 트랜잭션이 있을 경우 반드시 적용하여 영속성 컨텍스트 초기화
  - 벌크성 수정 쿼리는 DB에 직접 반영하기 때문에 영속성 컨텍스트와 데이터 불일치가 발생할 수 있으니 조심
<br>

- @EntityGraph
  - spring data jpa는 메서드로만 쿼리를 실행할 수도 있기 때문에 fetch join을 쿼리에 적지 못할 경우가 있음
  - `@EntityGraph(attributePaths = {"Entity"})`
<br>

- JPA Hint

- JPA Lock
<br>
<br>

### 학습 범위 : 4-5-1
<br>

**➡️ 사용자 정의 인터페이스**
- 인터페이스를 생성하고, 이를 클래스에서 `Impl`을 붙여 구현한다.

- 그리고 커스텀으로 생성한 인터페이스를 `JpaRepository`를 상속받는 인터페이스에 추가로 붙여주는 것이다.

- 그러면 JPA가 이를 함께 관리한다.
<br>

**➡️ Auditing**
- 엔티티를 생성, 변경할 때 변경한 사람과 시간을 추적할 때 사용

- 등록일, 수정일, 등록자, 수정자처럼 공통으로 넣어야되는 컬럼에 주로 사용한다.

- MappedSuperclass vs Embedded
  - 상속과 위임의 차이
  - 등록일, 수정일과 같이 공통 사항을 Embedded로 사용시 모든 엔티티에 따로 추가해야 하므로 번거로울 수 있다.
<br>

- Jpa vs springDataJpa
  - 둘이 Auditing을 하는 방법의 차이점 존재
  - springDataJpa 코드가 좀 더 줄어든다.
<br>

- springDataJpa의 Auditing의 경우 `@Bean`에 `AuditorAware` 설정을 등록하여 사용한다.
  - 해당 빈에 데이터가 들어오면, 그 데이터를 이용하여 등록자, 수정자 등에 데이터를 넣는다.
  - spring security 사용 시 사용자가 api를 쏠 때 마다, 사용자를 체크하고 해당 사용자이름을 등록자, 수정자에 넣을 수 있다.
