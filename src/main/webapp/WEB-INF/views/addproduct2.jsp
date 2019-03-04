<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2/18/2019
  Time: 4:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;
charset=ISO-8859-1">
    <link rel="stylesheet"
          href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <title>Products</title>
</head>
<body>
<section>
    <div class="pull-right" style="padding-right:50px">
        <a href="?language=en">English</a>|<a href="?language=nl">Dutch</a>
        <a href="<c:url value="/logout" />">Logout</a>
    </div>
</section>
<section>
    <div class="jumbotron">
        <div class="container">
            <h1>Products</h1>
            <p>Add products</p>
        </div>
    </div>
</section>
<section class="container">
    <form:form method="POST" modelAttribute="newProduct" class="form-horizontal" enctype="multipart/form-data">
        <form:errors path="*" cssClass="alert alert-danger" element="div"/>
        <fieldset>
            <legend>Add new product</legend>
            <div class="form-group">
                    <%--<label class="control-label col-lg-2 col-lg-2"--%>
                    <%--for="productId">Product Id</label>--%>
                <label class="control-label col-lg-2 col-lg-2" for="productId">
                    <spring:message code="addProduct.form.productId.label"/> </label>
                <div class="col-lg-10">
                    <form:input id="productId" path="productId"
                                type="text" class="form:input-large"/>
                    <form:errors path="productId" cssClass="text-danger"/>
                </div>
            </div>
                <%--<legend>Name</legend>--%>
            <div class="form-group">
                    <%--<label class="control-label col-lg-2 col-lg-2"--%>
                    <%--for="productId">Product Id</label>--%>
                <label class="control-label col-lg-2 col-lg-2" for="name">
                    <spring:message code="addProduct.form.productId.name"/> </label>
                <div class="col-lg-10">
                    <form:input id="name" path="name" type="text" class="form:input-large"/>
                    <form:errors path="name" cssClass="text-danger"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="unitPrice">
                    <spring:message code="addProduct.form.productId.unitPrice"/> </label>
                <div class="col-lg-10">
                    <form:input id="unitPrice" path="unitPrice" type="text" class="form:input-large"/>
                    <form:errors path="unitPrice" cssClass="text-danger"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="manufacturer">
                    <spring:message code="addProduct.form.productId.manufacturer"/> </label>
                <div class="col-lg-10">
                    <form:input id="manufacturer" path="manufacturer" type="text" class="form:input-large"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="category">
                    <spring:message code="addProduct.form.productId.category"/> </label>
                <div class="col-lg-10">
                    <form:input id="category" path="category" type="text" class="form:input-large"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="unitsInStock">
                    <spring:message code="addProduct.form.productId.unitsInStock"/> </label>
                <div class="col-lg-10">
                    <form:input id="unitsInStock" path="unitsInStock" type="text" class="form:input-large"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="unitsInOrder">
                    <spring:message code="addProduct.form.productId.unitsInOrder"/> </label>
                <div class="col-lg-10">
                    <form:input id="unitsInOrder" path="unitsInOrder" type="text" class="form:input-large"/>
                </div>
            </div>

            <!-- Similarly bind  tag for name,unitPrice,manufacturer,category,unitsInStockand unitsInOrder fields-->
            <div class="form-group">
                <label class="control-label col-lg-2"
                       for="description">Description</label>
                <div class="col-lg-10">
                    <form:textarea id="description"
                                   path="description" rows="2"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-lg-2"
                       for="condition">Condition</label>
                <div class="col-lg-10">
                    <form:radiobutton path="condition"
                                      value="New"/>New
                    <form:radiobutton path="condition"
                                      value="Old"/>Old
                    <form:radiobutton path="condition"
                                      value="Refurbished"/>Refurbished
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2" for="productImage">
                    <spring:message code="addProduct.form.productImage.label"/>
                </label>
                <div class="col-lg-10">
                    <form:input id="productImage" path="productImage" type="file" class="form:input-large"/>
                </div>
                    <%----%>
                    <%--</div>--%>
                <div class="form-group">
                    <label class="control-label col-lg-2" for="productManual">
                        <spring:message code="addProduct.form.productManual.label"/>
                    </label>
                    <div class="col-lg-10">
                        <form:input id="productManual" path="productManual" type="file" class="form:input-large"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-offset-2 col-lg-10">
                        <input type="submit" id="btnAdd" class="btn btn-primary" value="Add"/>
                    </div>
                </div>
        </fieldset>
    </form:form>
</section>
</body>
</html>