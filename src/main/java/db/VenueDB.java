package db;

import bean.VenueBean;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class VenueDB {
    private DB db;

    private Statement stmnt;
    PreparedStatement pStmnt;
    private Connection cnnt;

    public VenueDB(DB db) {
        this.db = db;

        CreateVenueTable();
    }

    private void CreateVenueTable() {
        try {
            cnnt = db.getConnection();
            stmnt = cnnt.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS venue("
                    + "venue_id  int PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(20),"
                    + "type VARCHAR(20),"
                    + "max_people INT,"
                    + "location VARCHAR(50),"
                    + "description TEXT,"
                    + "booking_fee int,"
                    + "active BOOLEAN NOT NULL DEFAULT TRUE)";

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


    public ArrayList<VenueBean> queryVenue() {
        ArrayList<VenueBean> venues = new ArrayList<>();

        try {
            cnnt = db.getConnection();
            stmnt = cnnt.createStatement();

            String sql = "SELECT * FROM venue";
            ResultSet rs = stmnt.executeQuery(sql);

            while (rs.next()) {
                VenueBean venue = new VenueBean();

                venue.setVenue_id(rs.getInt("venue_id"));
                venue.setName(rs.getString("name"));
                venue.setType(rs.getString("type"));
                venue.setMax_people(rs.getInt("max_people"));
                venue.setLocation(rs.getString("location"));
                venue.setDescription(rs.getString("description"));
                venue.setHoursFee(rs.getDouble("booking_fee"));
                venue.setActive(rs.getBoolean("active"));

                venues.add(venue);
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

        return venues;
    }

    public ArrayList<VenueBean> getActiveVenues() {
        ArrayList<VenueBean> venues = new ArrayList<>();
        try {
            cnnt = db.getConnection();
            String sql = "SELECT * FROM venue WHERE active = true";
            PreparedStatement pstmt = cnnt.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                VenueBean venue = new VenueBean();
                venue.setVenue_id(rs.getInt("venue_id"));
                venue.setName(rs.getString("name"));
                venue.setType(rs.getString("type"));
                venue.setMax_people(rs.getInt("max_people"));
                venue.setLocation(rs.getString("location"));
                venue.setDescription(rs.getString("description"));
                venue.setHoursFee(rs.getInt("booking_fee"));
                venue.setActive(rs.getBoolean("active"));
                venues.add(venue);
            }

            releaseDB();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return venues;
    }


    public int getVenueIDByName(String venue) {
        int venueID = 0;

        try {
            cnnt = db.getConnection();

            String preQueryStatement = "select * from venue where name = ? ";

            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, venue);


            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                venueID = rs.getInt("venue_id");
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
        return venueID;

    }

    public VenueBean getVenueByID(int venue_id) {
        VenueBean bean = new VenueBean();
        try {
            cnnt = db.getConnection();

            String preQueryStatement = "select * from venue where venue_id = ? ";

            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, venue_id);


            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                bean.setVenue_id(rs.getInt("venue_id"));
                bean.setMax_people(rs.getInt("max_people"));
                bean.setHoursFee(rs.getInt("booking_fee"));

                bean.setName(rs.getString("name"));
                bean.setType(rs.getString("type"));
                bean.setLocation(rs.getString("location"));
                bean.setDescription(rs.getString("description"));
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
        return bean;

    }

    public void deleteVenueById(int venue_id) {
        try {
            cnnt = db.getConnection();
            PreparedStatement pstmt = cnnt.prepareStatement("DELETE FROM venue WHERE venue_id = ?");
            pstmt.setInt(1, venue_id);
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

    public int getFeeByID(int venue_id) {
        int hoursFee = 0;

        try {
            cnnt = db.getConnection();

            String preQueryStatement = "select * from venue where venue_id = ? ";

            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, venue_id);

            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                hoursFee = rs.getInt("booking_fee");
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
        return hoursFee;

    }

    public void addVenue(String name, String type, int capacity, String location, String description,double fee) {
        try {
            cnnt = db.getConnection();
            PreparedStatement pstmt = cnnt.prepareStatement("INSERT INTO venue(name, type, max_people, location, description,booking_fee) VALUES(?,?,?,?,?,?)");

            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, capacity);
            pstmt.setString(4, location);
            pstmt.setString(5, description);
            pstmt.setDouble(6,fee);

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

    public void updateRecord(int venue_id, String venue_name, String venue_type, int venue_capacity, Double venue_fee, String venue_location, String venue_descrption) {
        try {
            cnnt = db.getConnection();

            String preQueryStatement = "UPDATE venue SET name=?, type=?, max_people=?, location=?, description=?, " +
                    "booking_fee=? WHERE venue_id=?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);

            pStmnt.setString(1, venue_name);
            pStmnt.setString(2, venue_type);
            pStmnt.setInt(3, venue_capacity);
            pStmnt.setString(4, venue_location);
            pStmnt.setString(5, venue_descrption);
            pStmnt.setDouble(6,venue_fee);
            pStmnt.setInt(7, venue_id);

            pStmnt.executeUpdate();

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

    public void toggleVenueActive(int venue_id) {
        try {
            cnnt = db.getConnection();

            String preQueryStatement = "UPDATE venue SET active = NOT active WHERE venue_id = ?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);

            pStmnt.setInt(1, venue_id);

            pStmnt.executeUpdate();

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


    public void releaseDB() throws SQLException {
        if (stmnt != null) {
            stmnt.close();
        } else {
            pStmnt.close();
        }

        cnnt.close();
    }



}
