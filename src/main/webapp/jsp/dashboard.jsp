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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css"/>
    </head>
    <body>

        <div class="overlay" id="overlay" onclick="closeMobileSidebar()"></div>

        <%@ include file="component/sidebar.jsp" %>

        <div class="main-content">

            <h1 class="page-title">List of problems</h1>

            <!-- Card Grid -->
            <div class="card-grid">
                <div class="cards-section">
                    <c:forEach var="problem" items="${problemList}">
                        <%@ include file="component/ProblemCard.jsp" %>
                    </c:forEach>
                </div>
                <div class="userlist-section">
                    <div class="suggest-container">
                        <!-- Profile -->
                        <div class="suggest-profile">
                            <div class="suggest-profile-left">
                                <img src="images/profile.jpg" alt="profile">
                                <div class="suggest-username">therudra.29</div>
                            </div>
                            <div class="suggest-switch">Switch</div>
                        </div>

                        <!-- Suggested Header -->
                        <div class="suggest-header">
                            <span>Suggested for you</span>
                            <div class="suggest-see-all">See All</div>
                        </div>
                        <c:forEach var="user" items="${userList}">
                            <%@include file="component/UserList.jsp" %>
                        </c:forEach>
                        <!-- Footer -->
                        <div class="suggest-footer">
                            About · Help · Press · API · Jobs · Privacy · Terms <br>
                            Locations · Language · Meta Verified
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/resources/js/mainLayout.js"></script>

    </body>
</html>
