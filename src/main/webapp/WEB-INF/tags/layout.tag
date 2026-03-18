<%@tag description="Main Layout Tag" pageEncoding="UTF-8"%>
<%@attribute name="pageTitle" required="false" type="java.lang.String" %>
<%@attribute name="onload" required="false" type="java.lang.String" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${empty pageTitle ? 'CivicSolve' : pageTitle}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mainLayout.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/card-layout.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <script>
            window.APP_CONTEXT = '${pageContext.request.contextPath}';
            window.USER_ID = '${sessionScope.loggedInUser != null ? sessionScope.loggedInUser.userId : 0}';
            window.USER_ROLE = '${sessionScope.loggedInUser != null ? sessionScope.loggedInUser.role : ""}';
        </script>
    </head>
    <body onload="initLayout(); ${not empty onload ? onload : ''}">
        <div class="overlay" id="overlay" onclick="closeMobileSidebar()"></div>
        <!-- Sidebar only once -->
        <jsp:include page="/jsp/component/sidebar.jsp" />
        <div class="main-content">
            <!-- Dynamic Page Content -->
            <jsp:doBody />
        </div>
        
        <!-- ================= ASSIGN PROBLEM MODAL (SOLVER) ================= -->
        <div class="modal-overlay" id="assign-modal" style="display: none;">
            <div class="assign-modal-container">
                <div class="assign-modal-header">
                    <h3>Assign to Myself</h3>
                    <button class="close-btn" onclick="closeAssignModal()">×</button>
                </div>
                <!-- Action Form -->
                <form id="assign-form" onsubmit="submitAssignForm(event)" class="assign-modal-form">
                    <input type="hidden" id="assign-prob-id" name="probId" value="" />
                    
                    <div class="form-group">
                        <label for="assign-estimate">Estimated Time to Resolve *</label>
                        <select id="assign-estimate" name="estimatedTime" required class="modal-input">
                            <option value="">-- Choose Duration --</option>
                            <option value="2">2 Hours</option>
                            <option value="4">4 Hours</option>
                            <option value="8">8 Hours</option>
                            <option value="24">24 Hours</option>
                            <option value="48">48 Hours (2 Days)</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="assign-notes">Solvers Notes / Plan *</label>
                        <textarea id="assign-notes" name="notes" rows="3" required placeholder="Write a brief plan on how you'll resolve this..." class="modal-input"></textarea>
                    </div>

                    <button type="submit" class="modal-submit-btn">Confirm Assign</button>
                </form>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/mainLayout.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/problem.js"></script>
    </body>
</html>
