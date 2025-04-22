package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public boolean create(UsersDTO entity) {
        
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        
        return false;
    }

    @Override
    public List<UsersDTO> getAll() {
        
        return null;
    }

    @Override
    public UsersDTO getById(Integer id) {
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
    public boolean update(UsersDTO entity) {
        
        return false;
    }
    
}
