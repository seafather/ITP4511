package tag;

import bean.Guest;
import bean.OrderInfo;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.util.ArrayList;

public class pendingListTag extends SimpleTagSupport {
    private ArrayList<OrderInfo> pendingList;

    public void setPendingList(ArrayList<OrderInfo> pendingList) {
        this.pendingList = pendingList;
    }


    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();


        String str = "";
        
        for(OrderInfo i : pendingList){
            str += "<tr>" +
                    "                    <td>"+String.format("%07d", i.getOrder_ID())+"</td>" +
                    "                    <td>"+i.getCustomerName() +
                    "                    <td>"+i.getDate()+"</td>" +
                    "                    <td>"+i.getStartTime().substring(0,2)+":00 - " +i.getEndTime().substring(0,2) +":00</td>" +
                    "                    <td>" +
                    "                        <a class='waves-effect waves-light btn' href='OrderController?action=processing&booking_id="+i.getOrder_ID()+"&venue_id="+i.getVenue_id()+"'>view</a>" +
                    "                    </td>" +
                    "                </tr>";
        }

        out.print(str);


    }
}
