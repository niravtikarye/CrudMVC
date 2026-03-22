<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="userImgsStr" value="" />
<c:forEach var="url" items="${problem.citizenImageUrls}" varStatus="status">
    <c:set var="userImgsStr" value="${userImgsStr}${url}${!status.last ? '|||' : ''}" />
</c:forEach>

<c:set var="solverImgsStr" value="" />
<c:forEach var="url" items="${problem.solverImageUrls}" varStatus="status">
    <c:set var="solverImgsStr" value="${solverImgsStr}${url}${!status.last ? '|||' : ''}" />
</c:forEach>

<div class="explore-card" onclick="openProblemInfo(this)" data-title="${problem.title}"
     data-desc="${problem.userDesc}" data-hipe="${problem.hypeCount}"
     data-status="${problem.status}" 
     data-image="${not empty problem.citizenImageUrls ? problem.citizenImageUrls[0] : 'https://i.pinimg.com/736x/00/0d/9c/000d9c727330e506be6d8ee2497cde54.jpg'}"
     data-citizen-images="${userImgsStr}"
     data-solver-images="${solverImgsStr}"
     data-prob-id="${problem.probId}"
     data-user-id="${problem.userId}"
     data-solver-id="${problem.solverId}">

    <img src="${not empty problem.citizenImageUrls ? problem.citizenImageUrls[0] : 'https://i.pinimg.com/736x/00/0d/9c/000d9c727330e506be6d8ee2497cde54.jpg'}" alt="explore-img">
    <div class="explore-overlay">
        <div class="overlay-content">
            <span>❤ ${problem.hypeCount}️ </span>
            <span>💬 ${problem.status}</span>
        </div>
    </div>
</div>
