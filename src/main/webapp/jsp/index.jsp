<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
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
        <h2>This is Hello page</h2>
        <p>hello 123<p>
        <button onclick="processOperation('add')">Add</button>
        <button onclick="processOperation('view')">View</button>
        <div id="ajax"></div>
    </body>
</html>
