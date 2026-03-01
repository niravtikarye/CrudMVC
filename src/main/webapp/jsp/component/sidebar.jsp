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
        <h3 class="title">CORE</h3>
        <button class="toggle-btn" onclick="toggleSidebar()">☰</button>
    </div>

    <ul class="menu">
        <li class="${fn:contains(currentPath, '/file') ? 'active' : ''}">
            <span>📂</span> <a href="${contextPath}/">File Problem</a>
        </li>
        <li class="${fn:contains(currentPath, '/solver') ? 'active' : ''}">
            <span>👥</span> <a href="${contextPath}/solver-list">Solver List</a>
        </li>
        <li><span>⑧</span> <a href="explore">Explore</a></li>
        <li><span>🔍</span> <a href="#">Search</a></li>
        <li><span>+</span> <a href="create">Create</a></li>
    </ul>

    <div class="bottom">
        <li style="list-style:none; padding: 15px 25px;">
            <span>⚙️</span> <a href="#" style="color:white; text-decoration:none; margin-left:15px;">Settings</a>
        </li>
    </div>
</div>

<!-- MOBILE SIDEBAR -->
<div class="mobile-bottom-nav">
    <a href="${contextPath}/" class="nav-item ${fn:contains(currentPath, '/') ? 'active' : ''}">
        <span>🏠</span>
    </a>

    <a href="${contextPath}/solver-list" class="nav-item ${fn:contains(currentPath, '/solver') ? 'active' : ''}">
        <span>👥</span>
    </a>

    <a href="#" class="nav-item">
        <span>➕</span>
    </a>
    <a href="#" class="nav-item">
        <span>🔍</span>
    </a>

    <a href="#" class="nav-item">
        <span>⚙️</span>
    </a>
</div>

<script>
    document.querySelectorAll('.menu a').forEach(link => {
        if (link.href === window.location.href) {
            link.parentElement.classList.add('active');
        }
    });
</script>