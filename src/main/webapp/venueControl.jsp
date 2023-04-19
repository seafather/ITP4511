<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.VenueBean" %><%--
  Created by IntelliJ IDEA.
  User: 93140
  Date: 4/10/2023
  Time: 6:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html;charset=UTF-8' language='java' %>
<html>
<head>
    <title>Title</title>
    <link rel='stylesheet' href='./css/index.css'>

    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css'>
</head>
<body>

<jsp:useBean id='userInfo' class='bean.UserInfo' scope='session'/>

<%@ taglib prefix="venuesList" uri="/WEB-INF/venue.tld" %>

<%@ taglib prefix="Editvenue" uri="/WEB-INF/VenueInfo.tld" %>


<%
    boolean isEditActive;
    if(((VenueBean)request.getAttribute("venue")).getName() == null){
        isEditActive = false;
    }else{
        isEditActive = true;
    }
%>
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
                        <a href='OrderController?action=check-in' class='nav_link '>
                            <ion-icon name='checkmark-outline' class='nav_icon'></ion-icon>
                            <span class='nav_name'>Check-in</span>
                        </a>

                        <a href='OrderController?action=check-out' class='nav_link'>
                            <ion-icon name='checkmark-done-outline' class='nav_icon'></ion-icon>
                            <span class='nav_name'>Check-out</span>
                        </a>

                        <a href='venue&action=list' class='nav_link active1'>
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

        <div class='row z-depth-1' style=''>
            <div class='col s12 '>
                <ul class='tabs tabs-fixed-width'>
                    <li class='tab col s3'><a class="<%= isEditActive ? "" : "active" %>" href='#test1'>Add venue</a></li>
                    <li class='tab col s3'><a class="<%= isEditActive ? "active" : "" %>" href='#test2'>edit venue</a></li>
                    <li class='tab col s3'><a  href='#test3'>Delete venue</a></li>
                    <li class='tab col s3'><a href='#test4'>Enable/disable venue</a></li>
                </ul>
            </div>
            <div id='test1' class='col s12'>
                <Editvenue:EditVenue venue="${venue}" action="add"/>


            </div>
            <div id='test2' class='col s12 ' style='padding:20px;'>

                <Editvenue:EditVenue venue="${venue}" action="edit"/>


                <table class='highlight centered'>
                    <thead>
                    <tr>
                        <th>Venue ID</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Hour fee</th>
                        <th>Capacity</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>

                    <venuesList:venueList venues="${venues}" action="Edit"/>


                    </tbody>
                </table>

            </div>

            <div id='test3' class='col s12' style='padding:20px;'>
                <table class='highlight centered'>
                    <thead>
                    <tr>
                        <th>Venue ID</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Hour fee</th>
                        <th>Capacity</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>

                    <venuesList:venueList venues="${venues}" action="Delete"/>

                    </tbody>
                </table>
            </div>

            <div id='test4' class='col s12' style='padding:20px;'>
                <table class='highlight centered'>
                    <thead>
                    <tr>
                        <th>Venue ID</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Hour fee</th>
                        <th>Capacity</th>
                        <th>Action</th>

                    </tr>
                    </thead>

                    <tbody>
                    <venuesList:venueList venues="${venues}" action="disable"/>

                    </tbody>
                </table>
            </div>

        </div>
    </div>

</div>


<script src='https://unpkg.com/ionicons@5.1.2/dist/ionicons.js'></script>
<script src='./js/index.js'></script>


<script src='https://code.jquery.com/jquery-3.6.4.js'
        integrity='sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=' crossorigin='anonymous'></script>

<script src='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js'></script>


</body>
</html>
