package sample;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by blackhatt on 09/04/2017.
 */
public class DBConn {

    private static final String URL = "jdbc:mysql://sql11.freemysqlhosting.net:3306/";
    private static final String DB_NAME = "sql11167728";
    private static final String USER = "sql11167728";
    private static final String PASS = "mCZ7BHUwjh";


    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    DBConn.URL + DBConn.DB_NAME,
                    DBConn.USER,
                    DBConn.PASS);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getDbName() {
        return DB_NAME;
    }
}

