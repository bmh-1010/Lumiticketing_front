package com.care.boot.member;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {
    @Autowired private MemberService service;
    @Autowired private HttpSession session;
    @Autowired private KakaoService kakaoService;

    @RequestMapping("regist")
    public String regist() {
        return "member/regist";
    }

    @PostMapping("registProc")
    public String registProc(MemberDTO member, Model model, RedirectAttributes ra) {
        String msg = service.registProc(member);
        ra.addFlashAttribute("msg", msg);
        return "redirect:index";
    }

    @RequestMapping("login")
    public String login() {
        return "member/login";
    }

    @PostMapping("loginProc")
    public String loginProc(String id, String pw, Model model, RedirectAttributes ra) {
        String msg = service.loginProc(id, pw);
        if(msg.equals("로그인 성공")) {
            ra.addFlashAttribute("msg", msg);
            return "redirect:index";
        }
        model.addAttribute("msg", msg);
        return "member/login";
    }

    @RequestMapping("logout")
    public String logout(RedirectAttributes ra, HttpSession session) {
        session.removeAttribute("loginUser"); // 세션에서 해당 속성만 제거
        ra.addFlashAttribute("logoutMessage", "로그아웃되었습니다!");
        return "redirect:/login";
    }

    @RequestMapping("vipPayment")
    public String vipPayment(RedirectAttributes redirect) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            redirect.addFlashAttribute("msg", "로그인이 필요합니다!");
            return "redirect:/login";
        }
        return "member/vipPayment";
    }

    @PostMapping("vipPaymentProc")
    public String vipPaymentProc(RedirectAttributes ra) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            ra.addFlashAttribute("msg", "로그인이 필요합니다!");
            return "redirect:/login";
        }

        String msg = service.upgradeToVIP(loginUser.getId());
        if (msg.equals("VIP 승격 완료!")) {
            session.invalidate();
            ra.addFlashAttribute("vipUpgradeMessage", "🎉 VIP로 승격되었습니다!");
            return "redirect:index";
        }
        ra.addFlashAttribute("msg", "VIP 승격 실패!");
        return "member/vipPayment";
    }

    @RequestMapping("ticketing")
    public String ticketing(RedirectAttributes redirect) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            redirect.addFlashAttribute("msg", "로그인이 필요합니다!");
            return "redirect:/login";
        }
        return "member/ticketing";
    }

    @PostMapping("ticketingPaymentProc")
    public String ticketingPaymentProc(RedirectAttributes redirect) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            redirect.addFlashAttribute("msg", "로그인이 필요합니다!");
            return "redirect:/login";
        }

        boolean success = service.reserveTicket(loginUser.getId());
        redirect.addFlashAttribute("msg", success ? "🎉 예매 성공!" : "❌ 예매 실패!");
        return "redirect:/ticketing";
    }

    @PostMapping("/reserveTicket")
    public String reserveTicket(HttpSession session, RedirectAttributes redirect, Model model) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            redirect.addFlashAttribute("msg", "로그인이 필요합니다!");
            return "redirect:/login";
        }

        return "member/ticketingPayment";  // 결제 페이지로 이동
    }

    @RequestMapping("ticketHolder")
    public String ticketHolder(@RequestParam(required = false) String keyword, Model model, RedirectAttributes redirect) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null || !"admin".equalsIgnoreCase(loginUser.getMembership())) {
            redirect.addFlashAttribute("msg", "관리자만 접근 가능한 페이지입니다!");
            return "redirect:index";
        }

        List<MemberDTO> tickets = (keyword != null && !keyword.trim().isEmpty()) ?
                service.searchTicketHolders(keyword) : service.getAllTicketHolders();
        model.addAttribute("tickets", tickets);
        return "member/ticketHolder";
    }

    @RequestMapping("memberInfoVIP")
    public String memberInfoVIP(@RequestParam(required = false) String keyword, Model model, RedirectAttributes redirect) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
	    System.out.println("loginUser : " + loginUser);
	    
	    if(loginUser != null) {
	    	System.out.println("loginUser.getMembership : " + loginUser.getMembership());
	    }
        
        if (loginUser == null || !"admin".equalsIgnoreCase(loginUser.getMembership())) {
            redirect.addFlashAttribute("msg", "관리자만 접근 가능한 페이지입니다!");
            return "redirect:index";
        }

        List<MemberDTO> members = (keyword != null && !keyword.trim().isEmpty()) ?
                service.searchVipMembers(keyword) : service.getVipMembers();
        model.addAttribute("members", members);
        return "member/memberInfoVIP";
    }

    @RequestMapping("memberInfoRegular")
    public String memberInfoRegular(@RequestParam(required = false) String keyword, Model model, RedirectAttributes redirect) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
	    System.out.println("loginUser : " + loginUser);
	    if(loginUser != null) {
	    	System.out.println("loginUser.getMembership : " + loginUser.getMembership());
	    }
        if (loginUser == null || !("admin".equalsIgnoreCase(loginUser.getMembership()) ) ) {
            redirect.addFlashAttribute("msg", "관리자만 접근 가능한 페이지입니다!");
            return "redirect:index";
        }

        List<MemberDTO> members = (keyword != null && !keyword.trim().isEmpty()) ?
                service.searchRegularMembers(keyword) : service.getRegularMembers();
        model.addAttribute("members", members);
        return "member/memberInfoRegular";
    }

    @PostMapping("/promoteToVIP")
    public ResponseEntity<String> promoteToVIP(@RequestParam String id) {
        try {
            service.upgradeMemberToVIP(id);
            return ResponseEntity.ok("VIP 승격 완료!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("VIP 승격 실패!");
        }
    }

    @PostMapping("/downgradetoRegular")
    public ResponseEntity<String> downgradeToRegular(@RequestParam String id) {
        try {
            service.downgradeMemberToRegular(id);
            return ResponseEntity.ok("일반회원 전환 완료!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일반회원 전환 실패!");
        }
    }

    @GetMapping("/test-session")
    @ResponseBody
    public String testSession(HttpSession session) {
        MemberDTO user = (MemberDTO) session.getAttribute("loginUser");
        return (user == null) ? "session not found" : user.toString();
    }
}
