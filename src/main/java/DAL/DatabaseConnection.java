package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        Connection c = null;
        try {
            // Load SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Kết nối đến SQL Server
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLCHXM;" +
                    "encrypt=true;" +
                    "trustServerCertificate=true";
            String username = "sa"; // tài khoản SQL Server của bạn
            String password = "123"; // mật khẩu SQL Server

            c = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy driver SQL Server", e);
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
