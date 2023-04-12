package servlet;

import bean.Guest;
import bean.OrderInfo;
import bean.UserInfo;
import bean.VenueBean;
import db.DB;
import db.GuestDB;
import db.OrderDB;
import db.VenueDB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "OrderController", urlPatterns = "/OrderController")
public class OrderController extends HttpServlet {

    private OrderDB orderDB;
    private GuestDB guestDB;
    private VenueDB venueDB;

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        orderDB = new OrderDB(new DB(dbUrl,dbUser,dbPassword));
        guestDB = new GuestDB(new DB(dbUrl,dbUser,dbPassword));
        venueDB = new VenueDB(new DB(dbUrl,dbUser,dbPassword));
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String action = request.getParameter("action");

        HttpSession session = request.getSession(false);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");


        if("list".equals(action)){

            ArrayList<OrderInfo> orders = orderDB.queryOrderByUserID(userInfo.getUserId());

            request.setAttribute("orders", orders);

            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/customerOrder.jsp");
            rd.forward(request, response);

        } else if ("preview".equals(action)) {
            int bookingID = Integer.parseInt(request.getParameter("booking_id"));
            int venue_id =  Integer.parseInt(request.getParameter("venue_id"));


            OrderInfo orderInfo = orderDB.queryOrderByBookingID(bookingID);
            ArrayList<Guest> guests = guestDB.getGuestsByBookingId(bookingID);
            VenueBean venueBean = venueDB.getVenueByID(venue_id);


            request.setAttribute("order",orderInfo);
            request.setAttribute("guests",guests);
            request.setAttribute("venueBean",venueBean);


            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/orderPreview.jsp");
            rd.forward(request, response);


        } else if ("update".equals(action)) {
            int bookingID = Integer.parseInt(request.getParameter("booking_id"));

            OrderInfo orderInfo = orderDB.queryOrderByBookingID(bookingID);
            ArrayList<Guest> guests = guestDB.getGuestsByBookingId(bookingID);


            request.setAttribute("order",orderInfo);
            request.setAttribute("guests",guests);


            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/orderUpdate.jsp");
            rd.forward(request, response);
        } else if ("processing".equals(action)) {
            int bookingID = Integer.parseInt(request.getParameter("booking_id"));
            int venue_id =  Integer.parseInt(request.getParameter("venue_id"));


            OrderInfo orderInfo = orderDB.queryOrderByBookingID(bookingID);
            ArrayList<Guest> guests = guestDB.getGuestsByBookingId(bookingID);
            VenueBean venueBean = venueDB.getVenueByID(venue_id);


            request.setAttribute("order",orderInfo);
            request.setAttribute("guests",guests);
            request.setAttribute("venueBean",venueBean);


            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/orderPreview.jsp?state=processing");
            rd.forward(request, response);

        }else if("check-in".equals(action)){

            ArrayList<OrderInfo> orders = orderDB.queryOrderByState("Approve");
            ArrayList<VenueBean> venues  = venueDB.queryVenue();


            request.setAttribute("venues", venues);
            request.setAttribute("orders",orders);

            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/check-in.jsp");
            rd.forward(request, response);
        }else if("check-out".equals(action)){

            ArrayList<OrderInfo> orders = orderDB.queryOrderByState("check-in");
            ArrayList<VenueBean> venues  = venueDB.queryVenue();


            request.setAttribute("venues", venues);
            request.setAttribute("orders",orders);

            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/check-out.jsp");
            rd.forward(request, response);
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
