<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="layout/header.jsp" %>

<div class="container">

    <c:forEach var="board" items="${boards.content}">
    <div class="card m-3">
        <div class="card-body">
            <h5 class="card-title">${board.title}</h5>
            <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
        </div>
    </div>
    </c:forEach>

    <%--pagenation--%>
    <nav aria-label="...">
        <%--justify-content-cente: bootstrap flex 가운데정렬 문법--%>
        <ul class="pagination justify-content-center">
            <%--Previous--%>
            <c:choose>
                <c:when test="${boards.first}">
                    <li class="page-item disabled">
                        <a class="page-link" href="?page=${boards.number-1}">Previous</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="?page=${boards.number-1}">Previous</a>
                    </li>
                </c:otherwise>
            </c:choose>

            <%--page 순번--%>
            <c:forEach var="i" begin="1" end="${boards.totalPages}">
                <li class="page-item"><a class="page-link" href="?page=${i-1}">${i}</a></li>
            </c:forEach>

            <%--Next--%>
            <c:choose>
                <c:when test="${boards.last}">
                    <li class="page-item disabled">
                        <a class="page-link" href="?page=${boards.number+1}">Next</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="?page=${boards.number+1}">Next</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</div>

<%@ include file="layout/footer.jsp" %>
