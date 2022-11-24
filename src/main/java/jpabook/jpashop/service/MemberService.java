package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// fixme - JPA 의 모든 데이터 변경이나 로직들은 transaction 안에서 실행되어야 한다.
// fixme - 스프링에서 제공하는 taransactional 어노테이션을 사용해야 쓸 수 있는 옵션이 많다.★
// 단순 조회에서는 @Transactional(readOnly = true) 을 하면 속도가 빨라진다.

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional(readOnly = false)
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);

        // id 라도 return 해야 뭐가 저장됐는지 알 수 있겠죠?
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회 -
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    
    // 회원 단건 조회 - 단순 조회에서는 @Transactional(readOnly = true) 을 하면 속도가 빨라진다.
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
