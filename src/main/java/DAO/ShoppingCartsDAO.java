package DAO;

import java.sql.Connection;
import java.util.List;

import DTO.ShoppingCartsDTO;

public class ShoppingCartsDAO implements DAO.Interface.ShoppingCartsDAOInterface<DTO.ShoppingCartsDTO, String> {
    @Override
    public boolean create(DTO.ShoppingCartsDTO entity, java.sql.Connection conn) {
        try {
            String sql = "INSERT INTO giohang (idKhachHang, idXe, soLuong) VALUES (?, ?, ?)";
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, entity.getIdCustomer());
            pstmt.setString(2, entity.getIdProduct());      
            pstmt.setInt(3, entity.getQuantity());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
    }

    @Override
    public boolean delete(String id, java.sql.Connection conn) {
        try {
            String sql = "DELETE FROM giohang WHERE idKhachHang = ?";
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }

    @Override
    public java.util.List<DTO.ShoppingCartsDTO> getAll(java.sql.Connection conn) {
        try {
            // TODO Auto-generated method stub
            return null;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public List<DTO.ShoppingCartsDTO> getByIdCustomer(String idCustomer, java.sql.Connection conn) {
        try {
            String sql = "SELECT * FROM giohang WHERE idKhachHang = ?";
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idCustomer);
            java.sql.ResultSet rs = pstmt.executeQuery();
            java.util.List<DTO.ShoppingCartsDTO> shoppingCarts = new java.util.ArrayList<>();
            while (rs.next()) {
                DTO.ShoppingCartsDTO shoppingCart = new DTO.ShoppingCartsDTO();
                shoppingCart.setIdCustomer(rs.getString("idKhachHang"));
                shoppingCart.setIdProduct(rs.getString("idXe"));
                shoppingCart.setQuantity(rs.getInt("soLuong"));
                shoppingCarts.add(shoppingCart);
            }
            return shoppingCarts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(DTO.ShoppingCartsDTO entity, java.sql.Connection conn) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean insert(DTO.ShoppingCartsDTO entity, java.sql.Connection conn) {
        try {
            String sql = "UPDATE giohang SET soLuong = ? WHERE idKhachHang = ? AND idXe = ?";
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, entity.getQuantity());
            pstmt.setString(2, entity.getIdCustomer());
            pstmt.setString(3, entity.getIdProduct());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ShoppingCartsDTO checkExist(String idCustomer, String idProduct, java.sql.Connection conn) {
        try {
            String sql = "SELECT * FROM giohang WHERE idKhachHang = ? AND idXe = ?";
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idCustomer);
            pstmt.setString(2, idProduct);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                DTO.ShoppingCartsDTO shoppingCart = new DTO.ShoppingCartsDTO();
                shoppingCart.setIdCustomer(rs.getString("idKhachHang"));
                shoppingCart.setIdProduct(rs.getString("idXe"));
                shoppingCart.setQuantity(rs.getInt("soLuong"));
                return shoppingCart;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DTO.ProductsDTO> getByShoppingCart(String idCustomer, java.sql.Connection conn) {
        try {
            String sql = "SELECT * FROM giohang WHERE idKhachHang = ?";
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idCustomer);
            java.sql.ResultSet rs = pstmt.executeQuery();
            java.util.List<DTO.ProductsDTO> products = new java.util.ArrayList<>();
            while (rs.next()) {
                DTO.ProductsDTO product = new DTO.ProductsDTO();
                product.setProductId((rs.getString("idXe")));
                product.setQuantity(rs.getInt("soLuong"));
                products.add(product);
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean deleteByIdProduct(String idProduct, Connection conn){
        try {
            String sql = "DELETE FROM giohang WHERE idXe = ?";
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idProduct);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
