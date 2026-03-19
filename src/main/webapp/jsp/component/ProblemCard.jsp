<%-- 
    Document   : ProblemCard
    Created on : 18-Feb-2026, 11:30:27 pm
    Author     : admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="insta-card">
    <!-- Header -->
    <div class="insta-header">
        <div class="user-info">
            <img src="https://i.pinimg.com/736x/ca/c6/d8/cac6d852725aa673ffe24f9f955e6ba1.jpg" />
            <div>
                <span class="username">${problem.authorName}</span>
                <span class="time">${problem.areaName} • ${problem.categoryName}</span>
            </div>
        </div>
        <div class="more">•••</div>
    </div>

    <!-- Problem Image -->
    <div class="slider-container">
        <c:if test="${fn:length(problem.imageUrls) > 1}">          
            <button class="prev-btn" onclick="prevSlide(this)">❮</button>
        </c:if>

        <div class="slider">
            <c:choose>
                <c:when test="${not empty problem.imageUrls}">
                    <c:forEach var="imgUrl" items="${problem.imageUrls}">
                        <img src="${imgUrl}" alt="${problem.title}" class="post-img">
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="no-image-placeholder">No Image Provided</div>
                </c:otherwise>
            </c:choose>
        </div>
        <c:if test="${fn:length(problem.imageUrls) > 1}">
            <button class="next-btn" onclick="nextSlide(this)">❯</button>
        </c:if>

    </div>

    <!-- Actions -->
    <div class="insta-actions">
        <div class="left-actions">
            <!-- Clicking the SVG triggers the hype logic. Using 'data-probid' to pass the ID -->
            <span onclick="hypeProblem(this, '${problem.probId}')" style="cursor:pointer;" class="hype-btn">
                <svg rpl="" fill="currentColor" height="16" icon-name="upvote" viewBox="0 0 20 20" width="16" xmlns="http://www.w3.org/2000/svg"> <path d="M10 19a3.966 3.966 0 01-3.96-3.962V10.98H2.838a1.731 1.731 0 01-1.605-1.073 1.734 1.734 0 01.377-1.895L9.364.254a.925.925 0 011.272 0l7.754 7.759c.498.499.646 1.242.376 1.894-.27.652-.9 1.073-1.605 1.073h-3.202v4.058A3.965 3.965 0 019.999 19H10zM2.989 9.179H7.84v5.731c0 1.13.81 2.163 1.934 2.278a2.163 2.163 0 002.386-2.15V9.179h4.851L10 2.163 2.989 9.179z"></path> </svg>
            </span>
            <span class="insta-likes" id="hype-count-${problem.probId}">${problem.hypeCount}</span>
        </div>
        <%-- Show assign button only when problem has no solver yet (status = ACTIVE) --%>
        <c:choose>
            <c:when test="${problem.solverId == null}">
                <div title="Assign to me" class="assign-icon" onclick="assignProblem(this, '${problem.probId}')" style="cursor:pointer;">
                    <svg rpl="" aria-hidden="true" fill="currentColor" height="16" icon-name="award" viewBox="0 0 20 20" width="16" xmlns="http://www.w3.org/2000/svg"><path d="M18.75 14.536l-2.414-3.581A6.947 6.947 0 0017 8c0-3.86-3.14-7-6.999-7-3.859 0-6.999 3.14-6.999 7 0 1.057.242 2.056.664 2.955l-2.414 3.581c-.289.428-.33.962-.109 1.429.22.467.658.776 1.173.826l1.575.151.758 1.494a1.435 1.435 0 001.297.795c.482 0 .926-.234 1.198-.639l2.437-3.612c.14.008.28.021.423.021.143 0 .282-.013.423-.021l2.437 3.612c.272.405.716.639 1.198.639.031 0 .062 0 .094-.003a1.435 1.435 0 001.203-.791l.758-1.495 1.576-.151c.514-.05.952-.358 1.172-.826a1.434 1.434 0 00-.109-1.429h-.006zM10 2.8A5.205 5.205 0 0115.2 8c0 2.867-2.333 5.2-5.2 5.2A5.205 5.205 0 014.801 8c0-2.867 2.332-5.2 5.2-5.2zM5.982 17.09l-.937-1.846-1.974-.189 1.66-2.462a7.02 7.02 0 002.936 1.999L5.982 17.09zm10.947-2.035l-1.974.189-.937 1.846-1.685-2.499a7.013 7.013 0 002.936-1.999l1.66 2.462v.001z"></path></svg>
                </div>
            </c:when>
            <c:otherwise>
                <%-- Already assigned — show a locked gold badge, not clickable --%>
                <div title="Already assigned" class="assign-icon assigned" style="cursor:default; color:gold; opacity:0.75;">
                    🏆
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Caption -->
    <div class="insta-caption">
        <strong>${problem.authorName}</strong> 
        <span class="title-highlight">${problem.title}</span> - ${problem.description}
        <br/><small style="color:var(--text-secondary);">Status: ${problem.status}</small>
    </div>

    <!-- Resolution & Verification Actions -->
    <div class="resolution-actions" style="margin: 10px 15px; padding-bottom: 10px;">
        <c:set var="userRole" value="${requestScope.loggedInUser != null ? requestScope.loggedInUser.role : ''}" />
        
        <!-- Logic for Solvers to Mark as Solved -->
        <c:if test="${problem.status == 'IN_PROGRESS' && userRole != 'citizen' && userRole != ''}">
            <form id="solve-form-${problem.probId}" onsubmit="solveProblem(event, '${problem.probId}')" enctype="multipart/form-data" style="display:flex; gap:10px; align-items:center; background:var(--bg-color); padding:10px; border-radius:6px;">
                <input type="file" name="proofImage" accept="image/*" required style="font-size: 0.8rem; flex:1;"/>
                <button type="submit" style="background:var(--primary-color); color:white; border:none; padding:6px 12px; border-radius:4px; cursor:pointer;">Finish Job</button>
            </form>
        </c:if>

        <!-- Logic for Citizens to Verify -->
        <c:if test="${problem.status == 'RESOLVED' && userRole == 'citizen'}">
            <div style="display:flex; gap:10px; background:var(--bg-color); padding:10px; border-radius:6px;">
                <span style="font-size:0.9rem; margin-top:4px;">Is this issue fixed?</span>
                <button onclick="verifyProblem('${problem.probId}', 'VERIFIED')" style="background:#28a745; color:white; border:none; padding:6px 12px; border-radius:4px; cursor:pointer;">Yes, Accept</button>
                <button onclick="verifyProblem('${problem.probId}', 'RE_OPENED')" style="background:#dc3545; color:white; border:none; padding:6px 12px; border-radius:4px; cursor:pointer;">No, Reject</button>
            </div>
        </c:if>
    </div>

</div>