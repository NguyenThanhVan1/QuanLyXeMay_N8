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
        Statement stmt = null;

        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();

            String sql = "INSERT INTO KHACHHANG VALUES (" +
                    "'" + kh.getMakh() + "'," +
                    "N'" + kh.getHoten() + "'," +
                    "'" + kh.getSdt() + "'," +
                    "N'" + kh.getDiachi() + "'," +
                    "'" + kh.getTendangnhap() + "'," +
                    "'" + kh.getMatkhau() + "')";
            System.out.println(sql);
            stmt.executeUpdate(sql);

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
        Statement stmt = null;

        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();

            String sql = "UPDATE KHACHHANG SET " +
                    "MAKH='" + kh.getMakh() + "'," +
                    "HOTEN=N'" + kh.getHoten() + "'," +
                    "SDT='" + kh.getSdt() + "'," +
                    "DIACHI=N'" + kh.getDiachi() + "'," +
                    "TENDANGNHAP='" + kh.getTendangnhap() + "'," +
                    "MATKHAU='" + kh.getMatkhau() + "' " +
                    "WHERE MAKH='" + kh.getMakh() + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);

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
        Statement stmt = null;

        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();

            String sql = "DELETE FROM KHACHHANG WHERE MAKH='" + makh + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);

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
}
