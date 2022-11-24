package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    // fixme - persist 하면 영속성 context 에 member 객체를 넣는다(올린다). , 트랜젝션이 commit 되는 시점에 db에 반영된다.
    // fixme - 그때 영속성 context 는 key, value 가 있는데 key 가 'pk' 가 된다.
    public Long save(Member member) {
        em.persist(member);
        return null;
    }

    // fixme - findOne() 의 경우 JPA 의 find 메서드를 사용하는데 첫번째는 타입, 두번째는 pk 를 넣어주시면 됩니다. (단건조회)
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // fixme - findAll() 은 JPQL 을 썼죠. JPQL 문법은 SQL 과 거의 똑같은데 from 의 대상이 테이블이 아닌 엔티티라고 보시면 됩니다.
    public List<Member> findAll() {

        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // fixme - 여기선 파라미터 바인딩해서, 특정 회원 이름으로 찾는 것을 만들어봤습니다.
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
