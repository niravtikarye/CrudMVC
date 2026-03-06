<%-- 
    Document   : get-solver-list
    Created on : 07-Mar-2026, 12:04:32 am
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout pageTitle="SolverList">
    <c:forEach var="user" items="${userList}">
        <%@include file="component/UserList.jsp" %>
    </c:forEach>
</t:layout>
