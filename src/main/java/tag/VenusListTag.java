package tag;

import bean.VenueBean;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.util.ArrayList;

public class VenusListTag extends SimpleTagSupport {
    private ArrayList<VenueBean> venues;
    private String action;
    private String buttonColor;

    public void setVenues(ArrayList<VenueBean> venues) {
        this.venues = venues;
    }

    public void setAction(String action) {
        this.action = action;

        switch (action){
            case "Delete":
                buttonColor = "red accent-3";break;
            default:
                buttonColor = "teal accent-3";break;
        }
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();

        String str = "";
        for ( VenueBean i :venues){

            str += "<tr>";
            str += "<td>"+String.format("%07d",i.getVenue_id())+"</td>";
            str += "<td>"+i.getName()+"</td>";
            str += "<td>"+i.getType()+"</td>";
            str += "<td>"+i.getHoursFee()+"</td>";
            str += "<td>"+i.getMax_people()+"</td>";


            if(action.equals("disable") && i.isActive()){
                str += "<td>"+"<a class='btn waves-effect waves-light " +"teal accent-3'"+
                        "href ='/venue?action="+"disable"+"&venue_id="+i.getVenue_id()+"'>"+"Disable"+"</td>";
            }else if (action.equals("disable")&& (!i.isActive())){
                str += "<td>"+"<a class='btn waves-effect waves-light " +"red accent-3'"+
                        "href ='/venue?action="+"enable"+"&venue_id="+i.getVenue_id()+"'>"+"Enable"+"</td>";
            }else{
                str += "<td>"+"<a class='btn waves-effect waves-light " +buttonColor+"'"+
                        "href ='/venue?action="+action+"&venue_id="+i.getVenue_id()+"'>"+action+"</td>";

            }
            str += "</tr>";


        }
        out.print(str);





    }
}
