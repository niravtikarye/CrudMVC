<%-- 
    Document   : dashboard
    Created on : 17-Feb-2026, 10:51:03 pm
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout pageTitle="Dashboard">
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
</t:layout>
