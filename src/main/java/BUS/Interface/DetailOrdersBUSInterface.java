package BUS.Interface;

import DTO.DetailOrderDTO;
import java.util.List;

public interface DetailOrdersBUSInterface {
    /**
     * Lấy danh sách tất cả chi tiết đơn hàng.
     * @return Danh sách các đối tượng DetailOrderDTO.
     */
    List<DetailOrderDTO> getAllDetailOrders();

    /**
     * Lấy danh sách chi tiết đơn hàng theo mã đơn hàng.
     * @param orderId Mã đơn hàng.
     * @return Danh sách các đối tượng DetailOrderDTO thuộc đơn hàng.
     */
    List<DetailOrderDTO> getDetailOrdersByOrderId(String orderId);

    /**
     * Thêm chi tiết đơn hàng mới.
     * @param detailOrder Đối tượng DetailOrderDTO chứa thông tin chi tiết đơn hàng cần thêm.
     * @return True nếu thêm thành công, ngược lại False.
     */
    boolean addDetailOrder(DetailOrderDTO detailOrder);

    /**
     * Cập nhật thông tin chi tiết đơn hàng.
     * @param detailOrder Đối tượng DetailOrderDTO chứa thông tin chi tiết đơn hàng cần cập nhật.
     * @return True nếu cập nhật thành công, ngược lại False.
     */
    boolean updateDetailOrder(DetailOrderDTO detailOrder);

    /**
     * Xóa chi tiết đơn hàng theo mã đơn hàng và mã xe.
     * @param orderId Mã đơn hàng.
     * @param xeId Mã xe.
     * @return True nếu xóa thành công, ngược lại False.
     */
    boolean deleteDetailOrder(String orderId, String xeId);
}
