<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index</title>
<script>
    // 회원가입 성공 메시지가 존재하면 팝업을 띄움
   window.onload = function() {
        var msg = "${msg}";
        var logoutMsg = "${logoutMessage}";
        var vipUpgradeMsg = "${vipUpgradeMessage}";
        
        if (msg && msg === "회원 등록 완료") {
            alert("✅ 정상적으로 회원등록이 되었습니다!");
        }

  
        
        if (vipUpgradeMsg) {
            alert("🎉 VIP로 등업되었습니다!");
        }
        if (msg && msg === "이미 사용중인 아이디입니다.") {
            alert("❌ 이미 사용 중인 아이디입니다!");
        }
    };
</script>
</head>
<body>
	<c:import url="/header"/>
	<c:import url="/main"/>
	<div align="center">
	</div>
	<c:import url="/footer"/>
</body>
</html>









