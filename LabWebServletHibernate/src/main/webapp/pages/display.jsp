<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/table.css" />
<title>Display</title>
</head>
<body>

<h3>Select Product Table Result : ${fn:length(select)} row(s) selected</h3>

<c:if test="${not empty select}">
<table>
	<thead>
	<tr>
		<th>ID</th>
		<th>Name</th>
		<th>Price</th>
		<th>Make</th>
		<th>Expire</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="obj" items="${select}">
		<c:url value="/pages/product.jsp" var="link" scope="page">
			<c:param name="id" value="${obj.id}" />
			<c:param name="name" value="${obj.name}" />
			<c:param name="price" value="${obj.price}" />
			<c:param name="make" value="${obj.make}" />
			<c:param name="expire" value="${obj.expire}" />
		</c:url>
	<tr>
		<td>${obj.id}</td>
		<td><a href="${link}">${obj.name}</a></td>
		<td>${obj.price}</td>
		<td>${obj.make}</td>
		<td>${obj.expire}</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
</c:if>

<h3><a href="<c:url value="/pages/product.jsp" />">Product Table</a></h3>
</body>
</html>