package db;

import bean.Guest;
import bean.UserInfo;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class GuestDB {
    private DB db;

    private Statement stmnt ;
    PreparedStatement pStmnt;
    private Connection cnnt ;

    public GuestDB(DB db) {
        this.db = db;

        CreateGuestTable();
    }

    private void CreateGuestTable() {
        try{
            cnnt = db.getConnection();
            stmnt = cnnt.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS guest_lists (" +
                    "  guest_list_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "  guest_name VARCHAR(20)," +
                    "  guest_email VARCHAR(50)" +
                    ");";

            stmnt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS booking_guests (" +
                    "  booking_id INT," +
                    "  guest_list_id INT," +
                    "  PRIMARY KEY (booking_id, guest_list_id)" +
                    ");";

            stmnt.execute(sql);

            releaseDB();

        }catch (SQLException ex){
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();

            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    public boolean insertGuests(int bookingId, ArrayList<String> guestNames, ArrayList<String> guestEmails) {
        int guestListId = -1;
        ResultSet rs = null;

        boolean isSuccess = false;
        try {
            cnnt = db.getConnection();
            cnnt.setAutoCommit(false);

            // 插入 guest_lists 表中的记录，并获取自增主键值
            String insertGuestListSQL = "INSERT INTO guest_lists (guest_name, guest_email) VALUES (?, ?)";
            pStmnt = cnnt.prepareStatement(insertGuestListSQL, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < guestNames.size(); i++) {
                pStmnt.setString(1, guestNames.get(i));
                pStmnt.setString(2, guestEmails.get(i));
                pStmnt.addBatch();
            }
            pStmnt.executeBatch();
            rs = pStmnt.getGeneratedKeys();
            if (rs.next()) {
                guestListId = rs.getInt(1);
            }

            // 插入 booking_guests 表中的记录
            String insertBookingGuestSQL = "INSERT INTO booking_guests (booking_id, guest_list_id) VALUES (?, ?)";
            pStmnt = cnnt.prepareStatement(insertBookingGuestSQL);
            for (int i = 0; i < guestNames.size(); i++) {
                pStmnt.setInt(1, bookingId);
                pStmnt.setInt(2, guestListId + i);
                pStmnt.addBatch();
            }
            pStmnt.executeBatch();
            cnnt.commit();

            isSuccess = true;

        } catch (SQLException ex) {
            if (cnnt != null) {
                try {
                    cnnt.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            // 在這裡進行處理異常的操作
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (cnnt != null) {
                try {
                    cnnt.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    releaseDB();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return isSuccess;
    }

    public void updateGuests(int bookingId, ArrayList<String> guestNames, ArrayList<String> guestEmails) {
        try {
            cnnt = db.getConnection();
            cnnt.setAutoCommit(false);

            // 獲取booking對應的guest_list_id列表
            String selectGuestListIdSQL = "SELECT guest_list_id FROM booking_guests WHERE booking_id = ?";
            pStmnt = cnnt.prepareStatement(selectGuestListIdSQL);
            pStmnt.setInt(1, bookingId);
            ResultSet rs = pStmnt.executeQuery();

            ArrayList<Integer> guestListIds = new ArrayList<>();
            while (rs.next()) {
                guestListIds.add(rs.getInt("guest_list_id"));
            }

            // 更新guest_lists表
            String updateGuestListSQL = "UPDATE guest_lists SET guest_name = ?, guest_email = ? WHERE guest_list_id = ?";
            pStmnt = cnnt.prepareStatement(updateGuestListSQL);
            for (int i = 0; i < guestListIds.size(); i++) {
                pStmnt.setString(1, guestNames.get(i));
                pStmnt.setString(2, guestEmails.get(i));
                pStmnt.setInt(3, guestListIds.get(i));
                pStmnt.addBatch();
            }
            pStmnt.executeBatch();

            cnnt.commit();

        } catch (SQLException ex) {
            if (cnnt != null) {
                try {
                    cnnt.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            // 在這裡進行處理異常的操作
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (cnnt != null) {
                try {
                    cnnt.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    releaseDB();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public ArrayList<Guest> getGuestsByBookingId(int bookingId) {
        ArrayList<Guest> guests = new ArrayList<>();

        try {
            cnnt = db.getConnection();

            String query = "SELECT gl.guest_name, gl.guest_email FROM guest_lists gl JOIN booking_guests bg ON gl.guest_list_id = bg.guest_list_id WHERE bg.booking_id = ?";
            pStmnt = cnnt.prepareStatement(query);
            pStmnt.setInt(1, bookingId);

            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestEmail(rs.getString("guest_email"));
                guest.setGuestName(rs.getString("guest_name"));
                guest.setGuestListId(bookingId);

                guests.add(guest);
            }

            releaseDB();

        } catch (SQLException ex) {
            if (cnnt != null) {
                try {
                    cnnt.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                releaseDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return guests;
    }

    public void releaseDB() throws SQLException{
        if (stmnt != null){
            stmnt.close();
        }else{
            pStmnt.close();
        }

        cnnt.close();
    }

}
