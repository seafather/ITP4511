package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB {
    private  String dburl,dbUser,dbPasword;

    public DB(String dburl, String dbUser, String dbPasword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPasword = dbPasword;
    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return DriverManager.getConnection(dburl, dbUser, dbPasword);
    }


}
