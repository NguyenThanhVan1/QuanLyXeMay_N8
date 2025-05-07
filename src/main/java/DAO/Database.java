package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection getConnection() throws SQLException {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/qlchxm";
            String username = "root";
            String password = "";
            c = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database. Check URL, username, and password.");
            throw e;
        }
        return c;
    }

    public static void closeConnection(Connection c) {
        try {
            if (c != null)
                c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}