<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<table border="1">
			<tr>
				<td>图书编号</td>
				<td>当前价格</td>
				<td>图书作者</td>
			</tr>
			<c:forEach items="${blist}" var="bo">
				<tr>
					<td>${bo.bid}</td>
					<td>${bo.currPrice}</td>
					<td>${bo.author}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="3">
					<a href="/goods/testAction?flag=paging&pageNumber=1">首页</a>
					<c:if test="${pageNumber>1}">
						<a href="/goods/testAction?flag=paging&pageNumber=${pageNumber-1}">上一页</a>
					</c:if>
					<c:if test="${pageNumber<totalPages }">
						<a href="/goods/testAction?flag=paging&pageNumber=${pageNumber+1}">下一页</a>
					</c:if>
					
					<a href="/goods/testAction?flag=paging&pageNumber=${totalPages}">尾页</a>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>