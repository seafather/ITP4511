package tag;

import bean.Guest;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class guestListTag  extends SimpleTagSupport {
    private ArrayList<Guest> guests;
    private boolean isRequired;

    public void setGuests(ArrayList<Guest> guests) {
        this.guests = guests;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }


    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();

        String requiredStr = "readonly";
        if (isRequired){
            requiredStr = "class='validate' ";
        }

        String str = "";

         str += "<li>" +
                 "                                <div class=\"collapsible-header hoverable\">Guest list</div>" +
                 "                                <div class=\"collapsible-body\">";

        if (guests != null) {
            int i = 0;
            for (Guest guest : guests) {
                str += " <div class=\"row\">" +
                        "                                        <div class=\"input-field col s6\">" +
                        "                                            <input type=\"text\"   value='"+guest.getGuestName()+"'"+requiredStr+"name='guest_name"+i+"'"+">" +
                        "                                            <label >Name</label>" +
                        "                                        </div>" +
                        "                                        <div class=\"input-field col s6\">" +
                        "                                            <input  type=\"email\"   value='"+guest.getGuestEmail()+"'"+requiredStr+"name='guest_email"+i+"'"+">" +
                        "                                            <label >Email</label>" +
                        "                                        </div>" +
                        "                                    </div>";
                i++;
            }
            
            str += " </div>" +
                    "                            </li>";


        }
        out.print(str);


    }
}
