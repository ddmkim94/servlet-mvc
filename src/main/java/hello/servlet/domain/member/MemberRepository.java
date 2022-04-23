package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// @NoArgsConstructor(access = AccessLevel.PRIVATE) 기본 생성자를 private 레벨로 생성
public class MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    // 저장소 객체는 싱글톤으로 관리
    private static final MemberRepository instance = new MemberRepository();

    // 저장소 객체는 getInstance() 메서드로만 받을 수 있음!
    public static MemberRepository getInstance() {
        return instance;
    }

    // 외부에서 맘대로 생성할 수 없도록 private 레벨로 생성
    private MemberRepository(){}

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
