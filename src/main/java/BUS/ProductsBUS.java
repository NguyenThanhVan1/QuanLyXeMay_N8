package BUS;

import java.sql.Connection;

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
    
}
