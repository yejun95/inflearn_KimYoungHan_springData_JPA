package study.datajpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    //NamedQuery를 쉽게 부르기 위해 사용
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    //쿼리 바로 치기
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    //Dto 조회
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    //컬렉션 파라미터 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    //다양한 반환 값
    List<Member> findListByUsername(String username); //컬렉션
    Member findMemberByUsername(String username); //단건
    Optional<Member> findOptionByUsername(String username); //단건 Optional

    //페이징 (만약 조인 조건이 복잡한 경우 count 확인 시에도 join이 일어나기 때문에 성능 최적화를 위해서 countQuery로 따로 체크
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    //벌크성 수정
    @Modifying(clearAutomatically = true) // 이게 있어야 executeUpdate를 실행하고, clear옵션은 벌크 수정 이후 영속성 컨텍스트를 clear 해주는 것임
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    //fetch join
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //JPA Hint
    @QueryHints(value = @QueryHint(name ="org.hibernate.readOnly", value="true"))
    Member findReadOnlyByUsername(String username);

    //JPA Lock
    @Lock(LockModeType.PESSIMISTIC_WRITE) //for update
    List<Member> findLockByUsername(String username);
}
