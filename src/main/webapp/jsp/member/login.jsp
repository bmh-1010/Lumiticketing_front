<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />

<script>
    // ✅ 엔터 키로 로그인 기능 추가
    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("f").addEventListener("keypress", function(event) {
            if (event.key === "Enter") {
                event.preventDefault();  // 기본 동작 막기
                loginCheck();  // 로그인 버튼 클릭과 동일한 동작 실행
            }
        });
    });
</script>
<div align="center">
	<h1>로그인</h1>
	<table >
	<tr><td>
		<font color="red" >${msg }</font>
	</td></tr>
	<tr><td>
<form action="loginProc" method="post" id="f" class="login-container">
    <input type="text" name="id" placeholder="아이디" id="id"><br>
    <input type="password" name="pw" placeholder="비밀번호" id="pw"><br>
    
    <div class="btn-container">
        <input type="button" value="로그인" onclick="loginCheck()">
        <input type="button" value="취소" onclick="location.href='index'">
    </div>
</form>
	
	</table>
</div>
<c:import url="/footer" />



