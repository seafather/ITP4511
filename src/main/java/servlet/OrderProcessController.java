package servlet;

import bean.OrderInfo;
import bean.VenueBean;
import db.DB;
import db.OrderDB;
import db.UserDB;
import db.VenueDB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "OrderProcessController", urlPatterns = "/OrderProcess")
public class OrderProcessController extends HttpServlet {

    private OrderDB orderDB;
    private VenueDB venueDB;
    private UserDB userDB;

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        orderDB = new OrderDB(new DB(dbUrl, dbUser, dbPassword));
        venueDB = new VenueDB(new DB(dbUrl, dbUser, dbPassword));
        userDB = new UserDB(new DB(dbUrl, dbUser, dbPassword));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        ArrayList<OrderInfo> pendingOrders = orderDB.queryOrderByState("Pending");
        ArrayList<OrderInfo> Orders = new ArrayList<>();
        ArrayList<VenueBean> venues = new ArrayList<>();


        String url = "/orderProcessing.jsp";

        if ("list".equals(action)) {
            Orders = orderDB.queryOrder();
            venues = venueDB.queryVenue();

            request.setAttribute("Orders", Orders);
            request.setAttribute("venues", venues);


        } else if ("view".equals(action)) {
            int booking = Integer.parseInt(request.getParameter("booking_id"));

            OrderInfo orderInfo = orderDB.queryOrderByBookingID(booking);


        } else if ("searchByVenue".equals(action)) {

            int venue_id = Integer.parseInt(request.getParameter("venue_id"));
            if (request.getParameter("page")!= null && request.getParameter("page").equals("check-in")){

                url = "/check-in.jsp";
                venues = venueDB.queryVenue();
                Orders = orderDB.queryOrderByStateAndVenue(venue_id,"Approve");


            }else if(request.getParameter("page")!= null && request.getParameter("page").equals("check-out")){

                url = "/check-out.jsp";
                venues = venueDB.queryVenue();
                Orders = orderDB.queryOrderByStateAndVenue(venue_id,"Check-in");
            }
            else{
                venues = venueDB.queryVenue();
                Orders = orderDB.queryOrderByVenusID(venue_id);
            }

            System.out.println();
            request.setAttribute("venues", venues);
            request.setAttribute("orders", Orders);


        } else if ("reject".equals(action)) {
            System.out.println(request.getParameter("booking_id"));
            int booking = Integer.parseInt(request.getParameter("booking_id"));
            orderDB.updateBookingState(booking, "Reject");

            Orders = orderDB.queryOrder();
            venues = venueDB.queryVenue();

        } else if ("approve".equals(action)) {
            int booking = Integer.parseInt(request.getParameter("booking_id"));

            if(orderDB.checkBookingOverlap(booking)){
                url = "/demo_war/"+request.getParameter("url")+"&error=true";

                System.out.println(url);
                response.sendRedirect(url);
                return;
            }else{
                orderDB.updateBookingState(booking, "Approve");
            }

            Orders = orderDB.queryOrder();
            venues = venueDB.queryVenue();
            pendingOrders = orderDB.queryOrderByState("Pending");



        } else if ("check-in".equals(action)) {

            int booking = Integer.parseInt(request.getParameter("booking_id"));
            orderDB.updateBookingState(booking, "Check-in");

            Orders = orderDB.queryOrder();
            venues = venueDB.queryVenue();
        } else if ("check-out".equals(action)) {

            int booking = Integer.parseInt(request.getParameter("booking_id"));
            String damage_descrption = request.getParameter("damage_descrption");

            if(!damage_descrption.equals("")){
                int user_id = orderDB.uploadDescriptionByBookingID(booking,damage_descrption);
                userDB.incrementDamagedTimes(user_id);
            }

            orderDB.updateBookingState(booking, "Finish");



            Orders = orderDB.queryOrder();
            venues = venueDB.queryVenue();
        }


        request.setAttribute("pendingOrders", pendingOrders);
        request.setAttribute("Orders", Orders);
        request.setAttribute("venues", venues);



        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }


}
