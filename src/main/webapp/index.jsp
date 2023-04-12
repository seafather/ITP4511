<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Assignment</title>

    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
</head>

<body>

<%
    if (request.getSession(false) == null) {
        response.sendRedirect("book.jsp");

    } else {
        response.sendRedirect("loginAndRegister.jsp");

    }


%>

<%--<jsp:forward page="loginAndRegister.jsp"/>--%>


<script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
<script src="js/index.js"></script>


<script src="https://code.jquery.com/jquery-3.6.4.js"
        integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>


<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>


</body>
</html>