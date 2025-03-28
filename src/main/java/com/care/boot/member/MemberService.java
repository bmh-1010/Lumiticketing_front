package com.care.boot.member;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Service


public class MemberService {
    @Autowired private IMemberMapper mapper;
    @Autowired private HttpSession session;

    public String registProc(MemberDTO member) {
        if(member.getId() == null || member.getId().trim().isEmpty()) return "아이디를 입력하세요.";
        if(member.getPw() == null || member.getPw().trim().isEmpty()) return "비밀번호를 입력하세요.";
        if(!member.getPw().equals(member.getConfirm())) return "두 비밀번호를 일치하여 입력하세요.";
        if(member.getUserName() == null || member.getUserName().trim().isEmpty()) return "이름을 입력하세요.";

        MemberDTO check = mapper.login(member.getId());
        if(check != null) return "이미 사용중인 아이디입니다.";

        /* 비밀번호 암호화 */
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secretPass = encoder.encode(member.getPw());
        member.setPw(secretPass);

        int result = mapper.registProc(member);
        return (result == 1) ? "회원 등록 완료" : "회원 등록을 다시 시도하세요.";
    }

    public String upgradeToVIP(String sessionId) {
        System.out.println("===== [DEBUG] VIP 승격 요청 시작 =====");
        System.out.println("sessionId: " + sessionId);

        MemberDTO member = mapper.login(sessionId);
        if (member == null) {
            System.out.println("🚨 회원 정보 없음!");
            return "회원 정보가 존재하지 않습니다.";
        }

        if ("VIP".equals(member.getMembership())) {
            System.out.println("⚠ 이미 VIP 회원임!");
            return "이미 VIP 회원입니다.";
        }

        int vipCount = mapper.countVIPMembers();
        System.out.println("현재 VIP 회원 수: " + vipCount);

        if (vipCount >= 100) {
            System.out.println("❌ VIP 최대 회원 수 초과!");
            return "VIP 회원이 최대 100명을 초과할 수 없습니다.";
        }

        int result = mapper.promoteToVIP(sessionId, vipCount + 1);
        if (result > 0) {
            mapper.removeFromRegular(sessionId);
            System.out.println("✅ VIP 승격 완료!");
            return "VIP 승격 완료!";
        }

        System.out.println("🚨 VIP 승격 실패!");
        return "VIP 승격에 실패했습니다.";
    }

    @Transactional
    public void upgradeMemberToVIP(String memberId) {
        System.out.println("🔹 upgradeMemberToVIP 실행 - id: " + memberId);

        List<Integer> vipNumbers = mapper.getAllVIPNumbers();
        System.out.println("🔹 현재 VIP 번호 목록: " + vipNumbers);

        int nextVIPNumber = findNextVIPNumber(vipNumbers);
        System.out.println("🔹 다음 VIP 번호: " + nextVIPNumber);

        int result = mapper.promoteToVIP(memberId, nextVIPNumber);
        System.out.println("🔹 promoteToVIP 실행 결과: " + result);

        if (result > 0) {
            mapper.removeFromRegular(memberId);
            System.out.println("✅ VIP 승격 완료! RegularMember에서 삭제됨");
        } else {
            throw new RuntimeException("VIP 승격 실패");
        }
    }

    /**
     * 가장 작은 VIP 번호를 찾아 -1을 적용하여 중복되지 않는 번호 반환
     */
    private int findNextVIPNumber(List<Integer> vipNumbers) {
        if (vipNumbers.isEmpty()) {
            return 1; // VIP 리스트가 비어 있으면 1부터 시작
        }

        int prev = vipNumbers.get(0) - 1;
        if (prev > 0 && !vipNumbers.contains(prev)) {
            return prev;
        }

        for (int i = 1; i < vipNumbers.size(); i++) {
            int next = vipNumbers.get(i) - 1;
            if (next > 0 && !vipNumbers.contains(next)) {
                return next;
            }
        }

        // 모든 값이 사용 중이라면 가장 큰 값 +1을 부여
        return vipNumbers.get(vipNumbers.size() - 1) + 1;
    }

    
    public String loginProc(String id, String pw) {
        if(id == null || id.trim().isEmpty()) {
            return "아이디를 입력하세요.";
        }
        if(pw == null || pw.trim().isEmpty()) {
            return "비밀번호를 입력하세요.";
        }

        MemberDTO check = mapper.login(id);  // RegularMember만 조회할 수도 있음!
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(check != null && encoder.matches(pw, check.getPw())) {
            session.setAttribute("id", check.getId());
            session.setAttribute("userName", check.getUserName());
            session.setAttribute("mobile", check.getMobile());
            session.setAttribute("membership", check.getMembership());
            
            // ✅ VIP 여부 체크 후 세션 저장

            return "로그인 성공";
        }


        return "아이디 또는 비밀번호를 확인 후 다시 입력하세요.";
    }

    public List<MemberDTO> searchRegularMembers(String keyword) {
        return mapper.searchRegularMembers(keyword);
    }






    public List<MemberDTO> getVipMembers() {
        return mapper.getVipMembers();
    }
    
    public List<MemberDTO> getAllTicketHolders() {
        return mapper.getAllTicketHolders();  // ✅ TicketHolderDTO → MemberDTO 변경
    }

    @Autowired
    private IMemberMapper memberMapper;

    public boolean reserveTicket(String id) {
        MemberDTO member = memberMapper.getMemberById(id);
        if (member == null) return false; // 멤버 없으면 예매 불가

        int ticketNumber;
        if ("VIP".equals(member.getMembership())) {
            ticketNumber = memberMapper.getNextVipTicketNumber();
        } else {
            ticketNumber = memberMapper.getNextRegularTicketNumber();
        }

        return memberMapper.insertTicketHolder(member.getId(), member.getUserName(),
                member.getMobile(), member.getMembership(), ticketNumber) > 0;
    }

    
    public List<MemberDTO> searchTicketHolders(String keyword) {
        return mapper.searchTicketHolders(keyword);
    }

    public List<MemberDTO> getRegularMembers() {
        return mapper.getRegularMembers();
    }

    public List<MemberDTO> searchVipMembers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return mapper.getVipMembers(); // 검색어 없으면 전체 VIP 회원 반환
        }
        return mapper.searchVipMembers(keyword); // 검색 기능 적용
    }
    
    @Transactional
    public void downgradeMemberToRegular(String memberId) {
        System.out.println("🔹 downgradeMemberToRegular 실행 - id: " + memberId);

        int result = mapper.downgradeToRegular(memberId); // ✅ RegularMember로 이동
        System.out.println("🔹 downgradeToRegular 실행 결과: " + result);

        if (result > 0) {
            mapper.removeFromVIP(memberId); // ✅ VIPMember에서 삭제
            System.out.println("✅ 일반회원 전환 완료! VIPMember에서 삭제됨");
        } else {
            throw new RuntimeException("일반회원 전환 실패");
        }
    }
    
    

    
    
	}
    
