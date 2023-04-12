package tag;

import bean.OrderInfo;
import bean.VenueBean;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.util.ArrayList;

public class VenusTag extends SimpleTagSupport {
    private ArrayList<VenueBean> venues;

    public void setVenues(ArrayList<VenueBean> venues) {
        this.venues = venues;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();

        String str = "<option value='' disabled selected>Choose your option</option>";


        for ( VenueBean i :venues){
            str += "<option value = '" + i.getVenue_id()+"'>" +i.getName() +"</option>";
        }

        out.print(str);



    }
}
