package study.datajpa.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();

        List<Member> members = em.createQuery("select m from Member m ", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }
    }

    //MappedSuperclass가 제대로 적용됐는지 확인
    @Test
    public void JpaEventBaseEntity() throws InterruptedException {
        Member member = new Member("member1");
        memberRepository.save(member); //@PrePersist

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush(); //@PreUpdate
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).get();

        System.out.println("findMember.getCreatedDate() = " + findMember.getCreatedDate());
        System.out.println("findMember.getUpdatedDate() = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getUpdatedDate() = " + findMember.getCreatedBy());
        System.out.println("findMember.getUpdatedDate() = " + findMember.getLastModifiedBy());
    }
}