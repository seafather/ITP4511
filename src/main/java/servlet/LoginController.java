package servlet;

import bean.UserInfo;
import db.DB;
import db.UserDB;
import db.VenueDB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private UserDB userDB;

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        userDB = new UserDB(new DB(dbUrl,dbUser,dbPassword));
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        boolean result = false;
        HttpSession session = request.getSession();

        if (session.getAttribute("userInfo") != null) {
            result = true;

        }
        return result;
    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String targetURL = "";


        boolean isValid =userDB.isValidUser(email, password);
        if (isValid) {
            HttpSession session = request.getSession(true);
            UserInfo bean = userDB.getUserByEmail(email);

            session.setAttribute("userInfo", bean);

            switch (bean.getPosition()){
                case "customer":
                    targetURL = "jumpToBook.jsp";break;
                case "staff":
                    targetURL = "jumpToOrderProcessing.jsp";break;
                case "manager":
                    targetURL = "jumpToBook.jsp";break;
            }
        } else {
            targetURL = "loginAndRegister.jsp?error=true";
        }

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String targetURL = "loginAndRegister.jsp";
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute("userInfo");
            session.invalidate();
        }

        doLogin(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String action = request.getParameter("action");
        if (!isAuthenticated(request)
                && !("authenticate".equals(action))
                && !("customerRegister".equals(action))) {
            doLogin(request, response);
            return;
        }


        if ("authenticate".equals(action)) {
            doAuthenticate(request, response);
        } else if ("logout".equals(action)) {
            doLogout(request, response);
        }else if("customerRegister".equals(action)){
            System.out.println("hello");
            customerRegisterEvent(request,response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }



    }

    private void customerRegisterEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("passwordAgain");

        if (!password2.equals(password)){
            String error = "register";
            request.setAttribute("error","Password incorrect twice");
            request.getRequestDispatcher("loginAndRegister.jsp").forward(request, response);
        }
        if (userDB.isExistingUser(email)){
            String error = "register";
            request.setAttribute("error","Email has been registered ");
            request.getRequestDispatcher("loginAndRegister.jsp").forward(request, response);
        }

        userDB.createUser(name,email,password,"customer");
        response.sendRedirect(request.getContextPath() +"/loginAndRegister.jsp");





    }
}
