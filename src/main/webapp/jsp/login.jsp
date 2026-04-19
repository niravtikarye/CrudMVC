<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Civic Solve</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/auth.css">
</head>
<body>

<div class="auth-container">
    <h2>Welcome Back</h2>
    <form id="loginForm" onsubmit="handleLogin(event)">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required autocomplete="username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required autocomplete="current-password">
        </div>
        <button type="submit" class="btn-submit">Login</button>
    </form>
    <div class="auth-links">
        Don't have an account? <a href="${pageContext.request.contextPath}/register">Sign Up</a>
    </div>
</div>

<script>
    window.APP_CONTEXT = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/auth.js"></script>

</body>
</html>
