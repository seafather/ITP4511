<%--
  Created by IntelliJ IDEA.
  User: 93140
  Date: 3/31/2023
  Time: 6:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:400,600,700,800&display=swap" rel="stylesheet">

</head>
<body>
<div class="cont">
    <form class="form sign-in" action="login" method="post">
        <input type="hidden" name="action" value="authenticate">

        <h2>Sign In</h2>
        <label>
            <span>Email Address</span>
            <input type="email" name="email">
        </label>
        <label>
            <span>Password</span>
            <input type="password" name="password">
        </label>
        <button class="submit" type="submit">Sign In</button>

    </form>

    <div class="sub-cont">
        <div class="img">
            <div class="img-text m-up">
                <h2>New here?</h2>
                <p>Sign up and discover great amount of new opportunities!</p>
            </div>
            <div class="img-text m-in">
                <h2>One of us?</h2>
                <p>If you already has an account, just sign in. We've missed you!</p>
            </div>
            <div class="img-btn">
                <span class="m-up" style="color: black;">Sign Up</span>
                <span class="m-in">Sign In</span>
            </div>
        </div>
        <form class="form sign-up" action="login" method="post">
            <h2>Sign Up</h2>
            <label>
                <span>Name</span>
                <input type="text" name="name">
            </label>
            <label>
                <span>Email</span>
                <input type="email" name="email">
            </label>
            <label>
                <span>Password</span>
                <input type="password" name="password">
            </label>
            <label>
                <span>Confirm Password</span>
                <input type="password" name="passwordAgain">
            </label>

            <input type="hidden" name="action" value="customerRegister">
            <button class="submit" type="submit">Sign Up Now</button>
        </form>
    </div>
</div>
<script type="text/javascript" src="js/login.js"></script>
</body>
</html>
