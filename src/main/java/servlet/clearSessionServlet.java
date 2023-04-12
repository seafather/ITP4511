package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "clearSessionServlet", value = "/clearSessionServlet")
public class clearSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Invalidate the session to clear all session attributes
        session.invalidate();

        // Redirect the user to a new page or back to the home page
        response.sendRedirect("index.jsp");
    }
}
