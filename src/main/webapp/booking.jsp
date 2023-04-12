<%--
  Created by IntelliJ IDEA.
  User: 93140
  Date: 4/8/2023
  Time: 4:58 PM
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

<%
    String arrayString = request.getParameter("query");
    String[] words = arrayString.split(",");
%>

<jsp:useBean id="userInfo" class="bean.UserInfo" scope="session"/>

<div class="row">
    <div class="col s1 m1 l1">
        <div class="l-navbar " id="navbar">
            <nav1 class="nav">
                <div>
                    <div class="nav_brand">
                        <!-- <ion-icon name="menu-outline" class="nav_toggle" id="nav_toggle"></ion-icon> -->
                        <ion-icon name="menu-outline" class="nav_toggle" id="nav-toggle"></ion-icon>

                        <a href="#" class="nav_logo">Welcome <%=userInfo.getName() %>
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
        <form id="form" action="booking" method="post" >
        <%
            int k = 0;
            for (String i : words
            ) {
                String str = "    <div class=\"row\">";
                str += "    <div class=\"card-panel teal transparent z-depth-3\" style=\"margin-bottom: 70px;\">" +
                        "" +
                        "                    <h5 style=\"margin-left: 20px;\">" + i;
                str += "</h5>" +
                        "                    <ul class=\"collapsible popout\" data-collapsible=\"accordion\">" +
                        "                        <li>" +
                        "                            <div class=\"collapsible-header  hoverable\">Customer</div>" +
                        "                            <div class=\"collapsible-body \">" +
                        "" +
                        "                                <div class=\"row\">" +
                        "                                    <div class=\"input-field col s6\">" +
                        "                                        <input id=\"first_name\" name = \"customer_first_name"+k+"\" type=\"text\" class=\"validate\" required>" +
                        "                                        <label for=\"first_name\">First Name</label>" +
                        "                                    </div>" +
                        "                                    <div class=\"input-field col s6\">" +
                        "                                        <input id=\"last_name\"  name = \"customer_last_name"+k+"\" type=\"text\" class=\"validate\" required>" +
                        "                                        <label for=\"last_name\">Last Name</label>" +
                        "                                    </div>" +
                        "                                </div>" +
                        "" +
                        "                                <div class=\"row\">" +
                        "                                    <div class=\"input-field col s12\">" +
                        "                                        <input id=\"email\" name = \"customer_phone"+k+"\" type=\"tel\" maxlength=8 minlength=8 class=\"validate\" required>" +
                        "                                        <label for=\"email\">Phone number</label>" +
                        "                                    </div>" +
                        "                                </div>" +
                        "" +
                        "                                <div class=\"row\">" +
                        "                                    <div class=\"input-field col s12\">" +
                        "                                        <input id=\"email\" type=\"email\" name = \"customer_email"+k+"\" class=\"validate\" required>" +
                        "                                        <label for=\"email\">Email</label>" +
                        "                                    </div>" +
                        "                                </div>" +
                        "" +
                        "" +
                        "                            </div>" +
                        "                        </li>";

                str += "<li>" +
                        "                            <div class=\"collapsible-header hoverable\">Date and time</div>" +
                        "                            <div class=\"collapsible-body\">" +
                        "" +
                        "                                <div class=\"row\">" +
                        "" +
                        "                                    <div class=\"input-field col s12\">" +
                        "                                        <input type=\"text\" name = 'date"+k+"' class=\"datepicker\" required>" +
                        "                                        <label for=\"Date\">Date</label>" +
                        "                                    </div>" +
                        "                                </div>" +
                        "" +
                        "                                <div class=\"row\">" +
                        "                                    <div class=\"input-field col s5\">" +
                        "                                        <input type=\"text\" class=\"timepicker\" name = \"first_time"+k+"\" required>" +
                        "                                        <label for=\"last_name\">First time</label>" +
                        "                                    </div>" +
                        "" +
                        "                                    <div class=\"col s1\">" +
                        "                                        <p style=\"  display: flex;" +
                        "                                            align-items: center;" +
                        "                                            justify-content: center;\">To</p>" +
                        "                                    </div>" +
                        "" +
                        "                                    <div class=\"input-field col s5\">" +
                        "" +
                        "                                        <input type=\"text\" class=\"timepicker\" name = \"last_time"+k+"\" required>" +
                        "                                        <label for=\"last_name\">Last Time</label>" +
                        "                                    </div>" +
                        "                                </div>" +
                        "" +
                        "" +
                        "                            </div>" +
                        "                        </li>";


                str += "        <li>" +
                        "                            <div class=\"collapsible-header hoverable\">Guest list</div>" +
                        "                            <div class=\"collapsible-body\">";


                for (int j = k*10; j <= k*10+10; j++) {
                    str += "<div class=\"row\">" +
                            "            <div class=\"input-field col s6\">" +
                            "                <input type=\"text\" class=\"validate\" name =\"guest_name" + j + "\">" +
                            "                <label for=\"first_name\">Name</label>" +
                            "            </div>" +
                            "            <div class=\"input-field col s6\">" +
                            "                <input id=\"last_name\" type=\"email\" class=\"validate\" name = \"guest_email" + j + "\">" +
                            "                <label for=\"last_name\">Email</label>" +
                            "            </div>" +
                            "        </div>";

                }

                str += "<input type='hidden' name='user_id' value ='"+ userInfo.getUserId() +"'/>";
                str += "<input type='hidden' name='venue"+k+"' value ='"+ i +"'/>";
                str += "<input type='hidden' name='action' value ='booking'/>";

                str += " </div>    </li>" +
                        "    </ul></div>" +
                        "</div>";

                out.println(str);


                k++;
            }

        %>
        </form>

        <div style="padding-top: 50px;">
            <button class="btn waves-effect waves-light" type="submit"  onclick="submitForms()">Submit Order

            </button>
        </div>
    </div>
</div>


<script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
<script src="./js/index.js"></script>


<script src="https://code.jquery.com/jquery-3.6.4.js"
        integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>

<script>

    $('.timepicker').pickatime({
        default: 'now', // Set default time: 'now', '1:30AM', '16:30'
        fromnow: 0,       // set default time to * milliseconds from now (using with default = 'now')
        twelvehour: false, // Use AM/PM or 24-hour format
        donetext: 'OK', // text for done-button
        cleartext: 'Clear', // text for clear-button
        canceltext: 'Cancel', // Text for cancel-button,
        container: undefined, // ex. 'body' will append picker to body
        autoclose: false, // automatic close timepicker
        ampmclickable: true, // make AM PM clickable
        aftershow: function () {
        } //Function for after opening timepicker
    });


    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15, // Creates a dropdown of 15 years to control year,
        today: 'Today',
        clear: 'Clear',
        close: 'Ok',
        closeOnSelect: false, // Close upon selecting a date,
        container: undefined, // ex. 'body' will append picker to body
    });

    function submitForms(){
        document.getElementById('form').submit();
    }


</script>


</body>
</html>
