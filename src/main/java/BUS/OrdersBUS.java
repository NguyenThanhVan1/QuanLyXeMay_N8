package BUS;

import BUS.Interface.OrdersBUSInterface;
import DAO.Database;
import DAO.DetailOrdersDAO;
import DAO.InvoicesDAO;
import DAO.OrdersDAO;
import DTO.DetailOrdersDTO;
import DTO.InvoicesDTO;
import DTO.OrdersDTO;
import DTO.ProductsDTO;
import GUI.IdCurrentUser;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersBUS implements OrdersBUSInterface<OrdersDTO, Integer> {
    private OrdersDAO orderDAO;
    private DetailOrdersDAO detailOrderDAO;
    private InvoicesDAO invoicesDAO;
    private Connection conn;


    public OrdersBUS() {
        this.orderDAO = new OrdersDAO();
        this.detailOrderDAO = new DetailOrdersDAO();
        this.invoicesDAO = new InvoicesDAO();
        try {
            this.conn = Database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrdersDTO> getAll() {
        
        return orderDAO.getAll();
    }

    @Override
    public OrdersDTO getById(Integer orderId) {
        
        return orderDAO.getById(orderId);
    }

    @Override
    public boolean create(OrdersDTO order, List<ProductsDTO> productList) {
        try {
            orderDAO.create(order);

            List <DetailOrdersDTO> detailOrders = new ArrayList<>();
            for(ProductsDTO product : productList) {
                BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity()));
                DetailOrdersDTO detailOrder = new DetailOrdersDTO(order.getOrderId(), product.getProductId(), product.getQuantity(), product.getPrice(), total);
                detailOrders.add(detailOrder);
            }
            detailOrderDAO.create(detailOrders);
            
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm đơn hàng: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(OrdersDTO order) {       
        if(order.getStatus().equals("Đã hoàn thành")){
            try {
                this.conn.setAutoCommit(false);

                Date date = order.getCreatedDate();
                List<DetailOrdersDTO> list = this.detailOrderDAO.getById(order.getOrderId()); 
                BigDecimal total = BigDecimal.ZERO;
                for(DetailOrdersDTO detailOrder : list) {
                    total = total.add(detailOrder.getTotalPrice()); 
                }
                InvoicesDTO invoice = new InvoicesDTO(date, order.getCustomerId(), IdCurrentUser.getCurrentUserId(), total, order.getOrderId());
                
                this.invoicesDAO.create(invoice, conn); 
                this.orderDAO.update(order, conn);

                conn.commit();
                return true;
            } catch (Exception e) {
                try {
                    conn.rollback(); 
                } catch (Exception rollbackEx) {
                    System.out.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
                e.printStackTrace();
                return false;
            } 
        }
        else{
            try {
                this.orderDAO.update(order, conn); 
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            
        }
    }

    @Override
    public boolean delete(Integer orderId) {
        //Xóa bảng chi tiết đơn hàng
        try {
            OrdersDTO order = orderDAO.getById(orderId);
            order.setStatus("Đã hủy"); 
            orderDAO.update(order, conn);
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<OrdersDTO> getOrdersByFilters(Date fromDate, Date toDate, String statusFilter) {
        if(fromDate == null && toDate == null){
            List<OrdersDTO> list = orderDAO.getByStatus(statusFilter);
            return list;
        }

        List<OrdersDTO> listFiltered = new ArrayList<>();
        List<OrdersDTO> list = orderDAO.getAll();
        for(OrdersDTO order : list) {
            Date orderDate = order.getCreatedDate();
            if (!orderDate.before(fromDate) && !orderDate.after(toDate)) {
                listFiltered.add(order);
            }
        }

        if(statusFilter.equals("Tất cả")){
            listFiltered = orderDAO.getAll();
            return listFiltered;
        }
        
        listFiltered = orderDAO.getByStatus(statusFilter);
        return listFiltered;
    }
}
