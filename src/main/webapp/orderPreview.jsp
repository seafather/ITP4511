<%@ page import="bean.Guest" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 93140
  Date: 4/9/2023
  Time: 4:04 PM
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
<jsp:useBean id="order" class="bean.OrderInfo" scope="request"/>


<%@ taglib prefix="guestTag" uri="/WEB-INF/guest.tld" %>
<%@ taglib prefix="venueInfoTag" uri="/WEB-INF/VenueInfo.tld" %>


<div class="row">
    <jsp:include page="Navigation.jsp">
        <jsp:param name="userInfo" value="<%= userInfo %>"/>
    </jsp:include>


    <div class="col l10 ">

        <div class="row">

            <div class="card-panel teal transparent z-depth-1" style="margin-bottom: 70px;">

                <h5 style="margin-left: 20px;"> Preview </h5>
                <ul class="collapsible popout" data-collapsible="accordion">
                    <li>
                        <div class="collapsible-header  hoverable">Order information</div>
                        <div class="collapsible-body ">

                            <div class="row">
                                <div class="input-field col s6">
                                    <input value=<%= "'"+order.getOrder_ID()+"'"%>  readonly type="text">
                                    <label class="active">Order number</label>
                                </div>
                                <div class="input-field col s6">
                                    <input value=<%= "'"+order.getCreateBytDatetime()+"'"%> readonly type="text">
                                    <label class="active">Order date</label>
                                </div>
                            </div>


                            <div class="row">
                                <div class="input-field col s6">
                                    <input value=<%= "'"+order.getState()+"'"%> readonly type="text">
                                    <label class="active">state</label>
                                </div>
                                <div class="input-field col s6">
                                    <input value=<%= "'"+order.getTotalFee()+"'"%> readonly type="text">
                                    <label class="active">Total fee</label>
                                </div>
                            </div>
                        </div>
                    </li>


                    <venueInfoTag:venueInfo venue="${venueBean}"/>


                    <li>
                        <div class="collapsible-header  hoverable">Customer information</div>
                        <div class="collapsible-body ">

                            <div class="row">
                                <div class="input-field col s6">
                                    <input value=<%= "'"+order.getCustomerName()+"'"%>  readonly type="text">
                                    <label class="active">Customer name</label>
                                </div>
                            </div>

                            <div class="row">
                                <div class="input-field col s6">
                                    <input value=<%= "'"+order.getCustomerNumber()+"'"%>  type="text">
                                    <label class="active">Customer phone</label>
                                </div>
                                <div class="input-field col s6">
                                    <input value=<%= "'"+order.getEmail()+"'"%>   type="text">
                                    <label class="active">Customer email</label>
                                </div>
                            </div>

                        </div>
                    </li>


                    <guestTag:guest guests="${guests}" isRequired="false"/>


                </ul>
            </div>
        </div>

        <% String str = "";

            if (request.getParameter("state") != null) {
                str = "<form action='OrderProcess' method='post' >" +
                        "                <div style='padding-top: 50px;'>" +
                        "<input type='hidden' name ='booking_id' value = '" + request.getParameter("booking_id") + "' />" +
                        "<input type='hidden' name='url' value = 'OrderController?action=processing&booking_id=" + request.getParameter("booking_id") + "&venue_id=" + request.getParameter("venue_id") + "' />" +
                        "                    <button class='btn waves-effect waves-light  red lighten-1' type='submit' name='action'" +
                        "                        value='reject'>Reject" +
                        "                    </button>";
                if (request.getParameter("error") == null) {
                    str += "                    <button class='btn waves-effect waves-light' style='margin-left:50px;' type='submit' name='action'" +
                            "                        value='approve'>Approve" +
                            "                    </button>";

                }


                str += "                                    </div>" +
                        "                                 </form>";

            } else {

                if (order.getState().equals("Check-in")) {
                    str += "    <form id = 'check-out' action='OrderProcess' method='post' >" +
                            "   <input type='hidden' name='booking_id' value = '" + order.getOrder_ID() + "'>" +
                            "   <input type='hidden'  name='action' value='check-out'>" +
                            "<ul class='collapsible' data-collapsible='accordion'>" +
                            "                <li>" +
                            "                    <div class='collapsible-header'><ion-icon class='nav_icon' name='people-outline'" +
                            "                            style='margin-right:10px;'></ion-icon>Any necessary remarks" +
                            "                            about damages or special issues</div>" +
                            "                    <div class='collapsible-body'>" +
                            "" +
                            "" +
                            "                        <div class='row'>" +
                            "                            <div class='col s12'>" +
                            "                                <div class='row'>" +
                            "                                    <div class='input-field col s12'>" +
                            "                                        <textarea id='textarea1' name = 'damage_descrption' class='materialize-textarea'></textarea>" +
                            "                                        <label for='textarea1'>Textarea</label>" +
                            "                                    </div>" +
                            "                                </div>" +
                            "                            </div>" +
                            "                        </div>" +
                            "                    </div>" +
                            "                </li>" +
                            "            </ul></form>";


                }
                str += "<div>";
                if (order.getState().equals("Approve")) {
                    str += "<a class='btn waves-effect waves-light  red accent-1' style='margin-right:20px;' href = 'OrderProcess?action=check-in&booking_id=" + order.getOrder_ID() + "'>Check-in </a>";

                } else if (order.getState().equals("Check-in")) {
//                    str += "<a class='btn waves-effect waves-light  red accent-1' style='margin-right:20px;' href = ''>finish </a>";

                }
                if (!order.getState().equals("Check-in")) {
                    str += "<a class='btn waves-effect waves-light' onClick='comeBack()' name='action'>Back</a>";
                } else {
                    str += "<a class='btn waves-effect waves-light red accent-2'  onclick = 'submitForm()' >Submit</a>";

                }
                str += "</div>";

            }

            out.print(str);
        %>

    </div>
</div>


<script src='https://unpkg.com/ionicons@5.1.2/dist/ionicons.js'></script>
<script src='./js/index.js'></script>


<script src='https://code.jquery.com/jquery-3.6.4.js'
        integrity='sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=' crossorigin='anonymous'></script>

<script src='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js'></script>

<script>
    function comeBack() {
        window.history.back();
    }

    function submitForm() {
        document.getElementById("check-out").submit();
    }
</script>
</body>
</html>
