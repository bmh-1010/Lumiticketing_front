package com.care.boot.member;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MemberDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String pw;
    private String userName;
    private String mobile;
    private String email;
    private String membership;   // "Regular", "VIP", "Admin"
    private Integer vipNumber;   // VIP 회원만 해당
    private LocalDateTime date;  // 등록일 or VIP 등록일
    private String confirm;
    private int ticketNumber;
    public int getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public MemberDTO() {
        this.membership = "Regular";
    }

    public MemberDTO(String id, String pw, String userName, String mobile, String email, String membership, Integer vipNumber, LocalDateTime date) {
        this.id = id;
        this.pw = pw;
        this.userName = userName;
        this.mobile = mobile;
        this.email = email;
        this.membership = membership;
        this.vipNumber = vipNumber;
        this.date = date;
    }

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPw() { return pw; }
    public void setPw(String pw) { this.pw = pw; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMembership() { return membership; }
    public void setMembership(String membership) { this.membership = membership; }

    public Integer getVipNumber() { return vipNumber; }
    public void setVipNumber(Integer vipNumber) { this.vipNumber = vipNumber; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getConfirm() { return confirm; }
    public void setConfirm(String confirm) { this.confirm = confirm; }
}
