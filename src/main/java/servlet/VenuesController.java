package servlet;

import bean.VenueBean;
import db.DB;

import db.VenueDB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "VenuesController", urlPatterns = "/venue")
public class VenuesController extends HttpServlet {
    private VenueDB venueDB;

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        venueDB = new VenueDB(new DB(dbUrl, dbUser, dbPassword));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "/venueControl.jsp";
        //  System.out.println(request.getRequestURI().substring(0, str.indexOf("/", 1)););

        ArrayList<VenueBean> venues = new ArrayList<VenueBean>();
        VenueBean venueBean = new VenueBean();

        int venue_id, venue_capacity;
        String venue_name, venue_type, venue_location, venue_descrption;
        Double venue_fee;


        switch (action) {
            case "customer":
                url = "/book.jsp";
                venues = venueDB.getActiveVenues();
                break;
            case "list":
                venues = venueDB.queryVenue();
                break;
            case "add":
                venue_name = request.getParameter("venue_name");
                venue_type = request.getParameter("venue_type");
                venue_capacity = Integer.parseInt(request.getParameter("venue_capacity"));
                venue_fee = Double.parseDouble(request.getParameter("venue_fee"));
                venue_location = request.getParameter("venue_location");
                venue_descrption = request.getParameter("venue_descrption");


                venueDB.addVenue(venue_name, venue_type, venue_capacity, venue_location, venue_descrption,venue_fee);
                venues = venueDB.queryVenue();

                break;
            case "Edit":
                if (request.getParameter("venue_name") != null) {
                    venue_id = Integer.parseInt(request.getParameter("venue_id"));
                    venue_name = request.getParameter("venue_name");
                    venue_type = request.getParameter("venue_type");
                    venue_capacity = Integer.parseInt(request.getParameter("venue_capacity"));
                    venue_fee = Double.parseDouble(request.getParameter("venue_fee"));
                    venue_location = request.getParameter("venue_location");
                    venue_descrption = request.getParameter("venue_descrption");


                    venueDB.updateRecord(venue_id,venue_name,venue_type,venue_capacity,venue_fee,venue_location,venue_descrption);
                }else {
                    venue_id = Integer.parseInt(request.getParameter("venue_id"));
                    venueBean = venueDB.getVenueByID(venue_id);
                    url += "?action=edit";
                }

                venues = venueDB.queryVenue();

                break;
            case "Delete":
                venue_id = Integer.parseInt(request.getParameter("venue_id"));


                venueDB.deleteVenueById(venue_id);
                venues = venueDB.queryVenue();
                break;

            case "disable":
                venue_id = Integer.parseInt(request.getParameter("venue_id"));


                venueDB.toggleVenueActive(venue_id);
                venues = venueDB.queryVenue();
                break;
        }

        request.setAttribute("venues", venues);
        request.setAttribute("venue", venueBean);


        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);


    }
}
