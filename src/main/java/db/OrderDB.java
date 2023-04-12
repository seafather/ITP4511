package db;

import bean.OrderInfo;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class OrderDB {
    private DB db;

    private Statement stmnt;
    PreparedStatement pStmnt;
    private Connection cnnt;

    public OrderDB(DB db) {
        this.db = db;

        CreateOrderTable();
    }

    private void CreateOrderTable() {
        try {
            cnnt = db.getConnection();
            stmnt = cnnt.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS bookings(" +
                    "    booking_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "    user_id INT," +
                    "    venue_id INT," +
                    "    DATE DATE," +
                    "    customer_name VARCHAR(20)," +
                    "    customer_phone VARCHAR(8)," +
                    "    customer_email VARCHAR(30)," +
                    "    start_time TIME," +
                    "    end_time TIME," +
                    "    state VARCHAR(20)," +
                    "    total_fee DECIMAL," +
                    "    order_create_date DATETIME," +
                    "   damage_description VARCHAR(100)"+
                    ")";

            stmnt.execute(sql);

            releaseDB();

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int insertRecord(int user_id, int venue_id, Date date, String CustomerName, String customerPhone, String customerEmail, String startTime, String endTime, String pending, double fee, Timestamp timestamp) {
        int booking_id = 0;
        try {
            cnnt = db.getConnection();


            String preQueryStatement = "INSERT INTO `bookings`( `user_id`, `venue_id`, `date`, `customer_name`, `customer_phone`, `customer_email`, `start_time`, `end_time`, `state`, `total_fee`, `order_create_date`) VALUES" +
                    " (?,?,?,?,?,?,?,?,?,?,?)";
            pStmnt = cnnt.prepareStatement(preQueryStatement, Statement.RETURN_GENERATED_KEYS);
            pStmnt.setInt(1, user_id);
            pStmnt.setInt(2, venue_id);
            pStmnt.setDate(3, date);
            pStmnt.setString(4, CustomerName);
            pStmnt.setString(5, customerPhone);
            pStmnt.setString(6, customerEmail);
            pStmnt.setString(7, startTime);
            pStmnt.setString(8, endTime);
            pStmnt.setString(9, pending);
            pStmnt.setDouble(10, fee);
            pStmnt.setTimestamp(11, timestamp);


            int rowCount = pStmnt.executeUpdate();
            if (rowCount < 1) {
                throw new SQLException();
            }

            ResultSet generatedKeys = pStmnt.getGeneratedKeys();
            if (generatedKeys.next()) {
                booking_id = generatedKeys.getInt(1);

            } else {
                throw new SQLException();
            }

            releaseDB();

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return booking_id;
    }

    public void insertRecord(String name, String phone, String email, int booking_id) {
        try {
            cnnt = db.getConnection();


            String preQueryStatement = "UPDATE bookings " +
                    "SET customer_name = ?, customer_phone = ?, customer_email = ?" +
                    "WHERE booking_id = ?";
            pStmnt = cnnt.prepareStatement(preQueryStatement, Statement.RETURN_GENERATED_KEYS);

            pStmnt.setString(1, name);
            pStmnt.setString(2, phone);
            pStmnt.setString(3, email);
            pStmnt.setInt(4, booking_id);


            int rowCount = pStmnt.executeUpdate();
            if (rowCount < 1) {
                throw new SQLException();
            }


            releaseDB();

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<OrderInfo> queryOrderByUserID(int userId) {
        ArrayList<OrderInfo> orders = new ArrayList<>();

        try {
            cnnt = db.getConnection();

            String preQueryStatement = "select * from bookings where user_id = ?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userId);

            ResultSet rs = pStmnt.executeQuery();

            while (rs.next()) {
                OrderInfo order = new OrderInfo();
                order.setOrder_ID(rs.getInt("booking_id"));
                order.setUser_id(userId);
                order.setVenue_id(rs.getInt("venue_id"));
                order.setDate(rs.getString("DATE"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setCustomerNumber(rs.getString("customer_phone"));
                order.setEmail(rs.getString("customer_email"));
                order.setStartTime(rs.getString("start_time"));
                order.setEndTime(rs.getString("end_time"));
                order.setState(rs.getString("state"));
                order.setTotalFee(rs.getInt("total_fee"));
                order.setCreateBytDatetime(rs.getString("order_create_date"));
                orders.add(order);

            }


        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return orders;
    }


    public ArrayList<OrderInfo> queryOrderByState(String state) {
        ArrayList<OrderInfo> orders = new ArrayList<>();

        try {
            cnnt = db.getConnection();

            String preQueryStatement = "select v.name, b.* from bookings b join venue v on b.venue_id= v.venue_id where state = ?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, state);


            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {

                OrderInfo order = new OrderInfo();
                order.setOrder_ID(rs.getInt("booking_id"));
                order.setUser_id(rs.getInt("user_id"));
                order.setVenue_id(rs.getInt("venue_id"));
                order.setVenue_name(rs.getString("name"));
                order.setDate(rs.getString("DATE"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setCustomerNumber(rs.getString("customer_phone"));
                order.setEmail(rs.getString("customer_email"));
                order.setStartTime(rs.getString("start_time"));
                order.setEndTime(rs.getString("end_time"));
                order.setState(rs.getString("state"));
                order.setTotalFee(rs.getInt("total_fee"));
                order.setCreateBytDatetime(rs.getString("order_create_date"));
                orders.add(order);
            }


        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return orders;

    }

    public ArrayList<OrderInfo> queryOrderByStateAndVenue(int venue_id,String state) {
        ArrayList<OrderInfo> orders = new ArrayList<>();

        try {
            cnnt = db.getConnection();

            String preQueryStatement = "select v.name, b.* from bookings b join venue v on b.venue_id= v.venue_id where state = ? and b.venue_id = ?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, state);
            pStmnt.setInt(2, venue_id);
            System.out.println(venue_id);
            System.out.println(state);


            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {

                OrderInfo order = new OrderInfo();
                order.setOrder_ID(rs.getInt("booking_id"));
                order.setUser_id(rs.getInt("user_id"));
                order.setVenue_id(rs.getInt("venue_id"));
                order.setVenue_name(rs.getString("name"));
                order.setDate(rs.getString("DATE"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setCustomerNumber(rs.getString("customer_phone"));
                order.setEmail(rs.getString("customer_email"));
                order.setStartTime(rs.getString("start_time"));
                order.setEndTime(rs.getString("end_time"));
                order.setState(rs.getString("state"));
                order.setTotalFee(rs.getInt("total_fee"));
                order.setCreateBytDatetime(rs.getString("order_create_date"));
                orders.add(order);
            }


        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return orders;

    }

    public boolean checkBookingOverlap(int booking_id) {
        try {
            cnnt = db.getConnection();

            String preQueryStatement = "SELECT * FROM bookings WHERE booking_id = ?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, booking_id);

            // Execute the query
            ResultSet rs = pStmnt.executeQuery();

            // Check if the input booking overlaps with any existing bookings
            if (rs.next()) {
                Timestamp start = rs.getTimestamp("start_time");
                Timestamp end = rs.getTimestamp("end_time");
                Date date = rs.getDate("date");

                String preQueryStatement2 = "SELECT * FROM bookings WHERE state = 'Approve' AND booking_id != ? and date = ?";
                pStmnt = cnnt.prepareStatement(preQueryStatement2);
                pStmnt.setInt(1, booking_id);
                pStmnt.setDate(2, date);

                // Execute the query
                ResultSet rs2 = pStmnt.executeQuery();

                // Check if the input booking overlaps with any existing bookings
                while (rs2.next()) {
                    Timestamp existingStart = rs2.getTimestamp("start_time");
                    Timestamp existingEnd = rs2.getTimestamp("end_time");


                    // Check for overlap
                    if ( start.before(existingEnd) && end.after(existingStart)) {
                        // There is an overlap
                        releaseDB();
                        return true;
                    }
                }
            }

            releaseDB();

        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public OrderInfo queryOrderByBookingID(int booking_id) {
        OrderInfo order = new OrderInfo();
        try {
            cnnt = db.getConnection();

            String preQueryStatement = "select * from bookings where booking_id = ?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, booking_id);

            ResultSet rs = pStmnt.executeQuery();

            while (rs.next()) {
                order.setOrder_ID(rs.getInt("booking_id"));
                order.setUser_id(rs.getInt("user_id"));
                order.setVenue_id(rs.getInt("venue_id"));
                order.setDate(rs.getString("DATE"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setCustomerNumber(rs.getString("customer_phone"));
                order.setEmail(rs.getString("customer_email"));
                order.setStartTime(rs.getString("start_time"));
                order.setEndTime(rs.getString("end_time"));
                order.setState(rs.getString("state"));
                order.setTotalFee(rs.getInt("total_fee"));
                order.setCreateBytDatetime(rs.getString("order_create_date"));
            }
            releaseDB();


        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return order;
    }

    public void releaseDB() throws SQLException {
        if (stmnt != null) {
            stmnt.close();
        } else {
            pStmnt.close();
        }

        cnnt.close();
    }


    public ArrayList<OrderInfo> queryOrder() {
        ArrayList<OrderInfo>  orders = new ArrayList<>();
        try {
            cnnt = db.getConnection();
            stmnt = cnnt.createStatement();

            String sql = "SELECT v.name, b.* " +
                    "FROM bookings b " +
                    "JOIN venue v ON b.venue_id = v.venue_id";

            ResultSet rs = stmnt.executeQuery(sql);

            while (rs.next()) {
                OrderInfo order = new OrderInfo();
                order.setOrder_ID(rs.getInt("booking_id"));
                order.setUser_id(rs.getInt("user_id"));
                order.setVenue_id(rs.getInt("venue_id"));
                order.setTotalFee(rs.getInt("total_fee"));
                order.setDate(rs.getString("DATE"));
                order.setEndTime(rs.getString("end_time"));
                order.setStartTime(rs.getString("start_time"));
                order.setState(rs.getString("state"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setCustomerNumber(rs.getString("customer_phone"));
                order.setEmail(rs.getString("customer_email"));
                order.setVenue_name(rs.getString("name"));

                orders.add(order);
            }

            releaseDB();

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return orders;
    }

    public ArrayList<OrderInfo> queryOrderByVenusID(int venue_id) {
        ArrayList<OrderInfo>  orders = new ArrayList<>();
        try {

            cnnt = db.getConnection();

            String preQueryStatement ="select v.name,b.* from bookings b join venue v on b.venue_id = v.venue_id where b.venue_id = ?";

            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, venue_id);

            ResultSet rs = pStmnt.executeQuery();

            while (rs.next()) {
                OrderInfo order = new OrderInfo();
                order.setOrder_ID(rs.getInt("booking_id"));
                order.setUser_id(rs.getInt("user_id"));
                order.setVenue_id(rs.getInt("venue_id"));
                order.setVenue_name(rs.getString("name"));
                order.setDate(rs.getString("date"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setCustomerNumber(rs.getString("customer_phone"));
                order.setEmail(rs.getString("customer_email"));
                order.setStartTime(rs.getString("start_time"));
                order.setEndTime(rs.getString("end_time"));
                order.setState(rs.getString("state"));
                order.setTotalFee(rs.getInt("total_fee"));
                order.setCreateBytDatetime(rs.getString("order_create_date"));

                orders.add(order);
            }

            releaseDB();

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return orders;
    }



    public void updateBookingState(int booking_id, String state) {
        try {
            cnnt = db.getConnection();
            PreparedStatement pstmt = cnnt.prepareStatement("UPDATE bookings SET state=? WHERE booking_id=?");

            pstmt.setString(1, state);
            pstmt.setInt(2, booking_id);

            pstmt.executeUpdate();

            releaseDB();

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int uploadDescriptionByBookingID(int booking_id, String description) {
        int user_id = -1; // Initialize user_id to -1, indicating that the user_id is not found
        try {
            cnnt = db.getConnection();
            pStmnt = cnnt.prepareStatement("UPDATE bookings SET damage_description = ? WHERE booking_id = ?");

            pStmnt.setString(1, description);
            pStmnt.setInt(2, booking_id);

            pStmnt.executeUpdate();

            // Query for the user_id of the booking
            PreparedStatement pstmt2 = cnnt.prepareStatement("SELECT user_id FROM bookings WHERE booking_id = ?");
            pstmt2.setInt(1, booking_id);

            ResultSet rs = pstmt2.executeQuery();
            if (rs.next()) {
                user_id = rs.getInt("user_id");
            }

            releaseDB();

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return user_id;
    }
}


