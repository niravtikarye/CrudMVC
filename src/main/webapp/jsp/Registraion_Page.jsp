<%-- 
    Document   : Registraion_Page
    Created on : Feb 11, 2026, 8:04:20 PM
    Author     : nirav
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- <!DOCTYPE html>
<html>
<head>
    <title>Registration</title>

    
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
        <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/style.css">

    <!-- JS -->
    <script
        src="${pageContext.request.contextPath}/resources/js/app.js">
    </script>
    
</head>
--%>
<!--<body>-->

<form id="regForm">

    <h2>Registration</h2>

    <div class="field">
        <input type="text" placeholder="Full Name" required minlength="3">
    </div>

    <div class="field radio-row">
        Gender:
        <label><input type="radio" name="gender" required> Male</label>
        <label><input type="radio" name="gender"> Female</label>
        <label><input type="radio" name="gender"> Other</label>
    </div>

    <div class="field">
        <input type="number" placeholder="Age ( > 12 )" id="age" required min="13">
    </div>

    <div class="field">
        <input type="text" placeholder="Username" required minlength="4">
    </div>

    <div class="field">
        <input type="email" placeholder="Email" required>
    </div>

    <div class="field">
        <input type="tel" placeholder="Phone Number" pattern="[0-9]{10}" required>
    </div>

    <div class="field radio-row">
        User Type:
        <label><input type="radio" name="userType" value="user" required> User</label>
        <label><input type="radio" name="userType" value="solver"> Solver</label>
    </div>

    <div class="field radio-row" id="solverType">
        Solver Type:
        <label><input type="radio" name="solver"> VMC</label>
        <label><input type="radio" name="solver"> NGO</label>
        <label><input type="radio" name="solver"> Noble Person</label>
    </div>

    <div class="field">
        <textarea rows="2" placeholder="Address" required></textarea>
    </div>

    <div class="field">
        <input type="password" id="pass" placeholder="Password" required minlength="6">
    </div>

    <div class="field">
        <input type="password" id="confirmPass" placeholder="Confirm Password" required>
    </div>

    <button type="submit">Register</button>
    <button type="button" onclick="goToLoginPage()">Login Page</button>

</form>
<!--


</body>
</html>-->
