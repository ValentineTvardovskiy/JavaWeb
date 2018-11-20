<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp" %>

<div class="container2">

    <div class="mt-5">
        <div class="row mb-3">
            <div class="col-md-2">
                <h4>Product Name</h4>
            </div>
            <div class="col-md-2">
                <h4>Price</h4>
            </div>
            <div class="col-md-2">
                <h4>Category Name</h4>
            </div>
        </div>
        <c:forEach var="p" items="${products}">
            <div class="row">
                <div class="col-md-2">
                    <h3><c:out value="${p.name}"/></h3>
                </div>
                <div class="col-md-2">
                    <h3><c:out value="${p.price}"/></h3>
                </div>
                <div class="col-md-2">
                    <h3><c:out value="${p.category.name}"/></h3>
                </div>
                <div class="col-md-4">
                    <a href="<c:url value="/servlet/admin/edit-product?c_id=${p.id}"/>" class="btn btn-outline-warning my-2 my-sm-0 ml-2">Edit</a>
                    <a href="<c:url value="/servlet/admin/delete-product?c_id=${p.id}"/>" class="btn btn-outline-danger my-2 my-sm-0 ml-2">Delete</a>
                </div>
            </div>
        </c:forEach>

    </div>

    <a href="<c:url value="/servlet/admin/add-product"/>" class="btn btn-outline-success my-2 my-sm-0">Add Product</a>

</div>

</body>
</html>
