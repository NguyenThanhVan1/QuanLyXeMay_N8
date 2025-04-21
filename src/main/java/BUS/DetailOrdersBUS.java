package BUS;

import BUS.Interface.DetailOrdersBUSInterface;
import DAO.DetailOrderDAO;
import DTO.DetailOrderDTO;

import java.util.List;

public class DetailOrdersBUS implements DetailOrdersBUSInterface {
    private final DetailOrderDAO detailOrderDAO;

    public DetailOrdersBUS() {
        this.detailOrderDAO = new DetailOrderDAO();
    }

    @Override
    public List<DetailOrderDTO> getAllDetailOrders() {
        // Lấy danh sách tất cả chi tiết đơn hàng từ DetailOrderDAO
        return detailOrderDAO.getAll();
    }

    @Override
    public List<DetailOrderDTO> getDetailOrdersByOrderId(String orderId) {
        // Lấy danh sách chi tiết đơn hàng theo mã đơn hàng từ DetailOrderDAO
        return detailOrderDAO.getByOrderId(orderId);
    }

    @Override
    public boolean addDetailOrder(DetailOrderDTO detailOrder) {
        // Thêm chi tiết đơn hàng mới thông qua DetailOrderDAO
        return detailOrderDAO.create(detailOrder);
    }

    @Override
    public boolean updateDetailOrder(DetailOrderDTO detailOrder) {
        // Cập nhật thông tin chi tiết đơn hàng thông qua DetailOrderDAO
        return detailOrderDAO.update(detailOrder);
    }

    @Override
    public boolean deleteDetailOrder(String orderId, String xeId) {
        // Xóa chi tiết đơn hàng theo mã đơn hàng và mã xe thông qua DetailOrderDAO
        return detailOrderDAO.delete(orderId, xeId);
    }
}
