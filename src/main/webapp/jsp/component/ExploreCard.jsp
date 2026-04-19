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
     data-solver-id="${problem.solverId}"
     data-is-hyped="${problem.hypedByCurrentUser}"
     data-area-name="${problem.areaName}"
     data-address="${fn:escapeXml(problem.addressDescription)}"
     data-solver-desc="${fn:escapeXml(problem.solverDesc)}">

    <img src="${not empty problem.citizenImageUrls ? problem.citizenImageUrls[0] : 'https://i.pinimg.com/736x/00/0d/9c/000d9c727330e506be6d8ee2497cde54.jpg'}" alt="explore-img">
    <div class="explore-overlay">
        <div class="overlay-content" style="flex-direction: column; text-align: center; gap: 10px;">
            <div style="display: flex; gap: 20px; justify-content: center; color: #ffffff; text-shadow: 0 1px 3px rgba(0,0,0,0.8);">
                <span style="color: #ffffff !important; font-weight: 700;">❤ ${problem.hypeCount}</span>
                <span style="color: #ffffff !important; font-weight: 700;">💬 ${problem.status}</span>
            </div>
            <div style="font-size: 13px; font-weight: 500; display: flex; flex-direction: column;">
                <span>📍 ${problem.areaName}</span>
            </div>
            <c:if test="${not empty problem.solverDesc && (problem.status == 'RESOLVED' || problem.status == 'VERIFIED')}">
                <div style="font-size: 11px; margin-top: 5px; color: #a7f3d0; padding: 0 10px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; max-width: 100%;">
                    ✔️ ${problem.solverDesc}
                </div>
            </c:if>
        </div>
    </div>
</div>
