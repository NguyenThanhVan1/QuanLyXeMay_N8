package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DAO.Interface.ProductsDAOInterface;
import DTO.ProductsDTO;

public class ProductsDAO implements ProductsDAOInterface<ProductsDTO, Integer> {
    private Connection conn;

    public ProductsDAO() {
        try {
            conn = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean create(ProductsDTO entity) {
        
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        
        return false;
    }

    @Override
    public List<ProductsDTO> getAll() {
        
        return null;
    }

    @Override
    public ProductsDTO getById(Integer id) {
        try {
            String sql = "SELECT * FROM xemay where MAXE = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int productId = rs.getInt("MAXE");
                String productName = rs.getString("TENXE");
                String brand = rs.getString("HANGXE");
                BigDecimal price = rs.getBigDecimal("GIABAN");
                int quantity = rs.getInt("SOLUONG");

                return new ProductsDTO(productId, productName, brand, price, quantity);
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy thông tin sản phẩm: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean update(ProductsDTO entity) {
        
        return false;
    }
}
