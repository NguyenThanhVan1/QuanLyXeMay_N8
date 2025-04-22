package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import DAO.Interface.InvoicesDAOInterface;
import DTO.InvoicesDTO;

public class InvoicesDAO implements InvoicesDAOInterface<InvoicesDTO, Integer> {

    @Override
    public boolean create(InvoicesDTO invoice, Connection conn) {
        try {
            System.out.println(invoice);
            String sql = "INSERT INTO hoadon (NGAYLAP, MAKH, MANV, TONGTIEN, MADH) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(invoice.getDate().getTime()));
            pstmt.setInt(2, invoice.getCustomerId());
            pstmt.setInt(3, invoice.getEmployerID());
            pstmt.setBigDecimal(4, invoice.getTotalPrice());
            pstmt.setInt(5, invoice.getOrderID());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo hóa đơn: " + e.getMessage(), e);
        } 
    }

    @Override
    public boolean delete(Integer id) {
        
        return false;
    }

    @Override
    public List<InvoicesDTO> getAll() {
        
        return null;
    }

    @Override
    public List<InvoicesDTO> getById(Integer id) {
        
        return null;
    }

    @Override
    public boolean update(InvoicesDTO invoice) {
        
        return false;
    }
    
}
