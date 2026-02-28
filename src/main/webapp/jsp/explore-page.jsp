<%-- Document : dashboard Created on : 17-Feb-2026, 10:51:03 pm Author : admin --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/explore.css">
    </head>

    <body>
        <div class="explore-grid">
            <c:forEach var="problem" items="${problemList}">
                <div class="explore-card">
                    <img src="${problem.image[0]}" alt="explore-img">
                    <div class="explore-overlay">
                        <div class="overlay-content">
                            <span>❤ ${problem.hipe}️ </span>
                            <span>💬 ${problem.status}</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </body>

</html>