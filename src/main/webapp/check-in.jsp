<%--
  Created by IntelliJ IDEA.
  User: 93140
  Date: 4/11/2023
  Time: 2:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel='stylesheet' href='./css/index.css'>

    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css'>
</head>
<body>
<jsp:useBean id="userInfo" class="bean.UserInfo" scope="session"/>

<%@ taglib prefix="order" uri="/WEB-INF/Order.tld" %>
<%@ taglib prefix="venue" uri="/WEB-INF/venue.tld" %>


<div class='row'>
    <div class='col s1 m1 l1'>
        <div class='l-navbar ' id='navbar'>
            <nav1 class='nav'>
                <div>
                    <div class='nav_brand'>
                        <!-- <ion-icon name='menu-outline' class='nav_toggle' id='nav_toggle'></ion-icon> -->
                        <ion-icon name='menu-outline' class='nav_toggle' id='nav-toggle'></ion-icon>

                        <a href="#" class="nav_logo">Welcome <%=userInfo.getName() %>
                    </div>


                    <div class='nav_list'>
                        <a href='OrderProcess?action=list' class='nav_link '>
                            <ion-icon name='cart-outline' class='nav_icon'></ion-icon>
                            <span class='nav_name'>Pending order</span>
                        </a>
                        <a href='OrderController?action=check-in' class='nav_link active1'>
                            <ion-icon name='checkmark-outline' class='nav_icon'></ion-icon>
                            <span class='nav_name'>Check-in</span>
                        </a>

                        <a href='OrderController?action=check-out' class='nav_link'>
                            <ion-icon name='checkmark-done-outline' class='nav_icon'></ion-icon>
                            <span class='nav_name'>Check-out</span>
                        </a>

                        <a href='venue?action=list' class='nav_link'>
                            <ion-icon name='radio-button-on-outline' class='nav_icon'></ion-icon>
                            <span class='nav_name'>Check-out</span>
                        </a>
                    </div>
                </div>
                <a href="clearSessionServlet" class="nav_link">
                    <ion-icon name='log-out-outline' class='nav_icon'></ion-icon>
                    <span class='nav_name'>Log out</span>
                </a>
            </nav1>
        </div>
    </div>
    <div class='col l10 '>


        <div class="row z-depth-1" style="padding: 20px;">
            <h5>Select location</h5>
            <form class="col s12" action="OrderProcess" method="post">
                <input type="hidden" name="page" value="check-in">
                <div class="input-field col s6">
                    <select name="venue_id">

                        <venue:venue venues="${venues}"/>

                    </select>
                </div>

                <button class="btn waves-effect waves-light grey darken-4" type="submit" name="action"
                        value="searchByVenue" style="margin-top: 20px;">Search

                </button>
            </form>

            <table class='highlight centered'>
                <thead>
                <tr>
                    <th>Order number</th>
                    <th>Customer Name</th>
                    <th>Customer Phone</th>
                    <th>Venue</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Details</th>

                </tr>
                </thead>

                <order:order orders="${orders}"/>
                </tbody>
            </table>
        </div>


    </div>
</div>


<script src='https://unpkg.com/ionicons@5.1.2/dist/ionicons.js'></script>
<script src='./js/index.js'></script>


<script src='https://code.jquery.com/jquery-3.6.4.js'
        integrity='sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=' crossorigin='anonymous'></script>

<script src='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js'></script>

<script>
    $(document).ready(function () {
        $('select').material_select();
    });


</script>

</body>
</html>
