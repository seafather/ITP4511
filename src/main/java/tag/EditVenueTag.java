package tag;

import bean.VenueBean;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.util.ArrayList;

public class EditVenueTag extends SimpleTagSupport {
    private VenueBean venue;
    private String action;

    public void setVenue(VenueBean venue) {
        this.venue = venue;
    }

    public void setAction(String action) {
        this.action = action;
        System.out.println(action);

    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();

        String str = "";

        if (action.equals("add")) {
            str = "<div class='row'>" +
                    "<form class='col s12' style='padding: 20px;' action='venue' method='post'>" +
                    "<div class='row'>" +
                    "<div class='input-field col s6'>" +
                    "<input type='text' class='validate' required name='venue_name'>" +
                    "<label>Venue name</label>" +
                    "</div>" +
                    "<div class='input-field col s6'>" +
                    "<input type='text' class='validate' required name='venue_type'>" +
                    "<label>Venue type</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='row'>" +
                    "<div class='input-field col s6'>" +
                    "<input type='number' min=1 class='validate' required name='venue_capacity'>" +
                    "<label>Maximum capacity</label>" +
                    "</div>" +
                    "<div class='input-field col s6'>" +
                    "<input type='number' min=1 class='validate' required name='venue_fee'>" +
                    "<label>Hour fee</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='row'>" +
                    "<div class='input-field col s12'>" +
                    "<input type='text' class='validate' required name='venue_location'>" +
                    "<label>Location</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='row'>" +
                    "<div class='input-field col s12'>" +
                    "<textarea class='materialize-textarea' required name='venue_descrption'></textarea>" +
                    "<label>Textarea</label>" +
                    "</div>" +
                    "</div>" +
                    "<button class='btn waves-effect waves-light blue accent-2' type='submit' name='action' value='add'>Add venue</button>" +
                    "</form>" +
                    "</div>";
        } else if(action.equals("edit") && venue.getName() != null){
            System.out.println("i am");
            str = "<div class='row'>" +
                    "<form class='col s12' style='padding: 20px;' action='venue' method='post'>" +
                    "<div class='row'>" +
                    "<div class='input-field col s6'>" +
                    "<input type='text' class='validate' required name='venue_name' value= '"+venue.getName()+"'>" +
                    "<label>Venue name</label>" +
                    "</div>" +
                    "<div class='input-field col s6'>" +
                    "<input type='text' class='validate' required name='venue_type' value='"+venue.getType()+"'>" +
                    "<label>Venue type</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='row'>" +
                    "<div class='input-field col s6'>" +
                    "<input type='number' min=1 class='validate' required name='venue_capacity' value='"+venue.getMax_people()+"'>" +
                    "<label>Maximum capacity</label>" +
                    "</div>" +
                    "<div class='input-field col s6'>" +
                    "<input type='number' min=1 class='validate' required name='venue_fee' value='"+venue.getHoursFee()+"'>" +
                    "<label>Hour fee</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='row'>" +
                    "<div class='input-field col s12'>" +
                    "<input type='text' class='validate' required name='venue_location' value='"+venue.getLocation()+"'>" +
                    "<label>Location</label>" +
                    "</div>" +
                    "</div>" +
                    "<div class='row'>" +
                    "<div class='input-field col s12'>" +
                    "<textarea class='materialize-textarea' required name='venue_descrption' value='"+venue.getDescription()+"'></textarea>" +
                    "<label>Textarea</label>" +
                    "</div>" +
                    "</div>" +
                    "<input type='hidden' name='venue_id' value='"+venue.getVenue_id()+ "'>"+
                    "<button class='btn waves-effect waves-light blue accent-2' type='submit' name='action' value='Edit'>Edit venue</button>" +
                    "</form>" +
                    "</div>";
        }
        out.print(str);


    }
}
