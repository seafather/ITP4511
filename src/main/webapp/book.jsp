<%@ page import="bean.VenueBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.VenueBean" %>
<%--
  Created by IntelliJ IDEA.
  User: 93140
  Date: 4/7/2023
  Time: 5:40 PM
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
    ArrayList<VenueBean> obj = (ArrayList) request.getAttribute("venues");

%>


<div class="row">
    <div class="col s1 m1 l1">
        <div class="l-navbar " id="navbar">
            <nav1 class="nav">
                <div>
                    <div class="nav_brand">
                        <!-- <ion-icon name="menu-outline" class="nav_toggle" id="nav_toggle"></ion-icon> -->
                        <ion-icon name="menu-outline" class="nav_toggle" id="nav-toggle"></ion-icon>

                        <a href="#" class="nav_logo">Welcome <%= userInfo.getName() %>
                        </a>
                    </div>


                    <div class="nav_list">
                        <a href="jumpToBook.jsp" class="nav_link active1">
                            <ion-icon name="home-outline" class="nav_icon"></ion-icon>
                            <span class="nav_name">Reserve</span>
                        </a>
                        <a href="OrderController?action=list" class="nav_link">
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

    <div class="col l10 ">

        <%
            String str = "";
            int j = 0;

            for (VenueBean i : obj) {
                str += "            <div class=\"card\" style=\"margin-bottom: 40px;\">\n" +
                        "            <div class=\"card-content\">\n" +
                        "            <span class=\"card-title grey-text text-darken-4\">" + i.getName() + "</span>\n" +
                        "            </div>";

                str += "    <div class=\"card-tabs\">\n" +
                        "            <ul class=\"tabs tabs-fixed-width\">\n" +
                        "            <a class=\"active\"></a>\n" +
                        "            <li class=\"tab\"><a href=\"#descptions" + j + "\">Descptions</a></li>\n" +
                        "            <li class=\"tab\"><a href=\"#details" + j + "\">Details</a></li>\n" +
                        "            <li class=\"tab\"><a href=\"#picture" + j + "\">Picture</a></li>\n" +
                        "            <li class=\"tab\"><a href=\"#price" + j + "\">price</a></li>\n" +
                        "            </ul>      </div>";

                str += "     <div class=\"card-content grey lighten-5\">\n" +
                        "            <div id=\"descptions" + j + "\" style=\"background-color: rgb(250, 250, 250);\">\n" +
                        "            <p style=\"font-size:18px;margin-bottom: 20px;\">Address:" + i.getLocation() + "</p>";

                str += "<p>" + i.getDescription() + "</p> </div>";

                str += "    <div id=\"details" + j + "\" style=\"background-color: rgb(250, 250, 250);\">\n" +
                        "            <p style=\"margin-bottom: 10px;  font-size: 20px;\">Maximum occupancy: " + i.getMax_people() + " peopls</p>\n" +
                        "            <p style=\"margin-bottom: 10px; font-size: 20px;\">facility:</p>\n" +
                        "            <p style=\"font-size: 16px;color: black;\">additional services</p>\n";

                str += "           <p style=\"color: black;\">Tables and chairs</p>\n" +
                        "            <p style=\"color: black;\">Sound system</p>\n" +
                        "            <p style=\"color: black;\">Projector</p>\n" +
                        "            <p style=\"color: black;\">Lighting equipment</p>\n" +
                        "            <p style=\"color: black;\">Network connection</p>\n" +
                        "            <p style=\"color: black;\">Air-conditioning system</p>\n" +
                        "            <p style=\"color: black;\">Kitchen</p>\n" +
                        "            <p style=\"color: black;\">Restrooms</p> </div>";

                str += "  <div id=\"picture" + j + "\" style=\"background-color: rgb(250, 250, 250);\"></div>";

                str += "    <div id=\"price" + j + "\" style=\"background-color: rgb(250, 250, 250);\"> <p>Hours Fee:" + i.getHoursFee() + "</p>" +
                        "     <div style=\"float:right; clear:both;\">\n" +
                        "                            <a class=\"waves-effect waves-light btn\" style=\"position: absolute;\n" +
                        "                            right: 0;\n" +
                        "                            bottom: 0;margin-top:20px; margin-bottom: 20px; margin-right: 20px;\" onclick=\"handleClick('"+i.getName()+ "')\">Add to cart</a>\n" +
                        "                        </div>"
                        + "</div></div></div>";

                j++;
            }

            out.print(str);


        %>

        <div>
            <a class="btn-floating btn-large waves-effect waves-light red" style="  position: fixed;
                bottom: 40px;
                right: 40px;" onclick="sendArrayToServlet()"><i class="material-icons">add</i></a>
        </div>


    </div>

</div>


<script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
<script src="js/index.js"></script>


<script src="https://code.jquery.com/jquery-3.6.4.js"
        integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

<script>
    var arrayList = [];

    function handleClick(str) {
        arrayList.push(str);
    }

    function sendArrayToServlet() {
        var arrStr = arrayList.join(",");

        var queryParams = "query=" + arrayList.join(",");
        window.location.href = "booking.jsp?" + queryParams;
    }

</script>
</body>
</html>
