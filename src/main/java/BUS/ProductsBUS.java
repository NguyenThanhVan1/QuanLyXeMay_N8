package BUS;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import BUS.Interface.ProductsBUSInterface;
import DAO.ProductsDAO;
import DTO.ProductsDTO;

public class ProductsBUS implements ProductsBUSInterface<ProductsDTO, Integer> {
    private Connection conn;
    private ProductsDAO productDAO;
    

    public ProductsBUS() {
        try {
            conn = DAO.Database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.productDAO = new ProductsDAO();
    }

    @Override
    public boolean create(DTO.ProductsDTO productDTO) {
        
        return false;
    }

    @Override
    public boolean update(DTO.ProductsDTO productDTO) {
        
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        
        return false;
    }

    @Override
    public DTO.ProductsDTO getById(Integer id) {
        try {
            return this.productDAO.getById(id, conn);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy thông tin xe máy: " + e.getMessage(), e);
        }
    }

    @Override
    public java.util.List<DTO.ProductsDTO> getAll() {
        
        return this.productDAO.getAll(conn);
    }

    public List<ProductsDTO> getFailedProductsAfterOrders(List<ProductsDTO> products) {
        try {
            List<ProductsDTO> list = this.productDAO.getAll(conn);
            List<ProductsDTO> failedProducts = new ArrayList<>();
            for (ProductsDTO product : list) {
                for (ProductsDTO orderedProduct : products) {
                    if (product.getProductId().equals(orderedProduct.getProductId())) {
                        if (product.getQuantity() < orderedProduct.getQuantity()) {
                            failedProducts.add(product);
                        }
                    }
                }
            }
            return failedProducts;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách sản phẩm không đủ số lượng: " + e.getMessage(), e);
        }
    }
    
}
