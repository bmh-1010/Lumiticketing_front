<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />

<%-- 로그인 상태 확인 (세션에 저장된 ID 체크) --%>
<c:if test="${empty sessionScope.loginUser.id}">
    <script>
        alert("로그인을 해주세요!");
        location.href = "${pageContext.request.contextPath}/login";
    </script>
</c:if>

<div align="center">
<table>
	<tr><td>${msg }</td></tr>  
	<tr><td>
	<form action="${pageContext.request.contextPath}/reserveTicket" method="post">
    <input type="submit" value="예매하기">
<input type="button" value="메인으로가기" onclick="location.href='index'">
</form>
	</td></tr>
</table>
</div>

<c:import url="/footer" />









