package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //NamedQuery를 쉽게 부르기 위해 사용
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);
}
