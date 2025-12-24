<%-- 
    Document   : userform
    Created on : 17 Dec 2025, 11:58:59 pm
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add User</title>

    <!-- CSS -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/style.css">

    <!-- JS -->
    <script
        src="${pageContext.request.contextPath}/resources/js/app.js">
    </script>
</head>

<body>

<h2>Add User</h2>

<form action="saveUser" method="post" onsubmit="return validateForm()">
    Name: <input type="text" id="name" name="name"><br><br>
    Email: <input type="text" id="email" name="email"><br><br>
    <input type="submit" value="Save">
</form>

</body>
</html>

