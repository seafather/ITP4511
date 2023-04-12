package tag;

import bean.VenueBean;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.util.ArrayList;

public class VenueInfoTag extends SimpleTagSupport {
    private VenueBean venue;

    public void setVenue(VenueBean venue) {
        this.venue = venue;
    }

    @Override
    public void doTag() throws IOException {

        JspWriter out = getJspContext().getOut();

        String str;

        str = "<li>";
        str += "<div class='collapsible-header  hoverable'>Vnue information</div>";
        str += "<div class='collapsible-body '>" ;

        str += "  <div class='row'> ";
        str +=
                "<div class='input-field col s6'>" +
                " <input value='"+venue.getName()+"' readonly type='text'>" +
                " <label class='active'>Venue name</label>" +
                "  </div>" +
                "  <div class='input-field col s6'>" +
                " <input value='"+venue.getType()+"' readonly type='text'>" +
                " <label class='active'>Venue type</label>" +
                " </div>" +
                "" +
                "  </div>" +
                "" +
                " <div class='row'>" +
                " <div class='input-field col s12'>" +
                "  <input value='"+venue.getLocation()+ "'readonly type='text'>" +
                " <label class='active'>Venue location</label>" +
                " </div>" +
                "  </div>";

        out.print(str);



    }
}
