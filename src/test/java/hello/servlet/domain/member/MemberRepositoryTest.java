package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    // 각 테스트 종료 후 실행되는 메서드
    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        Member member1 = new Member("은빈", 31);
        Member saveMember = memberRepository.save(member1);
        Member findMember = memberRepository.findById(saveMember.getId());

        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    void findAll() {
        Member member1 = new Member("은빈", 31);
        Member member2 = new Member("동민", 28);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(2);
        assertThat(members).contains(member1, member2);
    }
}