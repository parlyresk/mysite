<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    pageContext.setAttribute("newline", "\n");
%>
<html>
<head>
    <title>mysite</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div id="container">
        <c:import url="/WEB-INF/views/includes/header.jsp" />
        <div id="content">
            <div id="board" class="board-form">
                <table class="tbl-ex">
                    <tr>
                        <th colspan="2">글보기</th>
                    </tr>
                    <tr>
                        <td class="label">제목</td>
                        <td>${boardVo.title}</td>
                    </tr>
                    <tr>
                        <td class="label">내용</td>
                        <td>
                            <div class="view-content">${fn:replace(boardVo.contents, newline, '<br/>')}</div>
                        </td>
                    </tr>
                </table>
                <div class="bottom">
                    <a href="${pageContext.request.contextPath}/board">글목록</a>
                    <sec:authorize access="isAuthenticated()">
                        <sec:authorize access="authentication.principal.no == ${boardVo.userNo}">
                            <a href="${pageContext.request.contextPath}/board/modify?no=${boardVo.no}&page=${page}">글수정</a>
                        </sec:authorize>
                        <a href="${pageContext.request.contextPath}/board/reply?no=${boardVo.no}&page=${page}">답글작성</a>
                    </sec:authorize>
                </div>
            </div>
        </div>
        <c:import url="/WEB-INF/views/includes/navigation.jsp">
            <c:param name="menu" value="board" />
        </c:import>
        <c:import url="/WEB-INF/views/includes/footer.jsp" />
    </div>
</body>
</html>
