<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search"
					action="${pageContext.request.contextPath}/board?page=1" method="post">
					<input type="text" id="keyword" name="keyword" value="${keyword}">
					
					<input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach var="boardVo" items="${list}" varStatus="status">
						<tr>
							<td>${totalPosts-status.index-(currentPage-1)*postsPerPage}</td>
							<td style="text-align:left; padding-left:${20 * boardVo.depth}px">
								<c:if test="${boardVo.depth > 0}">
									<img
										src='${pageContext.servletContext.contextPath}/assets/images/reply.png'>
								</c:if> <a
								href="${pageContext.request.contextPath}/board/view?no=${boardVo.no}&page=${currentPage}&keyword=${keyword}">${boardVo.title}</a>
							</td>
							<td>${boardVo.userName}</td>
							<td>${boardVo.hit}</td>
							<td>${boardVo.regDate}</td>
							<td><c:if
									test="${sessionScope.authUser.no eq boardVo.userNo}">
									<a
										href="${pageContext.request.contextPath}/board/delete?no=${boardVo.no}&page=${currentPage}&keyword=${keyword}"
										class="del">삭제</a>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${currentPage > 1}">
							<li><a href="${pageContext.request.contextPath }/board?page=${currentPage - 1}&keyword=${keyword}">◀</a></li>
						</c:if>
						
						<c:forEach var="i" begin="${currentNavStart}" end="${currentNavEnd}">
							<c:choose>
								<c:when test="${i <= totalPages}">
									<li class="${i == currentPage ? 'selected' : ''}"><a
										href="${pageContext.request.contextPath }/board?page=${i}&keyword=${keyword}">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li>${i}</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${currentPage < totalPages}">
							<li><a href="${pageContext.request.contextPath }/board?page=${currentPage + 1}&keyword=${keyword}">▶</a></li>
						</c:if>

					</ul>
				</div>
				<!-- pager 추가 -->

				<div class="bottom">
					<c:if test="${not empty sessionScope.authUser}">
						<a
							href="${pageContext.request.contextPath}/board/write?page=${currentPage}&keyword=${keyword}"
							id="new-book">글쓰기</a>
					</c:if>
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