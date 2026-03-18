<%-- 
    Document   : Login_Page
    Created on : Feb 11, 2026, 6:58:44 PM
    Author     : nirav
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/resources/css/style.css">

        <!-- JS -->
        <script
            src="${pageContext.request.contextPath}/resources/js/app.js">
        </script>
    </head>

    <body>
        <div id="mainPage">
            <form class="login-box">
                <h2>Login</h2>

                <input type="text" placeholder="Username" required>

                <input type="password" placeholder="Password" required>

                <div class="radio-group">
                    <label><input type="radio" name="role"> User</label>
                    <label><input type="radio" name="role"> Solver</label>
                </div>

                <div class="actions">
                    <button type="button">Forgot Password</button>
                    <button type="submit">Login</button>
                    <button type="button" onclick="goToRegeistration()">Registration</button>

                </div>
            </form>
        </div>
    </body>
</html>