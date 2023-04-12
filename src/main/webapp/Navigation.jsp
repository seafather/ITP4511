<%--
  Created by IntelliJ IDEA.
  User: 93140
  Date: 4/11/2023
  Time: 3:34 PM
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="userInfo" class="bean.UserInfo" scope="session"/>


<div class="col s1 m1 l1">
    <div class="l-navbar " id="navbar">
        <nav1 class="nav">
            <div>
                <div class="nav_brand">
                    <!-- <ion-icon name="menu-outline" class="nav_toggle" id="nav_toggle"></ion-icon> -->
                    <ion-icon name="menu-outline" class="nav_toggle" id="nav-toggle"></ion-icon>

                    <a href="#" class="nav_logo">Welcome <%=userInfo.getName() %>
                </div>

                <%
                if(userInfo.getPosition().equals("customer")){
                    out.print("<div class='nav_list'>" +
                            "    <a href='jumpToBook.jsp' class='nav_link '>" +
                            "        <ion-icon name='home-outline' class='nav_icon'></ion-icon>" +
                            "        <span class='nav_name'>Reserve</span>" +
                            "    </a>" +
                            "    <a href='OrderController?action=list' class='nav_link active1'>" +
                            "        <ion-icon name='chatbubbles-outline' class='nav_icon'></ion-icon>" +
                            "        <span class='nav_name'>Order</span>" +
                            "    </a>" +
                            "</div>");
                }else{
                    out.print(" <div class='nav_list'>" +
                            "                        <a href='OrderProcess?action=list' class='nav_link active1'>" +
                            "                            <ion-icon name='cart-outline' class='nav_icon'></ion-icon>" +
                            "                            <span class='nav_name'>Pending order</span>" +
                            "                        </a>" +
                            "                        <a href='OrderController?action=check-in' class='nav_link'>" +
                            "                            <ion-icon name='checkmark-outline' class='nav_icon'></ion-icon>" +
                            "                            <span class='nav_name'>Check-in</span>" +
                            "                        </a>" +
                            "" +
                            "                         <a href='OrderController?action=check-out' class='nav_link'>" +
                            "                            <ion-icon name='checkmark-done-outline' class='nav_icon'></ion-icon>" +
                            "                            <span class='nav_name'>Check-out</span>" +
                            "                        </a>" +
                            "                         <a href='venue?action=list' class='nav_link'>" +
                            "                            <ion-icon name='radio-button-on-outline' class='nav_icon'></ion-icon>" +
                            "                            <span class='nav_name'>Check-out</span>" +
                            "                        </a>" +
                            "                    </div>");
                }
                %>



            </div>
            <a href="clearSessionServlet" class="nav_link">
                <ion-icon name="log-out-outline" class="nav_icon"></ion-icon>
                <span class="nav_name">Log out</span>
            </a>
        </nav1>
    </div>
</div>
