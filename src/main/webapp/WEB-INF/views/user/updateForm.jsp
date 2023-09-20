<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">

    <%--<form action="/user/join" method="POST">--%>
    <form id="profileUpdate" onsubmit="update(${principal.user.id},event)">
        <%--<input type="hidden" id="id" value="${principal.user.id}">--%>
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text"  value="${principal.user.username}" name="username" class="form-control" id="username" aria-describedby="usernameHelp" required>
            <div id="usernameHelp" class="form-text">Enter Username</div>
        </div>

        <c:if test="${empty principal.user.oauth}">
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" name="password" class="form-control" id="password" required>
            </div>
        </c:if>

        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" value="${principal.user.email}" name="email" class="form-control" id="email" aria-describedby="emailHelp">
            <div id="emailHelp" class="form-text">Enter your email</div>
        </div>
        <button>회원수정완료</button>
    </form>
    <%--<button id="btn-update" class="btn btn-primary">회원수정완료</button>--%>


</div>
<%-- /는 static을 찾아감, user.js 파일을 참조--%>
<script src="/js/update.js"></script>
<%@ include file="../layout/footer.jsp" %>
