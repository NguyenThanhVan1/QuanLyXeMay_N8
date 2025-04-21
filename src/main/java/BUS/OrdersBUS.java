package BUS;

import BUS.Interface.OrdersBUSInterface;
import DAO.OrderDAO;
import DTO.OrderDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersBUS implements OrdersBUSInterface {
    private final OrderDAO orderDAO;

    public OrdersBUS() {
        this.orderDAO = new OrderDAO();
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        // Lấy danh sách tất cả đơn hàng từ OrderDAO
        return orderDAO.getAll();
    }

    @Override
    public OrderDTO getOrderById(String orderId) {
        // Lấy thông tin đơn hàng theo ID từ OrderDAO
        return orderDAO.getById(orderId);
    }

    @Override
    public boolean addOrder(OrderDTO order) {
        // Thêm đơn hàng mới thông qua OrderDAO
        return orderDAO.create(order);
    }

    @Override
    public boolean updateOrder(OrderDTO order) {
        // Cập nhật thông tin đơn hàng thông qua OrderDAO
        return orderDAO.update(order);
    }

    @Override
    public boolean deleteOrder(String orderId) {
        // Xóa đơn hàng theo ID thông qua OrderDAO
        OrderDTO order = orderDAO.getById(orderId);
        order.setStatus("Đã hủy"); // Cập nhật trạng thái đơn hàng thành "Đã hủy"
        return orderDAO.update(order);
    }

    public List<OrderDTO> getOrdersByFilters(Date fromDate, Date toDate, String statusFilter) {
        if(fromDate == null && toDate == null){
            List<OrderDTO> list = orderDAO.getOrdersByStatus(statusFilter);
            return list;
        }

        List<OrderDTO> listFiltered = new ArrayList<>();
        List<OrderDTO> list = orderDAO.getAll();
        for(OrderDTO order : list) {
            Date orderDate = order.getCreatedDate();
            if (!orderDate.before(fromDate) && !orderDate.after(toDate)) {
                listFiltered.add(order);
            }
        }

        if(statusFilter.equals("Tất cả")){
            listFiltered = orderDAO.getAll();
            return listFiltered;
        }
        
        listFiltered = orderDAO.getOrdersByStatus(statusFilter);
        return listFiltered;
    }
}
