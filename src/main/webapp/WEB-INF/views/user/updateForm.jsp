<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">

    <%--<form action="/user/join" method="POST"> : 이전 방식--%>
    <form action="/user/join" method="POST">
        <input type="hidden" id="id" value="${principal.user.id}">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text"  value="${principal.user.username}" name="username" class="form-control" id="username" aria-describedby="usernameHelp" readonly>
            <div id="usernameHelp" class="form-text">Enter Username</div>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" name="password" class="form-control" id="password">
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" value="${principal.user.email}" name="email" class="form-control" id="email" aria-describedby="emailHelp">
            <div id="emailHelp" class="form-text">Enter your email</div>
        </div>
    </form>

    <button id="btn-update" class="btn btn-primary">회원수정완료</button>

</div>
<%-- /는 static을 찾아감, user.js 파일을 참조--%>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>
