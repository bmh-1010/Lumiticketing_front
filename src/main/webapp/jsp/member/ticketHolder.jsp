<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.loginUser == null || sessionScope.loginUser.membership ne 'admin'}">
    <script>
        alert("관리자만 접근 가능한 페이지입니다!");
        window.location.href = "index";
    </script>
</c:if>

<c:import url="/header" />

<div align="center">
    <font color="red">${msg}</font>
    <h1>티켓 홀더 목록</h1>

    <c:choose>
        <c:when test="${empty tickets}">
            <h1>등록된 티켓이 없습니다.</h1>
        </c:when>
        <c:otherwise>
            <table id="ticketTable" border="1" style="border-collapse: collapse; width: 50%; text-align: center; margin-top: 20px;">
                <thead>
                    <tr>
                        <th>티켓 ID</th>
                        <th>소유자</th>
                        <th>회원등급</th>
                        <th>티켓 번호</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ticket" items="${tickets}">
                        <tr>
                            <td>${ticket.id}</td>
                            <td>${ticket.userName}</td>  
                            <td>${ticket.membership}</td>
                            <td>${ticket.ticketNumber}</td>  
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- 페이지네이션 버튼 추가 -->
            <div id="pagination-container">
                <div id="pagination"></div>
            </div>

        </c:otherwise>
    </c:choose>

    <form action="ticketHolder" method="get">
        <input type="hidden" name="searchType" value="아이디">
        <input type="text" name="keyword" placeholder="아이디 입력">
        <button type="submit">검색</button>
    </form>

</div>

<!-- jQuery 추가 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- CSS 추가 -->
<style>
    #pagination-container {
        margin-top: 20px;  /* 페이지네이션과 테이블 사이 간격 추가 */
    }
    #pagination button {
        margin: 5px;
        padding: 8px 12px;
        border: 1px solid #ddd;
        background-color: #f8f9fa;
        cursor: pointer;
        font-size: 14px;
    }
    #pagination button:hover {
        background-color: #e9ecef;
    }
    #pagination button.active {
        font-weight: bold;
        background-color: #007bff;
        color: white;
    }
</style>

<script>
    $(document).ready(function() {
        // 페이지네이션 기능 추가
        const rowsPerPage = 10;
        const table = document.getElementById("ticketTable").getElementsByTagName("tbody")[0];
        const rows = table.getElementsByTagName("tr");
        const totalRows = rows.length;
        const totalPages = Math.ceil(totalRows / rowsPerPage);
        let currentPage = 1;

        function showPage(page) {
            const start = (page - 1) * rowsPerPage;
            const end = start + rowsPerPage;

            // 모든 행 숨기기
            for (let i = 0; i < totalRows; i++) {
                rows[i].style.display = (i >= start && i < end) ? "table-row" : "none";
            }

            updatePagination();
        }

        function updatePagination() {
            const paginationDiv = document.getElementById("pagination");
            paginationDiv.innerHTML = "";

            // 이전 버튼
            if (currentPage > 1) {
                const prevBtn = document.createElement("button");
                prevBtn.innerText = "이전";
                prevBtn.onclick = function() {
                    currentPage--;
                    showPage(currentPage);
                };
                paginationDiv.appendChild(prevBtn);
            }

            // 숫자 버튼
            for (let i = 1; i <= totalPages; i++) {
                const pageBtn = document.createElement("button");
                pageBtn.innerText = i;
                pageBtn.classList.add("page-btn");
                if (i === currentPage) {
                    pageBtn.classList.add("active");
                }
                pageBtn.onclick = function() {
                    currentPage = i;
                    showPage(currentPage);
                };
                paginationDiv.appendChild(pageBtn);
            }

            // 다음 버튼
            if (currentPage < totalPages) {
                const nextBtn = document.createElement("button");
                nextBtn.innerText = "다음";
                nextBtn.onclick = function() {
                    currentPage++;
                    showPage(currentPage);
                };
                paginationDiv.appendChild(nextBtn);
            }
        }

        // 초기 페이지 로딩
        showPage(1);
    });
</script>

<c:import url="/footer" />

