package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DTO.KhachHangDTO;

public class KhachHangDAO {

    // Hàm mã hóa mật khẩu
    // private static String hashPassword(String password) {
    // try {
    // MessageDigest md = MessageDigest.getInstance("SHA-256");
    // byte[] hashedBytes = md.digest(password.getBytes());
    // StringBuilder hexString = new StringBuilder();
    // for (byte b : hashedBytes) {
    // hexString.append(String.format("%02x", b));
    // }
    // return hexString.toString();
    // } catch (NoSuchAlgorithmException e) {
    // e.printStackTrace();
    // return null;
    // }
    // }

    // Phương thức checkLogin trả về KhachHangDTO thay vì boolean
    public static KhachHangDTO checkLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection(); // Đảm bảo lớp này có sẵn
            String hashedPassword = password; // Mã hóa mật khẩu

            String sql = "SELECT * FROM KHACHHANG WHERE TENDANGNHAP = ? AND MATKHAU = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            rs = stmt.executeQuery();
            if (rs.next()) {
                // Nếu có kết quả, tạo và trả về đối tượng KhachHangDTO
                KhachHangDTO khachHang = new KhachHangDTO();
                khachHang.setMaKhachHang(rs.getString("MAKH"));
                khachHang.setHoTen(rs.getString("HOTEN"));
                khachHang.setDiaChi(rs.getString("SDT"));
                khachHang.setSoDienThoai(rs.getString("DIACHI"));
                khachHang.setTenDangNhap(rs.getString("TENDANGNHAP"));
                khachHang.setMatKhau(rs.getString("MATKHAU"));
                return khachHang; // Trả về đối tượng KhachHangDTO
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return null; // Trả về null nếu không tìm thấy tài khoản hợp lệ
    }
}
