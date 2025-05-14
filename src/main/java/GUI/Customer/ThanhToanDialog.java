package GUI.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import BUS.OrdersBUS;
import BUS.UsersBUS;
import DTO.OrdersDTO;
import DTO.ProductsDTO;
import DTO.UsersDTO;
import GUI.IdCurrentUser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ThanhToanDialog extends JDialog {
    
    private JTextField txtHoTen;
    private JTextField txtDiaChi;
    private JTextField txtSoDienThoai;
    private JLabel lblTongTien;
    private JTextArea txtGhiChu;
    private JComboBox<String> cboHinhThucThanhToan;
    
    private BigDecimal tongTien;
    private List<ProductsDTO> danhSachSanPham;
    private UsersDTO khachHang;
    
    // Thêm màu sắc để giao diện đẹp hơn
    private final Color PRIMARY_COLOR = new Color(25, 118, 210);
    private final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private final Color CARD_COLOR = Color.WHITE;
    
    public ThanhToanDialog(Frame owner, List<ProductsDTO> danhSachSanPham, BigDecimal tongTien) {
        super(owner, "Thông tin thanh toán", true);
        this.danhSachSanPham = danhSachSanPham;
        this.tongTien = tongTien;
        
        // Lấy thông tin khách hàng từ database
        loadUserInfo();
        
        initComponents();
        
        setSize(500, 600);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void loadUserInfo() {
        try {
            UsersBUS usersBUS = new UsersBUS();
            IdCurrentUser idCurrentUser = new IdCurrentUser();
            String userId = idCurrentUser.getCurrentUserId();
            
            khachHang = usersBUS.getById(userId);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initComponents() {
        // Panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Tiêu đề
        JLabel lblTitle = new JLabel("XÁC NHẬN ĐƠN HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(PRIMARY_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Panel thông tin khách hàng
        JPanel customerPanel = createPanel("Thông tin khách hàng");
        
        txtHoTen = createTextField();
        if (khachHang != null) {
            txtHoTen.setText(khachHang.getName());
        }
        
        txtDiaChi = createTextField();
        if (khachHang != null && khachHang.getAddress() != null) {
            txtDiaChi.setText(khachHang.getAddress());
        }
        
        txtSoDienThoai = createTextField();
        if (khachHang != null && khachHang.getPhone() != null) {
            txtSoDienThoai.setText(khachHang.getPhone());
        }
        
        addFieldToPanel(customerPanel, "Họ và tên:", txtHoTen);
        addFieldToPanel(customerPanel, "Địa chỉ:", txtDiaChi);
        addFieldToPanel(customerPanel, "Số điện thoại:", txtSoDienThoai);
        
        mainPanel.add(customerPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Panel thanh toán
        JPanel paymentPanel = createPanel("Thông tin thanh toán");
        
        // Phương thức thanh toán
        JLabel lblPhuongThuc = new JLabel("Phương thức thanh toán:");
        cboHinhThucThanhToan = new JComboBox<>(new String[]{"Tiền mặt khi nhận hàng", "Chuyển khoản ngân hàng", "Thanh toán qua ví điện tử"});
        
        JPanel phuongThucPanel = new JPanel(new BorderLayout(10, 0));
        phuongThucPanel.setOpaque(false);
        phuongThucPanel.add(lblPhuongThuc, BorderLayout.WEST);
        phuongThucPanel.add(cboHinhThucThanhToan, BorderLayout.CENTER);
        paymentPanel.add(phuongThucPanel);
        paymentPanel.add(Box.createVerticalStrut(10));
        
        
        // Tổng tiền
        tongTien = tongTien.add(tongTien.multiply(new BigDecimal(0.1))); // Giả sử VAT là 10%
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        JPanel tongTienPanel = new JPanel(new BorderLayout(10, 0));
        tongTienPanel.setOpaque(false);
        
        JLabel lblText = new JLabel("Tổng tiền thanh toán (Bao gồm VAT):");
        lblText.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        lblTongTien = new JLabel(decimalFormat.format(tongTien) + " VND");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTongTien.setForeground(new Color(220, 53, 69)); // Màu đỏ đậm cho tổng tiền
        
        tongTienPanel.add(lblText, BorderLayout.WEST);
        tongTienPanel.add(lblTongTien, BorderLayout.EAST);
        
        paymentPanel.add(tongTienPanel);
        
        mainPanel.add(paymentPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        
        JButton btnXacNhan = new JButton("Xác nhận đặt hàng");
        btnXacNhan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXacNhan.setBackground(PRIMARY_COLOR);
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setFocusPainted(false);
        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // xuLyThanhToan();
            }
        });
        
        JButton btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnHuy.setFocusPainted(false);
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        buttonPanel.add(btnXacNhan);
        buttonPanel.add(btnHuy);
        
        mainPanel.add(buttonPanel);
        
        // Thêm panel chính vào dialog
        setContentPane(mainPanel);
    }
    
    private JPanel createPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                title,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14),
                PRIMARY_COLOR
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }
    
    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return textField;
    }
    
    private void addFieldToPanel(JPanel panel, String labelText, JComponent field) {
        JPanel fieldPanel = new JPanel(new BorderLayout(10, 0));
        fieldPanel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, 25));
        
        fieldPanel.add(label, BorderLayout.WEST);
        fieldPanel.add(field, BorderLayout.CENTER);
        
        panel.add(fieldPanel);
        panel.add(Box.createVerticalStrut(10));
    }
    
    // private void xuLyThanhToan() {
    //     String hoTen = txtHoTen.getText().trim();
    //     String diaChi = txtDiaChi.getText().trim();
    //     String soDienThoai = txtSoDienThoai.getText().trim();
    //     String ghiChu = txtGhiChu.getText().trim();
    //     String phuongThucThanhToan = (String) cboHinhThucThanhToan.getSelectedItem();
        
    //     // Kiểm tra dữ liệu nhập
    //     if (hoTen.isEmpty() || diaChi.isEmpty() || soDienThoai.isEmpty()) {
    //         JOptionPane.showMessageDialog(this, 
    //             "Vui lòng nhập đầy đủ thông tin khách hàng!", 
    //             "Lỗi", 
    //             JOptionPane.ERROR_MESSAGE);
    //         return;
    //     }
        
    //     try {
    //         // Tạo đơn hàng mới
    //         OrdersDTO donHang = new OrdersDTO();
    //         donHang.setCustomerId(khachHang.getId());
    //         donHang.setCreatedDate(new Date());
    //         donHang.setTotalAmount(tongTien);
    //         donHang.setStatus("Chờ xác nhận");
    //         donHang.setAddress(diaChi);
    //         donHang.setStatus("Chờ xử lý");
            
    //         // Lưu đơn hàng vào database
    //         OrdersBUS ordersBUS = new OrdersBUS();
    //         boolean success = ordersBUS.create(donHang);
            
    //         if (success) {
    //             // Thêm chi tiết đơn hàng
    //             for (ProductsDTO sp : danhSachSanPham) {
    //                 // Giả sử có phương thức để thêm chi tiết đơn hàng
    //                 ordersBUS.addOrderDetail(donHang.getOrderId(), sp.getProductId(), sp.getQuantity(), sp.getPrice());
    //             }
                
    //             // Xóa giỏ hàng sau khi đặt hàng thành công
    //             IdCurrentUser idCurrentUser = new IdCurrentUser();
    //             String userId = idCurrentUser.getCurrentUserId();
    //             ordersBUS.clearCart(userId);
                
    //             JOptionPane.showMessageDialog(this, 
    //                 "Đặt hàng thành công!\nMã đơn hàng: " + donHang.getOrderId(), 
    //                 "Thông báo", 
    //                 JOptionPane.INFORMATION_MESSAGE);
                
    //             dispose();
                
    //             // Cập nhật lại giỏ hàng
    //             if (getOwner() instanceof MainFrame) {
    //                 ((MainFrame) getOwner()).refreshGioHang();
    //             }
    //         } else {
    //             JOptionPane.showMessageDialog(this, 
    //                 "Đã xảy ra lỗi khi đặt hàng. Vui lòng thử lại sau!", 
    //                 "Lỗi", 
    //                 JOptionPane.ERROR_MESSAGE);
    //         }
            
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         JOptionPane.showMessageDialog(this, 
    //             "Đã xảy ra lỗi: " + e.getMessage(), 
    //             "Lỗi", 
    //             JOptionPane.ERROR_MESSAGE);
    //     }
    // }
    
    private String generateOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String datePrefix = sdf.format(new Date());
        String randomPart = UUID.randomUUID().toString().substring(0, 8);
        
        return "DH" + datePrefix + randomPart.toUpperCase();
    }
}