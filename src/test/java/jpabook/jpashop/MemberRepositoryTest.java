package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testMember() throws Exception {

        // fixme - given : member 를 가지고
        Member member = new Member();
        member.setUsername("memberA");

        // fixme - when : save 를 한 다음 저장된 id 를 뽑고
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        // fixme - then : 여기선 검증만
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember = " + findMember);
        System.out.println("member = " + member);

    }
}