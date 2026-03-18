<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Civic Solve</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/auth.css">
</head>
<body onload="initRegister()">

<div class="auth-container register-form">
    <h2>Join Civic Solve</h2>
    <form id="registerForm" onsubmit="handleRegister(event)">
        
        <div class="form-group">
            <label for="name">Full Name</label>
            <input type="text" id="name" name="name" required placeholder="John Doe">
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <label for="role">Account Type (Role)</label>
            <select id="role" name="role" required onchange="toggleOrganizationField()">
                <option value="citizen" selected>Citizen (Report Problems)</option>
                <option value="noble">Noble Volunteer (Solve Problems)</option>
                <option value="ngo">NGO Member</option>
                <option value="vmc">VMC Official</option>
            </select>
        </div>

        <div id="organization-group" class="form-group">
            <label for="organizationId">Select Organization</label>
            <select id="organizationId" name="organizationId">
                <option value="" selected disabled>Select your organization...</option>
                <!-- Populated dynamically via AJAX -->
            </select>
        </div>

        <button type="submit" class="btn-submit">Create Account</button>
    </form>
    <div class="auth-links">
        Already have an account? <a href="${pageContext.request.contextPath}/login">Log In</a>
    </div>
</div>

<script>
    window.APP_CONTEXT = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/auth.js"></script>

</body>
</html>
