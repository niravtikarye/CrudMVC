<%@tag description="Main Layout Tag" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle" required="false" type="java.lang.String" %>
<%@attribute name="onload" required="false" type="java.lang.String" %>

<c:if test="${empty requestScope.loggedInUser}">
    <c:redirect url="/login" />
</c:if>

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
            window.USER_ID = '${requestScope.loggedInUser != null ? requestScope.loggedInUser.userId : 0}';
            window.USER_ROLE = '${requestScope.loggedInUser != null ? requestScope.loggedInUser.role : ""}';
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
        
        <script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/mainLayout.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/problem.js"></script>
    </body>
</html>
