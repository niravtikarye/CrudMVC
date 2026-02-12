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

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        form {
            width: 340px;
        }

        h2 {
            text-align: center;
            margin-bottom: 15px;
        }

        .field {
            margin-bottom: 10px;
        }

        input, textarea {
            width: 100%;
            padding: 7px;
            box-sizing: border-box;
        }

        .radio-row {
            display: flex;
            gap: 15px;
            align-items: center;
            flex-wrap: wrap;
        }

        button {
            width: 100%;
            padding: 8px;
            margin-top: 8px;
        }

        #solverType {
            display: none;
        }

        @media (max-width: 400px) {
            form {
                width: 90%;
            }
        }
    </style>
    
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