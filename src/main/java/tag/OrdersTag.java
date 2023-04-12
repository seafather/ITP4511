package tag;

import bean.Guest;
import bean.OrderInfo;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.util.ArrayList;

public class OrdersTag extends SimpleTagSupport {
    private ArrayList<OrderInfo> orders;

    public void setOrders(ArrayList<OrderInfo> guests) {
        this.orders = guests;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();

        String str = "";

        for ( OrderInfo i :orders){
            str += "<tr>";
            str += "<td>" +String.format("%07d", i.getOrder_ID()) +"</td>";
            str += "<td>" +i.getCustomerName() +"</td>";
            str += "<td>" +i.getCustomerNumber() +"</td>";
            str += "<td>" +i.getVenue_name() +"</td>";
            str += "<td>" +i.getDate() +"</td>";
            str += "<td>" +i.getStartTime().substring(0,2)+":00 - "+i.getEndTime().substring(0,2) +":00</td>";
            str += "<td>" +"<a class='waves-effect waves-light btn' href='OrderController?action=preview&booking_id="+i.getOrder_ID()+"&venue_id="+i.getVenue_id()+"'>View</a></td>";
            str += "<tr>";
        }

        out.print(str);



    }
}
