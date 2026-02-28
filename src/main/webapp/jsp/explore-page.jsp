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

                    <!-- <c:forEach var="problem" items="${problemList}"> -->
                    <div class="explore-card">

                        <img src="https://i.pinimg.com/736x/26/be/da/26beda6a53b20adf4d4c2ab32274730d.jpg"
                            alt="explore-img">

                        <div class="explore-overlay">
                            <div class="overlay-content">
                                <span>❤️ ${problem.hype}</span>
                                <span>💬 ${problem.status}</span>
                            </div>
                        </div>

                    </div>
                    <div class="explore-card">

                        <img src="https://i.pinimg.com/736x/26/be/da/26beda6a53b20adf4d4c2ab32274730d.jpg"
                            alt="explore-img">

                        <div class="explore-overlay">
                            <div class="overlay-content">
                                <span>❤️ ${problem.hype}</span>
                                <span>💬 ${problem.status}</span>
                            </div>
                        </div>

                    </div>
                    <div class="explore-card">

                        <img src="https://i.pinimg.com/736x/26/be/da/26beda6a53b20adf4d4c2ab32274730d.jpg"
                            alt="explore-img">

                        <div class="explore-overlay">
                            <div class="overlay-content">
                                <span>❤️ ${problem.hype}</span>
                                <span>💬 ${problem.status}</span>
                            </div>
                        </div>

                    </div>
                    <div class="explore-card">

                        <img src="https://i.pinimg.com/736x/26/be/da/26beda6a53b20adf4d4c2ab32274730d.jpg"
                            alt="explore-img">

                        <div class="explore-overlay">
                            <div class="overlay-content">
                                <span>❤️ ${problem.hype}</span>
                                <span>💬 ${problem.status}</span>
                            </div>
                        </div>

                    </div>
                    <div class="explore-card">

                        <img src="https://i.pinimg.com/1200x/0a/6c/0a/0a6c0a3a85ab2eca1180e0c27b732721.jpg"
                            alt="explore-img">

                        <div class="explore-overlay">
                            <div class="overlay-content">
                                <span>❤️ ${problem.hype}</span>
                                <span>💬 ${problem.status}</span>
                            </div>
                        </div>

                    </div>
                    <div class="explore-card">

                        <img src="https://i.pinimg.com/736x/05/cd/18/05cd18eebb9f68c2291cf055f7bd05c6.jpg"
                            alt="explore-img">

                        <div class="explore-overlay">
                            <div class="overlay-content">
                                <span>❤️ ${problem.hype}</span>
                                <span>💬 ${problem.status}</span>
                            </div>
                        </div>

                    </div>
                    <!-- </c:forEach> -->
                </div>
            </body>

            </html>