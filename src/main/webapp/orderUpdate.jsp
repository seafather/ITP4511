<%--
  Created by IntelliJ IDEA.
  User: 93140
  Date: 4/10/2023
  Time: 2:38 PM
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
<jsp:useBean id="order" class="bean.OrderInfo" scope="request" />

<%@ taglib prefix="guestTag" uri="/WEB-INF/guest.tld" %>


<div class='row'>
    <div class='col s1 m1 l1'>
        <div class='l-navbar ' id='navbar'>
            <nav1 class="nav">
                <div>
                    <div class="nav_brand">
                        <!-- <ion-icon name="menu-outline" class="nav_toggle" id="nav_toggle"></ion-icon> -->
                        <ion-icon name="menu-outline" class="nav_toggle" id="nav-toggle"></ion-icon>

                        <a href="#" class="nav_logo">Welcome <%=userInfo.getName() %>
                    </div>


                    <div class="nav_list">
                        <a href="jumpToBook.jsp" class="nav_link">
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


    <div class='col l10 '>
        <form id='form' action='booking' method='post'>
            <div class='row'>

                <div class='card-panel teal transparent z-depth-3' style='margin-bottom: 70px;'>

                    <h5 style='margin-left: 20px;'> Update </h5>
                    <ul class='collapsible popout' data-collapsible='accordion'>

                        <li>
                            <div class='collapsible-header  hoverable'>Customer information</div>
                            <div class='collapsible-body '>

                                <div class='row'>
                                    <div class='input-field col s6'>
                                        <input value= '<%= order.getCustomerName() %>' name="customer_name" type='text' class='validate'>
                                        <label class='active'>Customer name</label>
                                    </div>

                                </div>

                                <div class='row'>
                                    <div class='input-field col s6'>
                                        <input value='<%= order.getCustomerNumber() %>'   name="customer_phone" type='tel' maxlength=8 minlength=8 class='validate'>
                                        <label class='active'>Customer phone</label>
                                    </div>
                                    <div class='input-field col s6'>
                                        <input value='<%= order.getEmail() %>'   name="customer_email" type='email' class='validate'>
                                        <label class='active'>Customer email</label>
                                    </div>
                                </div>

                            </div>
                        </li>

                        <guestTag:guest guests="${guests}" isRequired="true" />


                    </ul>

                </div>
            </div>

            <input type="hidden" name='action' value='update'>
            <input type="hidden" name="booking_id" value = <%= order.getOrder_ID()%>>

            <div style='padding-top: 50px;'>
                <button class='btn waves-effect waves-light' type='submit' >Submit

                </button>
            </div>

        </form>





    </div>
</div>

<script src='https://unpkg.com/ionicons@5.1.2/dist/ionicons.js'></script>
<script src='./js/index.js'></script>


<script src='https://code.jquery.com/jquery-3.6.4.js'
        integrity='sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=' crossorigin='anonymous'></script>

<script src='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js'></script>
</body>
</html>
