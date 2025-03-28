<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="icon" type="image/x-icon" href="/static/favicon.ico">


<style type="text/css">
   /* 공통 스타일 */
   a { text-decoration: none; color: black; }
   ul { padding: 10px; margin: 0; }
   ul li { display: inline-block; padding: 12px; position: relative; cursor: pointer; }
   
   /* 제목 컨테이너 */
.header-container {
    background-color: #CBD5E1; /* 미스트 블루 */
    color: #222;
    padding: 10px 0; /* 내부 여백 */
    text-align: center;
    border-bottom: none;
    position: relative; /* 내부 요소 배치 기준 */
    height: 90px; /* 헤더 높이 */
    display: flex;
    align-items: center;
    justify-content: center; /* 중앙 정렬 */
}


/* 사이트 제목 (LUMITICKETING) */
.site-title {
    font-size: 24px;
    color: #222;
    margin: 0;
    position: absolute;
    left: 50%;
    top: 50%; /* 헤더의 정중앙 */
    transform: translate(-50%, -50%); /* 수평 + 수직 중앙 정렬 */
}

.user-info {
    font-size: 14px;
    color: #555;
    position: absolute;
    right: 20px; /* 오른쪽 정렬 */
    bottom: 10px; /* 헤더 내부 하단으로 이동 */
    text-align: right;
    white-space: nowrap; /* 줄바꿈 방지 */
}

/* 사이트 제목 (LUMITICKETING) */
.site-title {
    font-size: 26px;
    color: #222;
    margin: 0;
    position: absolute;
    left: 50%;
    transform: translateX(-50%); /* 🔹 정확한 중앙 정렬 */
}


    .site-title {
    font-size: 26px;
    color: #222;
}

    

   /* 네비게이션 바 */
   .nav-container {
        background-color: #F1F5F9;
        padding: 12px 0;
        text-align: center;
    }

   .nav-container a {
        color: #444;
        font-size: 13.5px;
        text-decoration: none;
        margin: 0 18px; /* 여백 추가 */
        transition: color 0.3s;
    }

   .nav-container a:hover {
        color: #000;
    }

   /* 드롭다운 메뉴 스타일 */
   .dropdown-content {
      display: none;
      position: absolute;
      background-color: white;
      box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
      min-width: 150px;
      z-index: 10;
      left: 0;
      top: 25px;
   }

   .dropdown-content a {
      display: block;
      padding: 10px;
      color: black;
      text-align: left;
      white-space: nowrap;
   }

   .dropdown-content a:hover {
      background-color: #f1f1f1;
   }
</style>

<script src="dbQuiz.js"></script>
<script>
   function toggleDropdown() {
      var dropdown = document.getElementById("memberDropdown");
      if (dropdown.style.display === "block") {
         dropdown.style.display = "none";
      } else {
         dropdown.style.display = "block";
      }
   }

   // 클릭 외부 감지하여 닫기
   document.addEventListener("click", function(event) {
      var dropdown = document.getElementById("memberDropdown");
      var menu = document.getElementById("memberMenu");

      if (!menu.contains(event.target)) {
         dropdown.style.display = "none";
      }
   });
</script>

<div class="header-container">
    <h1 class="site-title">LUMITICKETING VIP</h1>
    
    <c:choose>
    <c:when test="${not empty sessionScope.loginUser}">
        <div class="user-info">
            ${sessionScope.loginUser.id}님 환영합니다! 등급: ${sessionScope.loginUser.membership}
        </div>
    </c:when>
    <c:otherwise>
        <div class="user-info">
            로그인해주세요.
        </div>
    </c:otherwise>
</c:choose>


</div>

<c:url var="context" value="/"/>
<div align="right">
   <!-- 네비게이션 바 -->
<c:url var="context" value="/"/>
<div class="nav-container">
   <ul>
   <li><a href="${context}index">HOME</a></li>

<li><a href="${context}logout">Logout</a></li>

<c:if test="${sessionScope.loginUser.membership == 'admin'}">
   <li id="memberMenu" onclick="toggleDropdown()">MemberInfo
      <div id="memberDropdown" class="dropdown-content">
         <a href="${context}memberInfoRegular">Regular Member</a>
         <a href="${context}memberInfoVIP">VIP Member</a>
      </div>
   </li>
   <li><a href="${context}ticketHolder">Ticket Holder</a></li>
</c:if>

</ul>

</div>






