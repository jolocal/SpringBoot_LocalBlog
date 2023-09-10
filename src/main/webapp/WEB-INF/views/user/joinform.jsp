<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">

    <%--<form action="/user/join" method="POST"> : 이전 방식--%>
    <form action="/user/join" method="POST">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control" id="username" aria-describedby="usernameHelp">
            <div id="usernameHelp" class="form-text">Enter Username</div>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password">
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" aria-describedby="emailHelp">
            <div id="emailHelp" class="form-text">Enter your email</div>
        </div>
    </form>

    <button id="btn-save" class="btn btn-primary">회원가입완료</button>

</div>
<%-- /는 static을 찾아감, user.js 파일을 참조--%>
<script src="/blog/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>
