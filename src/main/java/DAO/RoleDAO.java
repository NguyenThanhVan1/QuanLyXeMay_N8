
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;

// public class RoleDAO {

//     private Connection connection;

//     // Constructor để khởi tạo kết nối
//     public RoleDAO(Connection connection) {
//         this.connection = connection;
//     }

//     // Phương thức kiểm tra thông tin đăng nhập
//     public boolean authenticateRole(String username, String password) {
//         String query = "SELECT * FROM roles WHERE username = ? AND password = ?";
//         try (PreparedStatement ps = connection.prepareStatement(query)) {
//             ps.setString(1, username);
//             ps.setString(2, password);
//             ResultSet rs = ps.executeQuery();

//             // Nếu tìm thấy vai trò trong cơ sở dữ liệu
//             if (rs.next()) {
//                 return true; // Đăng nhập thành công
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return false; // Đăng nhập thất bại
//     }
// }
