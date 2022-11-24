package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

/*
* 순수한 단위 테스트를 만드는게 아니라
* JPA 가 실제 DB 까지 도는 것을 보여주는게 중요해서
* 메모리 모드로 DB 까지 엮어서 테스트를 하는게 중요해요.
*
* 완전히 스프링이랑 integration 해서 테스트를 할꺼구요.
* @RunWith() 와 @SpringBootTest 가 있어야 스프링이랑 integration 해서 스프링 부트로 올려서 테스트 할 수 있습니다.
*
* 데이터를 변경하기 위해 @Transactional 작성. 이게 있어야 롤백이 되죠.
* */

@RunWith(SpringRunner.class) // @RunWith(SpringRunner.class) 는 Junit 실행할때 spring 이랑 엮어서 실행할래 라고하면 @RunWith() 에 SpringRunner.class 를 넣으면 됩니다.
@SpringBootTest // 스프링 부트를 띄운 상태에서 테스트 하려면 @SpringBootTest 가 있어야 합니다.
@Transactional // @Transactional 을 걸면 테스트를 한다음에 테스트가 끝나면 롤백해버립니다.
public class MemberServiceTest {
    
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @Rollback(value = false) // @Rollback(value = false) 를 주면 insert 문을 볼 수 있다. , @Transactional 이 테스트 케이스에 있으면 기본적으로 Rollback 을 해버리기 때문.
    public void 회원가입() throws Exception {
        // fixme - given (이런게 주어졌을때)
        Member member = new Member();
        member.setName("kim");

        // fixme - when (이렇게 하면)
        Long savedId = memberService.join(member);

        // fixme - then (이렇게 된다.)
        em.flush(); // flush 는 영속성 context 에 있는 변경이나 등록 내용을 데이터 베이스에 반영한다.
        Assert.assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test(expected = IllegalStateException.class) // expected = IllegalStateException.class 를 작성해주면 try, catch 문을 작성하지 않아도 된다.
    public void 중복_회원_예외() throws Exception {
        // fixme - given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // fixme - when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다.

        // fixme - then
        fail("예외가 발생해야 한다.");

    }

}