<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">

    <form>
        <input type="hidden" id="id" value="${board.id}">
        <div class="mb-3">
            <input value="${board.title}" type="text" class="form-control" id="title" placeholder="Enter Title">
        </div>

        <div class="mb-3">
            <textarea class="form-control summernote" id="content" rows="5">${board.content}</textarea>
        </div>
    </form>
    <button id="btn-update" class="btn btn-primary">글수정완료</button>
</div>
<script>
    // summernote
    $('.summernote').summernote({
        tabsize: 2,
        height : 300,
        lang   : 'ko-KR', // default: 'en-US'
    });
</script>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>

