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
        <div class="more" style="position:relative;">
            <span onclick="toggleDropdown('dropdown-${problem.probId}', event)" style="cursor:pointer; font-size: 18px; user-select:none;">•••</span>
            <div id="dropdown-${problem.probId}" class="dropdown-content" style="display:none; position:absolute; right:0; top: 25px; background:white; border:1px solid #e2e8f0; padding:10px; z-index:100; border-radius:8px; white-space:nowrap; box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1); min-width: 150px;">
                <c:set var="userRole" value="${requestScope.loggedInUser != null ? requestScope.loggedInUser.role : ''}" />
                
                <%-- SOLVER ACTIONS --%>
                <c:if test="${problem.status == 'IN_PROGRESS' && problem.solverId == requestScope.loggedInUser.userId && userRole != 'citizen'}">
                    <div style="cursor:pointer; font-size:14px; font-weight:600; color:#3b82f6; display:flex; align-items:center; gap:8px; padding: 6px 0; border-bottom: 1px solid #f1f5f9;" onclick="openSolveModal('${problem.probId}')">
                        <svg rpl="" fill="currentColor" height="16" icon-name="check" viewBox="0 0 20 20" width="16" xmlns="http://www.w3.org/2000/svg"><path d="M17.707 5.293a1 1 0 010 1.414l-9 9a1 1 0 01-1.414 0l-5-5a1 1 0 111.414-1.414L8 13.586l8.293-8.293a1 1 0 011.414 0z"></path></svg>
                        Problem Solved
                    </div>
                    <div style="cursor:pointer; font-size:14px; font-weight:600; color:#dc3545; display:flex; align-items:center; gap:8px; padding: 6px 0;" onclick="rejectProblem('${problem.probId}')">
                        <svg rpl="" fill="currentColor" height="16" icon-name="close" viewBox="0 0 20 20" width="16" xmlns="http://www.w3.org/2000/svg"><path d="M10 8.586l3.535-3.535 1.414 1.414L11.414 10l3.535 3.535-1.414 1.414L10 11.414l-3.535 3.535-1.414-1.414L8.586 10 5.051 6.465l1.414-1.414L10 8.586z"></path></svg>
                        Reject problem
                    </div>
                </c:if>

                <%-- CREATOR ACTIONS --%>
                <c:if test="${problem.userId == requestScope.loggedInUser.userId}">
                    <div style="cursor:pointer; font-size:14px; font-weight:600; color:#475569; display:flex; align-items:center; gap:8px; padding: 6px 0; border-bottom: 1px solid #f1f5f9;" onclick="editProblem('${problem.probId}')">
                        <svg rpl="" fill="currentColor" height="16" viewBox="0 0 20 20" width="16" xmlns="http://www.w3.org/2000/svg"><path d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.829-2.828z"></path></svg>
                        Edit problem
                    </div>
                    <div style="cursor:pointer; font-size:14px; font-weight:600; color:#dc3545; display:flex; align-items:center; gap:8px; padding: 6px 0;" onclick="deleteProblem('${problem.probId}')">
                        <svg rpl="" fill="currentColor" height="16" viewBox="0 0 20 20" width="16" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd"></path></svg>
                        Delete problem
                    </div>
                </c:if>

                <%-- If nothing to show, show a placeholder --%>
                <c:if test="${(problem.solverId != requestScope.loggedInUser.userId || problem.status != 'IN_PROGRESS' || userRole == 'citizen') && problem.userId != requestScope.loggedInUser.userId}">
                    <div style="font-size:13px; color:#94a3b8; padding: 4px;">No actions available</div>
                </c:if>
            </div>
        </div>
    </div>

    <!-- Problem Image -->
    <div class="slider-container">
        <c:if test="${fn:length(problem.citizenImageUrls) > 1}">          
            <button class="slider-btn prev-btn" onclick="prevImage(event, this)">❮</button>
        </c:if>

        <div class="slider">
            <c:choose>
                <c:when test="${not empty problem.citizenImageUrls}">
                    <c:forEach var="imgUrl" items="${problem.citizenImageUrls}">
                        <div class="slider-item">
                            <img src="${imgUrl}" alt="Problem Image">
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="slider-item" style="display: flex; align-items: center; justify-content: center; background: #f8fafc; color: #94a3b8;">
                        <span>No Image Provided</span>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <!-- Prev/Next buttons only if there are multiple images -->
        <c:if test="${fn:length(problem.citizenImageUrls) > 1}">
            <button class="slider-btn next-btn" onclick="nextImage(event, this)">❯</button>
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
        <span class="title-highlight">${problem.title}</span> - ${problem.userDesc}
        <br/><small style="color:var(--text-secondary);">Status: ${problem.status}</small>
        
        <c:if test="${not empty problem.solverDesc}">
            <div style="margin-top:10px; padding-top:10px; border-top:1px solid var(--border-color);">
                <strong>Solver Notes:</strong> 
                <span style="color:var(--text-secondary);">${problem.solverDesc}</span>
            </div>
        </c:if>
    </div>

    <!-- Resolution & Verification Actions -->
    <div class="resolution-actions" style="margin: 10px 15px; padding-bottom: 10px;">
        <c:set var="userRole" value="${requestScope.loggedInUser != null ? requestScope.loggedInUser.role : ''}" />
        
        <!-- Logic for Solvers moved to dropdown actions -->

        <!-- Logic for Citizens to Verify -->
        <c:if test="${(problem.status == 'RESOLVED' || problem.status == 'SOLVED') && userRole == 'citizen'}">
            <div style="display:flex; gap:10px; background:var(--bg-color); padding:10px; border-radius:6px;">
                <span style="font-size:0.9rem; margin-top:4px;">Is this issue fixed?</span>
                <button onclick="verifyProblem('${problem.probId}', 'VERIFIED')" style="background:#28a745; color:white; border:none; padding:6px 12px; border-radius:4px; cursor:pointer;">Yes, Accept</button>
                <button onclick="verifyProblem('${problem.probId}', 'REOPENED')" style="background:#dc3545; color:white; border:none; padding:6px 12px; border-radius:4px; cursor:pointer;">No, Reject</button>
            </div>
        </c:if>
    </div>

</div>

<!-- Solve Modal for this specific problem -->
<div id="solve-modal-${problem.probId}" class="modal-overlay" style="display:none; align-items:center; justify-content:center; z-index: 10000; position: fixed; top: 0; left: 0; width: 100vw; height: 100vh; background: rgba(0, 0, 0, 0.75);">
    <div class="problem-info-container" style="max-width:500px; height:auto; padding:24px; text-align:left; background: #fff; border-radius: 16px; box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.3); position: relative; width: 90%;">
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:20px; border-bottom: 1px solid #e2e8f0; padding-bottom: 10px;">
            <h3 style="font-size: 20px; font-weight: 700; color: #0f172a; margin: 0;">Submit Resolution</h3>
            <button type="button" class="close-btn" onclick="closeSolveModal('${problem.probId}')" style="background:transparent; border:none; font-size:20px; cursor:pointer; color:#64748b;">✖</button>
        </div>
        <form id="solve-form-${problem.probId}" onsubmit="solveProblem(event, '${problem.probId}')" enctype="multipart/form-data" style="display:flex; flex-direction:column; gap:20px;">
            <div>
                <label style="display:block; margin-bottom:8px; font-weight:600; font-size: 14px; color: #475569;">Proof Image (Required)</label>
                <input type="file" name="proofImage" accept="image/*" required style="width:100%; border:1px solid #cbd5e1; border-radius:8px; padding:10px; font-size: 14px; background: #f8fafc;"/>
            </div>
            <div>
                <label style="display:block; margin-bottom:8px; font-weight:600; font-size: 14px; color: #475569;">Description / Details</label>
                <textarea name="description" rows="4" placeholder="Explain what was done to resolve this problem..." style="width:100%; border:1px solid #cbd5e1; border-radius:8px; padding:10px; font-size: 14px; background: #f8fafc; resize: vertical;"></textarea>
            </div>
            <div style="display:flex; justify-content:flex-end; gap: 10px; margin-top: 10px;">
                <button type="button" onclick="closeSolveModal('${problem.probId}')" style="background:#e2e8f0; color:#475569; border:none; padding:10px 20px; border-radius:8px; font-weight:600; cursor:pointer;">Cancel</button>
                <button type="submit" style="background:linear-gradient(135deg, #10b981 0%, #059669 100%); color:white; border:none; padding:10px 24px; border-radius:8px; font-weight:600; cursor:pointer; box-shadow: 0 4px 6px -1px rgba(16, 185, 129, 0.3);">Problem Solved</button>
            </div>
        </form>
    </div>
</div>