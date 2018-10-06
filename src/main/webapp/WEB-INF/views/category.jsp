<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 004 04.10.18
  Time: 18:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Category</title>
</head>
<body>
<h1>category ${category.name}</h1>
<c:forEach var = "p" items="${category.products}">
    <h3>Product name: <a href="<c:url value="/servlet/product?p_id=${p.id}"/>"><c:out value="${p.name}"/></a> ${p.price}</h3>
</c:forEach>
</body>
</html>