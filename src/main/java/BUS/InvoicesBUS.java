package BUS;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import BUS.Interface.InvoicesBUSInterface;
import DAO.Database;
import DAO.InvoicesDAO;
import DAO.DetailOrdersDAO;
import DTO.DetailOrdersDTO;
import DTO.InvoicesDTO;
import DTO.OrdersDTO;

public class InvoicesBUS implements InvoicesBUSInterface<InvoicesDTO, Integer> {
    private Connection conn;
    private InvoicesDAO invoicesDAO;
    private DetailOrdersDAO detailOrdersDAO;

    public InvoicesBUS() {
        try {
            this.conn = Database.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), e);
        }
        
        this.invoicesDAO = new InvoicesDAO();
        this.detailOrdersDAO = new DetailOrdersDAO();
    }

    @Override
    public List<InvoicesDTO> getAll() {
        return null;
    }

    @Override
    public List<InvoicesDTO> getById(Integer invoiceId) {
        return null;
    }

    @Override
    public boolean create(Integer customerID, Integer employerID, Integer orderID) {
        try {
            Date currentDate = new Date(System.currentTimeMillis());
            List<DetailOrdersDTO> detailOrders = detailOrdersDAO.getById(orderID);
            BigDecimal totalPrice = BigDecimal.ZERO;
            for(DetailOrdersDTO detailOrder : detailOrders) {
                totalPrice = totalPrice.add(detailOrder.getTotalPrice());
            }
            InvoicesDTO invoice = new InvoicesDTO(currentDate, customerID, employerID, totalPrice, orderID);
            System.out.println(invoice);
            this.invoicesDAO.create(invoice, conn);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(InvoicesDTO invoice) {
        return false;
    }

    @Override
    public boolean delete(Integer invoiceId) {
        return false;
    }
    
}
