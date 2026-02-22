<%-- 
    Document   : sidebar.jsp
    Created on : 18-Feb-2026, 8:59:55?pm
    Author     : admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="currentPath" value="${pageContext.request.requestURI}" />

<div class="sidebar" id="sidebar">

    <div class="sidebar-header">
        <h3 class="title">Menu</h3>
        <button class="toggle-btn" onclick="toggleSidebar()">◲</button>
    </div>

    <ul class="menu">
        <li class="${(currentPath == contextPath || currentPath == contextPath.concat('/')) ? 'active' : ''}">
            <a href="${contextPath}/">File Problem</a>
        </li>
        <li class="${currentPath == contextPath.concat('/solver-list') ? 'active' : ''}">
            <a href="${contextPath}/solver-list">Solver List</a>
        </li>
        <li><span>Explore</span></li>
        <li><span>Solved Problem</span></li>
    </ul>

    <div class="bottom">
        <span>Settings</span>
    </div>

</div>

<script>
    document.querySelectorAll('.menu a').forEach(link => {
        if (link.href === window.location.href) {
            link.parentElement.classList.add('active');
        }
    });
</script>