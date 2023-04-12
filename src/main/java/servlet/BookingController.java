package servlet;

import bean.OrderInfo;
import bean.UserInfo;
import db.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "BookingController", value = "/booking")
public class BookingController extends HttpServlet {
    private OrderDB orderDB;
    private VenueDB venueDB;
    private GuestDB guestDB;


    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        orderDB = new OrderDB(new DB(dbUrl, dbUser, dbPassword));
        venueDB = new VenueDB(new DB(dbUrl, dbUser, dbPassword));
        guestDB = new GuestDB(new DB(dbUrl, dbUser, dbPassword));
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        String arrayString = request.getParameter("query");
//
//
//        String[] words = arrayString.split(",");
//        List<String> arrayList = new ArrayList<>(Arrays.asList(words));
//
//        String redirectURL = "booking.jsp?param1=" +
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        int user_id = userInfo.getUserId();

        if ("update".equals(request.getParameter("action"))) {
            System.out.println("hello");
            String name = request.getParameter("customer_name" ) ;
            String customerPhone = request.getParameter("customer_phone" );
            String customerEmail = request.getParameter("customer_email" );
            int booking_ID = Integer.parseInt(request.getParameter("booking_id"));

            ArrayList<String> guestsName = new ArrayList<>();
            ArrayList<String> guestsEmail = new ArrayList<>();

            for (int i = 0; i <= 10; i++) {
                if (request.getParameter("guest_name" + i) != null) {
                    guestsName.add(request.getParameter("guest_name" + i));
                    guestsEmail.add(request.getParameter("guest_email" + i));
                }
            }


            orderDB.insertRecord(name,customerPhone,customerEmail,booking_ID);
            guestDB.updateGuests(booking_ID, guestsName, guestsEmail);



        } else if ("booking".equals(request.getParameter("action"))) {

            int j = 0;
            while (request.getParameter("customer_first_name" + j) != null) {
                System.out.println("j = " + j);


                String name = request.getParameter("customer_first_name" + j) + " " + request.getParameter("customer_last_name" + j);
                String customerPhone = request.getParameter("customer_phone" + j);
                String customerEmail = request.getParameter("customer_email" + j);

                String startTime = (request.getParameter("first_time" + j));
                String endTime = (request.getParameter("last_time" + j));

                java.sql.Date sqlDate;
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);
                    Date date = dateFormat.parse(request.getParameter("date" + j));
                    sqlDate = new java.sql.Date(date.getTime());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                Date now = new Date();
                // Convert the time to a java.sql.Timestamp object
                Timestamp timestamp = new Timestamp(now.getTime());


                int hours = Integer.parseInt(endTime.substring(0, 2)) - Integer.parseInt(startTime.substring(0, 2));

                int venue_id = venueDB.getVenueIDByName(request.getParameter("venue"+j));

                double fee = hours * venueDB.getFeeByID(venue_id);


                ArrayList<String> guestsName = new ArrayList<>();
                ArrayList<String> guestsEmail = new ArrayList<>();

//            while (request.getParameter("guest_name" + String.valueOf(i)) != null && request.getParameter("guest_email" + String.valueOf(i)) != null) {
//                guestsName.add(request.getParameter("guest_name" + String.valueOf(i)));
//                guestsEmail.add(request.getParameter("guest_email" + String.valueOf(i)));
//                i++;
//            }

                for (int i = j * 10; i <= j * 10 + 10; i++) {
                    if (request.getParameter("guest_name" + i) != null) {
                        guestsName.add(request.getParameter("guest_name" + i));
                        guestsEmail.add(request.getParameter("guest_email" + i));
                    }
                }


                int booking_id = orderDB.insertRecord(user_id, venue_id, sqlDate, name, customerPhone, customerEmail, startTime, endTime, "Pending", fee, timestamp);
                guestDB.insertGuests(booking_id, guestsName, guestsEmail);

                j++;
            }
        }


        response.sendRedirect("OrderController?action=list");
    }
}
