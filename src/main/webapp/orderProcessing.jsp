<%--
  Created by IntelliJ IDEA.
  User: 93140
  Date: 4/10/2023
  Time: 6:01 PM
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

<%@ taglib prefix="pendingOrder" uri="/WEB-INF/Order.tld" %>
<%@ taglib prefix="order" uri="/WEB-INF/Order.tld" %>
<%@ taglib prefix="venue" uri="/WEB-INF/venue.tld" %>


<div class='row'>

    <jsp:include page="Navigation.jsp">
        <jsp:param name="userInfo" value="<%= userInfo %>"/>
    </jsp:include>

    <div class='col l10 '>

        <div class="row z-depth-1">

            <div class="card-tabs">
                <ul class="tabs tabs-fixed-width">
                    <li class="tab"><a class="active" href="#test4">All order</a></li>
                    <li class="tab"><a href="#test5">Pending order</a></li>
                </ul>
            </div>
            <div class="card-content white " style="padding: 20px;">
                <div id="test4">

                    <div class="row">
                        <form class="col s12" action="OrderProcess" method="post">
                            <input type="hidden" name="page" value="allOrder">
                            <div class="input-field col s6">
                                <select name="venue_id">

                                    <venue:venue venues="${venues}"/>

                                </select>
                            </div>

                            <button class="btn waves-effect waves-light grey darken-4" type="submit" name="action"
                                    value="searchByVenue" style="margin-top: 20px;">Search

                            </button>
                        </form>
                    </div>
                    <form action='' style='margin-top: 20px;'>

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

                            <tbody>
                            <order:order orders="${Orders}"/>


                            </tbody>
                        </table>

                    </form>
                </div>
                <div id="test5">
                    <form action='' style='margin-top: 20px;'>

                        <table class='highlight centered'>
                            <thead>
                            <tr>
                                <th>Order number</th>
                                <th>Customer Name</th>
                                <th>Customer Phone</th>
                                <th>Date</th>
                                <th>Time</th>

                            </tr>
                            </thead>

                            <tbody>
                            <pendingOrder:pending pendingList="${pendingOrders}"/>
                            </tbody>
                        </table>

                    </form>
                </div>
            </div>
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
