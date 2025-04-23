package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.Interface.UsersDAOInterface;
import DTO.UsersDTO;

public class UsersDAO implements UsersDAOInterface<UsersDTO, Integer>{
    private Connection conn;

    public UsersDAO() {
       try {
            conn = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean create(UsersDTO entity, Connection conn) {
        String sql = "INSERT INTO khachhang (HOTEN, SDT, DIACHI, TENDANGNHAP, MATKHAU, QUYEN) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getRole());
            ps.setString(3, entity.getAddress());
            ps.setString(4, entity.getUserName());
            ps.setString(5, entity.getPassword());
            ps.setString(6, entity.getRole());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm người dùng: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Integer id, Connection conn) {
        try {
            String sql = "DELETE FROM khachhang WHERE MAKH = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa người dùng: " + e.getMessage(), e);
        }
    }

    @Override
    public List<UsersDTO> getAll(Connection conn) {
        try {
            String sql = "SELECT * FROM khachhang";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<UsersDTO> usersList = new ArrayList<>();
            while (rs.next()) {
                UsersDTO user = new UsersDTO(rs.getInt("MAKH"), rs.getString("HOTEN"), rs.getString("SDT"),
                        rs.getString("DIACHI"), rs.getString("TENDANGNHAP"), rs.getString("MATKHAU"),
                        rs.getString("QUYEN"));
                usersList.add(user);
            }
            return usersList;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách người dùng: " + e.getMessage(), e);
        } 
    }

    @Override
    public UsersDTO getById(Integer id, Connection conn) {
        try {
            String sql = "SELECT * FROM khachhang WHERE MAKH = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UsersDTO(rs.getInt("MAKH"), rs.getString("HOTEN"), rs.getString("SDT"),
                        rs.getString("DIACHI"), rs.getString("TENDANGNHAP"), rs.getString("MATKHAU"),
                        rs.getString("QUYEN"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy thông tin người dùng: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean update(UsersDTO entity, Connection conn) {
        try {
            String sql = "UPDATE khachhang SET HOTEN = ?, SDT = ?, DIACHI = ?, TENDANGNHAP = ?, MATKHAU = ?, QUYEN = ? WHERE MAKH = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getRole());
            ps.setString(3, entity.getAddress());
            ps.setString(4, entity.getUserName());
            ps.setString(5, entity.getPassword());
            ps.setString(6, entity.getRole());
            ps.setInt(7, entity.getId());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật người dùng: " + e.getMessage(), e);
        }
    }
    
}
