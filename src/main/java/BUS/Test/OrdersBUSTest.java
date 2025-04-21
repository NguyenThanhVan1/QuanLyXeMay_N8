package BUS.Test;

import BUS.OrdersBUS;
import DTO.OrderDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrdersBUSTest {
    public static void main(String[] args) {
        OrdersBUS ordersBUS = new OrdersBUS();

        // Test getAllOrders
        System.out.println("=== Test getAllOrders ===");
        List<OrderDTO> orders = ordersBUS.getAllOrders();
        for (OrderDTO order : orders) {
            System.out.println(order);
        }

        // Test getOrderById
//         System.out.println("\n=== Test getOrderById ===");
//         String testOrderId = "DH001"; // Thay bằng mã đơn hàng có trong cơ sở dữ liệu
//         OrderDTO order = ordersBUS.getOrderById(testOrderId);
//         if (order != null) {
//             System.out.println(order);
//         } else {
//             System.out.println("Không tìm thấy đơn hàng với mã: " + testOrderId);
//         }

    //     // Test addOrder
//         System.out.println("\n=== Test addOrder ===");
//         OrderDTO newOrder = new OrderDTO(
//                 "DH999", // Mã đơn hàng mới
//                 new Date(),
//                 "KH001", // Mã khách hàng
//                 "123 Đường ABC, TP.HCM", // Địa chỉ
//                 new BigDecimal("1000000"), // Tổng tiền
//                 "Chờ duyệt" // Trạng thái
//         );
//         boolean isAdded = ordersBUS.addOrder(newOrder);
//         System.out.println("Thêm đơn hàng mới: " + (isAdded ? "Thành công" : "Thất bại"));

         // Test updateOrder
//         System.out.println("\n=== Test updateOrder ===");
//         if (order != null) {
//             order.setStatus("Đã duyệt");
//             boolean isUpdated = ordersBUS.updateOrder(order);
//             System.out.println("Cập nhật đơn hàng: " + (isUpdated ? "Thành công" : "Thất bại"));
//         }

//         // Test deleteOrder
//         System.out.println("\n=== Test deleteOrder ===");
//         String deleteOrderId = "DH999"; // Mã đơn hàng cần xóa
//         boolean isDeleted = ordersBUS.deleteOrder(deleteOrderId);
//         System.out.println("Xóa đơn hàng: " + (isDeleted ? "Thành công" : "Thất bại"));
    }
}