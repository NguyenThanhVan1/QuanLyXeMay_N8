package BUS.Interface;

import DTO.OrderDTO;
import java.util.List;

public interface OrdersBUSInterface {
    /**
     * Lấy danh sách tất cả đơn hàng.
     * @return Danh sách các đối tượng OrderDTO.
     */
    List<OrderDTO> getAllOrders();

    /**
     * Lấy thông tin đơn hàng theo ID.
     * @param orderId Mã đơn hàng.
     * @return Đối tượng OrderDTO chứa thông tin đơn hàng.
     */
    OrderDTO getOrderById(String orderId);

    /**
     * Thêm đơn hàng mới.
     * @param order Đối tượng OrderDTO chứa thông tin đơn hàng cần thêm.
     * @return True nếu thêm thành công, ngược lại False.
     */
    boolean addOrder(OrderDTO order);

    /**
     * Cập nhật thông tin đơn hàng.
     * @param order Đối tượng OrderDTO chứa thông tin đơn hàng cần cập nhật.
     * @return True nếu cập nhật thành công, ngược lại False.
     */
    boolean updateOrder(OrderDTO order);

    /**
     * Xóa đơn hàng theo ID.
     * @param orderId Mã đơn hàng cần xóa.
     * @return True nếu xóa thành công, ngược lại False.
     */
    boolean deleteOrder(String orderId);
}