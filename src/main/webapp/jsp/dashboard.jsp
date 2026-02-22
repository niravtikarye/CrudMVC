<%-- 
    Document   : dashboard
    Created on : 17-Feb-2026, 10:51:03 pm
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mainLayout.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/card-layout.css"/>
    </head>
    <body>

        <div class="overlay" id="overlay" onclick="closeMobileSidebar()"></div>

        <%@ include file="component/sidebar.jsp" %>
        
        <div class="main-content">

            <h1 class="page-title">List of problems</h1>

            <!-- Card Grid -->
            <div class="card-grid">
                <c:forEach var="problem" items="${problemList}">
                   <%@ include file="component/ProblemCard.jsp" %>
                </c:forEach>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/resources/js/mainLayout.js"></script>

    </body>
</html>
