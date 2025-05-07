package DAO;

import DTO.KhachHangDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhachHangDAO {

    public ArrayList<KhachHangDTO> list() {
        ArrayList<KhachHangDTO> dskh = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();
            String sql = "SELECT * FROM KHACHHANG";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String makh = rs.getString("MAKH");
                String hoten = rs.getString("HOTEN");
                String sdt = rs.getString("SDT");
                String diachi = rs.getString("DIACHI");
                String tendangnhap = rs.getString("TENDANGNHAP");
                String matkhau = rs.getString("MATKHAU");

                KhachHangDTO kh = new KhachHangDTO(makh, hoten, sdt, diachi, tendangnhap, matkhau);
                dskh.add(kh);
            }
        } catch (SQLException e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Database.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dskh;
    }

    public void add(KhachHangDTO kh) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Database.getConnection();
            String sql = "INSERT INTO KHACHHANG (MAKH, HOTEN, SDT, DIACHI, TENDANGNHAP, MATKHAU) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, kh.getMakh());
            stmt.setString(2, kh.getHoten());
            stmt.setString(3, kh.getSdt());
            stmt.setString(4, kh.getDiachi());
            stmt.setString(5, kh.getTendangnhap());
            stmt.setString(6, kh.getMatkhau());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                Database.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void set(KhachHangDTO kh) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Database.getConnection();
            String sql = "UPDATE KHACHHANG SET HOTEN=?, SDT=?, DIACHI=?, TENDANGNHAP=?, MATKHAU=? WHERE MAKH=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, kh.getHoten());
            stmt.setString(2, kh.getSdt());
            stmt.setString(3, kh.getDiachi());
            stmt.setString(4, kh.getTendangnhap());
            stmt.setString(5, kh.getMatkhau());
            stmt.setString(6, kh.getMakh());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                Database.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(String makh) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Database.getConnection();
            String sql = "DELETE FROM KHACHHANG WHERE MAKH=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, makh);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                Database.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static KhachHangDTO checkLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();
            String sql = "SELECT * FROM KHACHHANG WHERE TENDANGNHAP = ? AND MATKHAU = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password); // Nếu có mã hóa thì mã hóa trước khi truyền vào đây

            rs = stmt.executeQuery();
            if (rs.next()) {
                KhachHangDTO khachHang = new KhachHangDTO();
                khachHang.setMakh(rs.getString("MAKH"));
                khachHang.setHoten(rs.getString("HOTEN"));
                khachHang.setSdt(rs.getString("SDT"));
                khachHang.setDiachi(rs.getString("DIACHI"));
                khachHang.setTendangnhap(rs.getString("TENDANGNHAP"));
                khachHang.setMatkhau(rs.getString("MATKHAU"));
                return khachHang;
            }
        } catch (SQLException e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
