<%@ page import="bean.OrderInfo" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 93140
  Date: 4/9/2023
  Time: 2:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="./css/index.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
</head>

<body>

<jsp:useBean id="userInfo" class="bean.UserInfo" scope="session"/>

<%
    ArrayList<OrderInfo> orders = (ArrayList<OrderInfo>) request.getAttribute("orders");


%>


<div class="row">
    <div class="col s1 m1 l1">
        <div class="l-navbar " id="navbar">
            <nav1 class="nav">
                <div>
                    <div class="nav_brand">
                        <!-- <ion-icon name="menu-outline" class="nav_toggle" id="nav_toggle"></ion-icon> -->
                        <ion-icon name="menu-outline" class="nav_toggle" id="nav-toggle"></ion-icon>

                        <a href="#" class="nav_logo">Welcome <%=userInfo.getName() %>
                        </a>
                    </div>


                    <div class="nav_list">
                        <a href="jumpToBook.jsp" class="nav_link ">
                            <ion-icon name="home-outline" class="nav_icon"></ion-icon>
                            <span class="nav_name">Reserve</span>
                        </a>
                        <a href="OrderController?action=list" class="nav_link active1">
                            <ion-icon name="chatbubbles-outline" class="nav_icon"></ion-icon>
                            <span class="nav_name">Order</span>
                        </a>
                    </div>
                </div>
                <a href="clearSessionServlet" class="nav_link">
                    <ion-icon name="log-out-outline" class="nav_icon"></ion-icon>

                    <span class="nav_name">Log out</span>
                </a>
            </nav1>
        </div>
    </div>

    <div class="col l10 z-depth-3">


        <%
            String str = "";
            str += "        <table class=\"highlight centered\">" +
                    "            <thead>" +
                    "            <tr>" +
                    " <th>Order number</th>\n" +
                    "                        <th>Customer name</th>\n" +
                    "                        <th>Order Date</th>\n" +
                    "                        <th>Book Date</th>\n" +
                    "                        <th>period of time</th>\n" +
                    "                        <th>State</th>" +
                    "            </tr>" +
                    "            </thead>" +
                    "   <tbody>";


            for (OrderInfo i : orders) {
                String display = "";

                if (i.getState() != "finish") {
                    display = "display:none;";
                }

                str += String.format("<tr>" +
                        "            <td>%s</td>" +
                        "            <td>%s</td>" +
                        "            <td>%s</td>" +
                        "            <td>%s</td>" +
                        "            <td>%s</td>" +
                        "            <td>%s</td>" +
                        "            <td>" +
                        "                <a class=\"waves-effect waves-light btn  blue lighten-1\" style=\"margin-right:15px;\" href= 'OrderController?action=preview&booking_id=" + i.getOrder_ID() +"&venue_id="+i.getVenue_id()+ "'>Review</a>" +
                        "                <a class=\"waves-effect waves-light btn queen\" style=\"margin-right:15px;\" href='OrderController?action=update&booking_id=" + i.getOrder_ID() + "'>Update</a>" +
                        "                <a class=\"waves-effect waves-light btn  orange lighten-3\" style='margin-right:15px;"+ display +"'>Comment</a>" +
        "            </td>" +
        "        </tr>",
        String.format("%07d", i.getOrder_ID()), i.getCustomerName(), i.getCreateBytDatetime(), i.getDate(),
        i.getStartTime()+" - "+i.getEndTime(), i.getState());

        }

        str += "     </tbody>\n" +
        "        </table>";

        out.print(str);
        %>


    </div>
</div>
<script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
<script src="./js/index.js"></script>


<script src="https://code.jquery.com/jquery-3.6.4.js"
        integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
</body>

</html>
