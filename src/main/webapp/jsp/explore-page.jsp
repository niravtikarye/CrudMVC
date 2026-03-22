<%-- Document : dashboard Created on : 17-Feb-2026, 10:51:03 pm Author : admin --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout pageTitle="Explore" onload="initExplore()">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/explore.css">

    <!-- Filter Bar -->
    <div class="explore-filters" style="margin: 20px; display: flex; justify-content: center;">
        <form action="${pageContext.request.contextPath}/explore" method="GET" style="display: flex; gap: 15px; flex-wrap: wrap;">
            <select name="areaId" style="padding: 10px; border-radius: 8px; border: 1px solid #cbd5e1; outline: none; flex: 1; min-width: 150px;">
                <option value="">All Areas</option>
                <c:forEach var="area" items="${areas}">
                    <option value="${area.areaId}" ${selectedArea == area.areaId ? 'selected' : ''}>${area.areaName}</option>
                </c:forEach>
            </select>
            <select name="categoryId" style="padding: 10px; border-radius: 8px; border: 1px solid #cbd5e1; outline: none; flex: 1; min-width: 150px;">
                <option value="">All Categories</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.categoryId}" ${selectedCategory == cat.categoryId ? 'selected' : ''}>${cat.categoryName}</option>
                </c:forEach>
            </select>
            <select name="status" style="padding: 10px; border-radius: 8px; border: 1px solid #cbd5e1; outline: none; flex: 1; min-width: 150px;">
                <option value="">All Statuses</option>
                <option value="OPEN" ${selectedStatus == 'OPEN' ? 'selected' : ''}>Open</option>
                <option value="IN_PROGRESS" ${selectedStatus == 'IN_PROGRESS' ? 'selected' : ''}>In Progress</option>
                <option value="RESOLVED" ${selectedStatus == 'RESOLVED' ? 'selected' : ''}>Resolved</option>
                <option value="VERIFIED" ${selectedStatus == 'VERIFIED' ? 'selected' : ''}>Verified</option>
            </select>
            <button type="submit" style="padding: 10px 20px; background: #3b82f6; color: white; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; flex: 1; min-width: 100px;">Apply Filters</button>
            <a href="${pageContext.request.contextPath}/explore" style="padding: 10px 20px; background: #e2e8f0; color: #475569; border: none; border-radius: 8px; text-decoration: none; display: flex; align-items: center; justify-content: center; font-weight: bold; flex: 1; min-width: 100px;">Reset</a>
        </form>
    </div>

    <div class="explore-grid">
        <c:forEach var="problem" items="${problemList}">
            <%@ include file="component/ExploreCard.jsp" %>
        </c:forEach>
    </div>

    <!-- Problem Info Modal Container -->
    <div id="problem-info-modal" class="modal-overlay" style="display: none;">
        <jsp:include page="component/ProblemInfo.jsp" />
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/explore.js?v=2"></script>
</t:layout>