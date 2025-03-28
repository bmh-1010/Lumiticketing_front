package com.care.boot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.boot.member.MemberDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@RequestMapping("index")
	public String index(HttpSession session, RedirectAttributes redirect) {
	    MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
	    System.out.println("loginUser : " + loginUser);
	    if(loginUser != null) {
	    	System.out.println("loginUser.getMembership : " + loginUser.getMembership());
	    }
	    	
	    // 로그인 되어 있지 않거나, 등급이 VIP나 Admin이 아니면 리다이렉트
	    if (loginUser == null || 
	        (!"VIP".equalsIgnoreCase(loginUser.getMembership()) && 
	         !"Admin".equalsIgnoreCase(loginUser.getMembership()))) {

	        redirect.addFlashAttribute("msg", "접근 권한이 없습니다. 로그인 후 이용해주세요.");
	        return "redirect:https://login.lumiticketing.click";
	    }

	    return "index"; // index.jsp로 이동
	}

	
	@RequestMapping("header")
	public String header() {
		return "default/header";
	}
	@RequestMapping("main")
	public String main() {
		return "default/main";
	}
	@RequestMapping("footer")
	public String footer() {
		return "default/footer";
	}
}
