<%-- Document : sidebar.jsp Created on : 18-Feb-2026, 8:59:55?pm Author : admin --%>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                <c:set var="contextPath" value="${pageContext.request.contextPath}" />
                <c:set var="currentPath" value="${pageContext.request.requestURI}" />

                <div class="sidebar" id="sidebar">
                    <div class="sidebar-header">
                        <h3 class="title">CORE</h3>
                        <button class="toggle-btn" onclick="toggleSidebar()">☰</button>
                    </div>

                    <ul class="menu">
                        <li class="nav-links">
                            <span>📂</span> <a href="${contextPath}/">File Problem</a>
                        </li>
                        <li class="nav-links">
                            <span>👥</span> <a href="${contextPath}/solver-list">Solver List</a>
                        </li>
                        <li class="nav-links">
                            <span>⑧</span> <a href="${contextPath}/explore">Explore</a>
                        </li>
                        <li class="nav-links">
                            <span>🔍</span> <a href="#">Search</a>
                        </li>
                        <li class="nav-links">
                            <span>+</span> <a href="${contextPath}/createProblem">Create</a>
                        </li>
                    </ul>

                    <div class="bottom">
                        <li style="list-style:none; padding: 15px 25px;">
                            <span>⚙️</span> <a href="#"
                                style="color:white; text-decoration:none; margin-left:15px;">Settings</a>
                        </li>
                    </div>
                </div>

                <!-- MOBILE SIDEBAR -->
                <div class="mobile-bottom-nav">
                    <a href="${contextPath}/" class="nav-item">
                        <span>🏠</span>
                    </a>

                    <a href="${contextPath}/solver-list" class="nav-item">
                        <span>👥</span>
                    </a>

                    <a href="${contextPath}/createProblem" class="nav-item">
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
                    document.addEventListener("DOMContentLoaded", function () {
                        const currentUrl = window.location.pathname; // e.g. /CrudMVC/explore or /explore
                        const contextPath = '${contextPath}';      // e.g. /CrudMVC or empty

                        // Normalize current path by stripping contextPath if it exists so we just compare the route (e.g. "/" or "/explore")
                        let route = currentUrl;
                        if (contextPath && currentUrl.startsWith(contextPath)) {
                            route = currentUrl.substring(contextPath.length);
                        }
                        if (route === '') route = '/';

                        // Function to determine if a link matches the current route
                        function isLinkActive(linkHref) {
                            try {
                                const linkPath = new URL(linkHref).pathname;

                                let linkRoute = linkPath;
                                if (contextPath && linkPath.startsWith(contextPath)) {
                                    linkRoute = linkPath.substring(contextPath.length);
                                }
                                if (linkRoute === '') linkRoute = '/';

                                // Exact match only for root "/" to prevent it lighting up constantly
                                if (linkRoute === '/') {
                                    return route === '/';
                                }

                                // For other routes, check if current route starts with it (e.g. /explore/123 starts with /explore)
                                return route.startsWith(linkRoute);
                            } catch (e) {
                                return false;
                            }
                        }

                        // Desktop Menu
                        const menuLinks = document.querySelectorAll('.menu a');
                        menuLinks.forEach(link => {
                            // Skip empty or hash links like Search '#'
                            if (link.getAttribute('href') === "#" || !link.getAttribute('href')) return;

                            if (isLinkActive(link.href)) {
                                link.closest('li').classList.add('active');
                            } else {
                                link.closest('li').classList.remove('active');
                            }
                        });

                        // Mobile Menu
                        const mobileLinks = document.querySelectorAll('.mobile-bottom-nav a');
                        mobileLinks.forEach(link => {
                            if (link.getAttribute('href') === "#" || !link.getAttribute('href')) return;

                            if (isLinkActive(link.href)) {
                                link.classList.add('active');
                            } else {
                                link.classList.remove('active');
                            }
                        });
                    });
                </script>