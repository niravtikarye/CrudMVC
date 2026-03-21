<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

            <t:layout pageTitle="My Profile">
                <style>
                    .profile-container {
                        padding: 20px;
                        max-width: 1000px;
                        margin: 0 auto;
                        padding-bottom: 60px;
                    }

                    .profile-header {
                        display: flex;
                        align-items: center;
                        gap: 20px;
                        background: var(--bg-color);
                        padding: 20px;
                        border-radius: 8px;
                        border: 1px solid var(--border-color);
                        margin-bottom: 20px;
                    }

                    .tabs-header {
                        display: flex;
                        gap: 10px;
                        margin-bottom: 20px;
                        border-bottom: 1px solid var(--border-color);
                        overflow-x: auto;
                        white-space: nowrap;
                        padding-bottom: 5px;
                    }

                    .tab-btn {
                        background: none;
                        border: none;
                        padding: 10px 20px;
                        font-size: 1rem;
                        cursor: pointer;
                        color: var(--text-secondary);
                        border-bottom: 3px solid transparent;
                        font-weight: bold;
                        transition: all 0.3s;
                    }

                    .tab-btn.active {
                        color: var(--primary-color);
                        border-bottom: 3px solid var(--primary-color);
                    }

                    .tab-btn:hover {
                        color: var(--primary-color);
                    }

                    /* Grid Layout instead of horizontal column */
                    .cards-grid {
                        display: grid;
                        grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
                        gap: 20px;
                        align-items: start;
                    }

                    .tab-content {
                        display: none;
                    }

                    .tab-content.active {
                        display: block;
                    }

                    .empty-state {
                        text-align: center;
                        color: var(--text-secondary);
                        padding: 60px 20px;
                        background: var(--bg-color);
                        border: 1px solid var(--border-color);
                        border-radius: 8px;
                        grid-column: 1 / -1;
                    }

                    .empty-state svg {
                        width: 50px;
                        height: 50px;
                        color: var(--text-secondary);
                        margin-bottom: 10px;
                    }
                </style>

                <div class="profile-container">
                    <div class="profile-header">
                        <img src="https://ui-avatars.com/api/?name=${user.name}&background=random&color=fff"
                            alt="Avatar" style="width:80px; height:80px; border-radius:50%; object-fit:cover;">
                        <div>
                            <h1 style="margin: 0 0 5px 0; font-size: 1.5rem;">${user.name}</h1>
                            <p style="margin: 0; color: var(--text-secondary); text-transform: capitalize;">
                                @${user.username} &bull; Role: ${user.role}</p>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${user.role == 'citizen'}">
                            <h2
                                style="font-size: 1.2rem; margin-bottom: 15px; padding-left: 10px; border-left: 4px solid var(--primary-color);">
                                ${feedType}</h2>
                            <div class="cards-grid">
                                <c:choose>
                                    <c:when test="${not empty problems}">
                                        <c:forEach var="problem" items="${problems}">
                                            <%@ include file="component/ProblemCard.jsp" %>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="empty-state">
                                            <svg fill="none" stroke="currentColor" viewBox="0 0 24 24"
                                                xmlns="http://www.w3.org/2000/svg">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4">
                                                </path>
                                            </svg>
                                            <p>No issues found here yet.</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:when>

                        <c:otherwise>
                            <!-- SOLVER PROFILE TABS -->
                            <div class="tabs-header">
                                <button class="tab-btn active" onclick="switchProfileTab('tab-assigned', this)">Assigned
                                    to Me</button>
                                <button class="tab-btn" onclick="switchProfileTab('tab-available', this)">Available
                                    Problems</button>
                                <button class="tab-btn" onclick="switchProfileTab('tab-solved', this)">Solved by
                                    Me</button>
                            </div>

                            <!-- ASSIGNED TAB -->
                            <div id="tab-assigned" class="tab-content active">
                                <div class="cards-grid">
                                    <c:choose>
                                        <c:when test="${not empty assignedProblems}">
                                            <c:forEach var="problem" items="${assignedProblems}">
                                                <%@ include file="component/ProblemCard.jsp" %>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="empty-state">
                                                <svg fill="none" stroke="currentColor" viewBox="0 0 24 24"
                                                    xmlns="http://www.w3.org/2000/svg">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2"
                                                        d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2">
                                                    </path>
                                                </svg>
                                                <p>You have no pending assigned tasks.</p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <!-- AVAILABLE TAB -->
                            <div id="tab-available" class="tab-content">
                                <div class="cards-grid">
                                    <c:choose>
                                        <c:when test="${not empty availableProblems}">
                                            <c:forEach var="problem" items="${availableProblems}">
                                                <%@ include file="component/ProblemCard.jsp" %>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="empty-state">
                                                <svg fill="none" stroke="currentColor" viewBox="0 0 24 24"
                                                    xmlns="http://www.w3.org/2000/svg">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2" d="M5 13l4 4L19 7"></path>
                                                </svg>
                                                <p>No new problems available right now.</p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <!-- SOLVED TAB -->
                            <div id="tab-solved" class="tab-content">
                                <div class="cards-grid">
                                    <c:choose>
                                        <c:when test="${not empty solvedProblems}">
                                            <c:forEach var="problem" items="${solvedProblems}">
                                                <%@ include file="component/ProblemCard.jsp" %>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="empty-state">
                                                <svg fill="none" stroke="currentColor" viewBox="0 0 24 24"
                                                    xmlns="http://www.w3.org/2000/svg">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2"
                                                        d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                                                </svg>
                                                <p>You haven't resolved any problems yet.</p>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                        </c:otherwise>
                    </c:choose>
                </div>

                <script>
                    function switchProfileTab(tabId, el) {
                        document.querySelectorAll('.tab-content').forEach(function (content) {
                            content.classList.remove('active');
                        });
                        document.querySelectorAll('.tab-btn').forEach(function (btn) {
                            btn.classList.remove('active');
                        });
                        document.getElementById(tabId).classList.add('active');
                        el.classList.add('active');
                    }
                </script>
            </t:layout>