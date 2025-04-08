<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
    // 페이지 로드 시 `msg` 값이 있으면 alert 창 띄우기
 
    function allCheck() {
        let id = document.getElementById("id").value;
        let pw = document.getElementById("pw").value;
        let confirm = document.getElementById("confirm").value;
        let userName = document.getElementById("userName").value;
        
        if (id.trim() === "") {
            alert("아이디를 입력하세요.");
            return;
        }
        if (pw.trim() === "") {
            alert("비밀번호를 입력하세요.");
            return;
        }
        if (confirm.trim() === "" || pw !== confirm) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }
        if (userName.trim() === "") {
            alert("이름을 입력하세요.");
            return;
        }

        document.getElementById("f").submit();
    }
</script>


<div align="center">
	<h1>회원 등록</h1>
	<table >
	<tr><td>
		<font color="red" >${msg }</font>
	</td></tr>
	<tr><td>
	<form action="registProc" method="post" id="f">
		<input type="text" name="id" placeholder="아이디" id="id"> (*필수 항목) <br>
		<input type="password" name="pw" placeholder="비밀번호" id="pw"><br>
		<input type="password" name="confirm" placeholder="비밀번호 확인 " id="confirm"
		onchange="pwCheck()">
		<label id="label" ></label><br>
		<input type="text" name="userName" id="userName" placeholder="이름" ><br>
		
		
		<input type="text" name="mobile" placeholder="전화번호" ><br>
		<input type="button" value="회원가입" onclick="allCheck()">
		<input type="button" value="취소" onclick="location.href='index'"><br>
	</form>
	</td></tr></table>
</div>

<c:import url="/footer" />



