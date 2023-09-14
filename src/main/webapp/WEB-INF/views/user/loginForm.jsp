<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">

    <form action="/auth/loginProc" , method="POST">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" name="username" class="form-control" id="username" aria-describedby="usernameHelp">
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" name="password" class="form-control" id="password">
        </div>

        <button id="btn-login" class="btn btn-primary">로그인</button>
        <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=c61f08f62473c3801b979b56fa3028de&redirect_uri=http://localhost:8000/auth/kakao/callback"><img
                height="38px" src="/image/kakao_login_button.png"></a>
    </form>

</div>

<%@ include file="../layout/footer.jsp" %>
