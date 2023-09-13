<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form>
        <div class="mb-3">
            <input type="text" class="form-control" id="title" placeholder="Enter Title">
        </div>

        <div class="mb-3">
            <textarea class="form-control summernote" id="content" rows="5"></textarea>
        </div>
    </form>
    <button id="btn-save" class="btn btn-primary">글쓰기 완료</button>

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

