<%@tag description="Main Layout Tag" pageEncoding="UTF-8"%>
<%@attribute name="pageTitle" required="false" type="java.lang.String" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${empty pageTitle ? 'CivicSolve' : pageTitle}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mainLayout.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/card-layout.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css">
    </head>
    <body>
        <div class="overlay" id="overlay" onclick="closeMobileSidebar()"></div>
        <!-- Sidebar only once -->
        <jsp:include page="/jsp/component/sidebar.jsp" />
        <div class="main-content">
            <!-- Dynamic Page Content -->
            <jsp:doBody />
        </div>

        <script src="${pageContext.request.contextPath}/resources/js/mainLayout.js"></script>
    </body>
</html>
