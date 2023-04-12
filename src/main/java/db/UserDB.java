package db;

import bean.UserInfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDB {
    private DB db;

    private Statement stmnt ;
    PreparedStatement pStmnt;
    private Connection cnnt ;

    public UserDB(DB db) {
        this.db = db;

        CreateUserInfoTable();
    }

    private void CreateUserInfoTable() {
        try{
            cnnt = db.getConnection();
            stmnt = cnnt.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS users("
                    + "user_id int(11) PRIMARY KEY AUTO_INCREMENT,"
                    + "position VARCHAR(10) NOT NULL,"
                    + "name VARCHAR(10) NOT NULL,"
                    + "email varchar(50) not null,"
                    + "password varchar(50) not null,"
                    + "Damaged_times int(11)"
                    + " )";

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

    public boolean isValidUser(String email ,String password) {
        boolean isSuccess = false;
        try {
            cnnt = db.getConnection();


            String preQueryStatement = "select * from users where email = ? and password = ? ";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, email);
            pStmnt.setString(2, password);

            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                isSuccess = true;
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
        return isSuccess;
    }

    public boolean isExistingUser(String email){
        boolean isSuccess = false;
        try {
            cnnt = db.getConnection();


            String preQueryStatement = "select * from users where email = ?";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, email);

            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                isSuccess = true;
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
        return isSuccess;
    }

    public void createUser(String name, String email, String password, String position) {
        try {
            cnnt = db.getConnection();

            String query = "INSERT INTO `users`(`position`, `name`, `email`, `password`) VALUES (?, ?, ?, ?)";
            pStmnt = cnnt.prepareStatement(query);
            pStmnt.setString(1, position);
            pStmnt.setString(2, name);
            pStmnt.setString(3, email);
            pStmnt.setString(4, password);

            pStmnt.executeUpdate();

            releaseDB();

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            releaseDB();
        }
    }




    public UserInfo getUserByEmail(String email) {
        UserInfo userInfo = new UserInfo();

        try {
            cnnt = db.getConnection();


            String preQueryStatement = "select * from users where email = ? ";
            pStmnt = cnnt.prepareStatement(preQueryStatement);
            pStmnt.setString(1, email);

            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                userInfo.setEmail(rs.getString("email"));
                userInfo.setName(rs.getString("name"));
                userInfo.setPosition(rs.getString("position"));
                userInfo.setUserId(rs.getInt("user_id"));

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
        return userInfo;
    }

    public void incrementDamagedTimes(int user_id) {
        try {
            cnnt = db.getConnection();
            PreparedStatement pstmt = cnnt.prepareStatement("UPDATE users SET Damaged_times = Damaged_times + 1 WHERE user_id=?");
            pstmt.setInt(1, user_id);
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

    public void releaseDB() {
        try{
            if (stmnt != null){
                stmnt.close();
            }else{
                pStmnt.close();
            }

            cnnt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}

