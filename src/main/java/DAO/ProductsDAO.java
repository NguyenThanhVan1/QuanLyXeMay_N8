package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.Interface.ProductsDAOInterface;
import DTO.ProductsDTO;

public class ProductsDAO implements ProductsDAOInterface<ProductsDTO, Integer> {
    private Connection conn;

    public ProductsDAO() {
        try {
            conn = Database1.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage(), e);
        }
    }


    @Override
    public boolean create(ProductsDTO entity, Connection conn) {
        
        return false;
    }

    @Override
    public boolean delete(Integer id, Connection conn) {
        
        return false;
    }

    @Override
    public List<ProductsDTO> getAll(Connection conn) {
        
        return null;
    }

    @Override
    public ProductsDTO getById(Integer id, Connection conn) {
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
    public boolean update(List<ProductsDTO> entity, Connection conn) {
        try {
            conn.setAutoCommit(false);
            String sql = "UPDATE xemay SET TENXE = ?, HANGXE = ?, GIABAN = ?, SOLUONG = ? WHERE MAXE = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            for (ProductsDTO product : entity) {
                ps.setString(1, product.getProductName());
                ps.setString(2, product.getBrand());
                ps.setBigDecimal(3, product.getPrice());
                ps.setInt(4, product.getQuantity());
                ps.setInt(5, product.getProductId());
                ps.addBatch();
            }

            ps.executeBatch();
            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                conn.rollback(); // Rollback nếu có lỗi xảy ra
            } catch (SQLException rollbackEx) {
                System.out.println("Lỗi khi rollback: " + rollbackEx.getMessage());
            }
            throw new RuntimeException("Lỗi khi cập nhật thông tin sản phẩm: " + e.getMessage(), e);
        }
    }
}
