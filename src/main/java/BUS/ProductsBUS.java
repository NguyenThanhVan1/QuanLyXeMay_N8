package BUS;

import BUS.Interface.ProductsBUSInterface;
import DAO.ProductsDAO;
import DTO.ProductsDTO;

public class ProductsBUS implements ProductsBUSInterface<ProductsDTO, Integer> {
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
            ProductsDAO productDAO = new ProductsDAO();
            ProductsDTO productDTO = productDAO.getById(id);
            return productDTO;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy thông tin xe máy: " + e.getMessage(), e);
        }
    }

    @Override
    public java.util.List<DTO.ProductsDTO> getAll() {
        
        return null;
    }
    
}
