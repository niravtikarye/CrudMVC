<%-- Document : sidebar.jsp Created on : 18-Feb-2026, 8:59:55?pm Author : admin --%>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                <c:set var="contextPath" value="${pageContext.request.contextPath}" />
                <c:set var="currentPath" value="${pageContext.request.requestURI}" />

                <div class="sidebar" id="sidebar">
                    <div class="sidebar-header">
                        <h3 class="title">Civic Solve</h3>
                        <button class="toggle-btn" onclick="toggleSidebar()">☰</button>
                    </div>

                    <ul class="menu">
                        <li class="nav-links">
                            <span>🏠</span> <a href="${contextPath}/explore">Explore</a>
                        </li>
                        <c:if test="${requestScope.loggedInUser != null && requestScope.loggedInUser.role == 'Citizen'}">
                            <li class="nav-links">
                                <span>➕</span> <a href="${contextPath}/createProblem">Create Issue</a>
                            </li>
                        </c:if>
                        <c:if test="${requestScope.loggedInUser != null}">
                            <li class="nav-links">
                                <span>👤</span> <a href="${contextPath}/profile">My Profile</a>
                            </li>
                        </c:if>
                    </ul>

                    <div class="bottom" style="padding: 15px 25px;">
                        <c:choose>
                            <c:when test="${requestScope.loggedInUser != null}">
                                <div style="display:flex; flex-direction:column; gap:10px;">
                                    <span style="color:var(--text-secondary); font-size:0.85rem;">Logged in as ${requestScope.loggedInUser.name}</span>
                                    <a href="${contextPath}/api/auth/logout" style="color:#ff4d4d; text-decoration:none; display:flex; align-items:center; gap:10px;">
                                        <span>🚪</span> Logout
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div style="display:flex; flex-direction:column; gap:10px;">
                                    <a href="${contextPath}/login" style="color:var(--primary-color); text-decoration:none; font-weight:bold;">Login</a>
                                    <a href="${contextPath}/register" style="color:white; text-decoration:none;">Register</a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- MOBILE SIDEBAR -->
                <div class="mobile-bottom-nav">
                    <a href="${contextPath}/explore" class="nav-item">
                        <span>🏠</span>
                    </a>
                    
                    <c:if test="${requestScope.loggedInUser != null && requestScope.loggedInUser.role == 'Citizen'}">
                        <a href="${contextPath}/createProblem" class="nav-item">
                            <span>➕</span>
                        </a>
                    </c:if>

                    <c:if test="${requestScope.loggedInUser != null}">
                        <a href="${contextPath}/profile" class="nav-item">
                            <span>👤</span>
                        </a>
                    </c:if>
                </div>

                </div>