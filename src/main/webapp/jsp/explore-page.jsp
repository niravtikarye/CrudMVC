<%-- Document : dashboard Created on : 17-Feb-2026, 10:51:03 pm Author : admin --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout pageTitle="Explore" onload="initExplore()">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/explore.css">

    <div class="explore-grid">
        <c:forEach var="problem" items="${problemList}">
            <div class="explore-card" onclick="openProblemInfo(this)" data-title="${problem.title}"
                 data-desc="${problem.description}" data-hipe="${problem.hypeCount}"
                 data-status="${problem.status}" data-image="${not empty problem.imageUrls ? problem.imageUrls[0] : 'https://i.pinimg.com/736x/00/0d/9c/000d9c727330e506be6d8ee2497cde54.jpg'}">

                <img src="${not empty problem.imageUrls ? problem.imageUrls[0] : 'https://i.pinimg.com/736x/00/0d/9c/000d9c727330e506be6d8ee2497cde54.jpg'}" alt="explore-img">
                <div class="explore-overlay">
                    <div class="overlay-content">
                        <span>❤ ${problem.hypeCount}️ </span>
                        <span>💬 ${problem.status}</span>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Problem Info Modal Container -->
    <div id="problem-info-modal" class="modal-overlay" style="display: none;">
        <jsp:include page="component/ProblemInfo.jsp" />
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/explore.js"></script>
</t:layout>