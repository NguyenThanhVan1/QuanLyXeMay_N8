package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

public class KhachHangBUS {

    // Phương thức tĩnh cần phải khởi tạo đối tượng tĩnh hoặc gọi phương thức tĩnh
    public static boolean checkCustomerLogin(String username, String password) {
        // Khởi tạo đối tượng KhachHangDAO trong phương thức tĩnh
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        KhachHangDTO kh = khachHangDAO.checkLogin(username, password);
        return kh != null;
    }
}
