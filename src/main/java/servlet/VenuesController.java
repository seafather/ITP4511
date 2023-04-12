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

        venueDB = new VenueDB(new DB(dbUrl,dbUser,dbPassword));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "/venueControl.jsp";

        ArrayList<VenueBean> venues = new ArrayList<VenueBean>();

        switch (action) {
            case "customer":
                url = "/book.jsp";
                venues = venueDB.getActiveVenues();break;
            case "list":
                venues = venueDB.queryVenue();
                break;
            case "add":
                String venue_name = request.getParameter("venue_name");
                String venue_type = request.getParameter("venue_type");
                int venue_capacity = Integer.parseInt(request.getParameter("venue_capacity"));
                String venue_location = request.getParameter("venue_location");
                String venue_descrption = request.getParameter("venue_descrption");

                venueDB.addVenue(venue_name,venue_type,venue_capacity,venue_location,venue_descrption);
                break;
            case "edit":

                break;

        }

        request.setAttribute("venues", venues);


        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);


    }
}
