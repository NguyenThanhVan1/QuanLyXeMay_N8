package GUI.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThongTinPanel extends JPanel {
    private JTextField txtHoTen;
    private JTextField txtEmail;
    private JTextField txtSDT;
    private JTextField txtDiaChi;
    private JComboBox<String> cboPhuongThucThanhToan;
    private MainFrame mainFrame;
    
    public ThongTinPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Panel nhập thông tin
        JPanel panelThongTin = createInfoPanel();
        add(panelThongTin, BorderLayout.CENTER);
        
        // Panel nút bấm
        JPanel panelButton = createButtonPanel();
        add(panelButton, BorderLayout.SOUTH);
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin thanh toán"));
        
        // Thông tin cá nhân
        JPanel personalPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        personalPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        personalPanel.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField(20);
        personalPanel.add(txtHoTen);
        
        personalPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        personalPanel.add(txtEmail);
        
        personalPanel.add(new JLabel("Số điện thoại:"));
        txtSDT = new JTextField();
        personalPanel.add(txtSDT);
        
        personalPanel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        personalPanel.add(txtDiaChi);
        
        // Phương thức thanh toán
        JPanel paymentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        paymentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        paymentPanel.add(new JLabel("Phương thức thanh toán:"));
        cboPhuongThucThanhToan = new JComboBox<>(new String[]{
            "Thanh toán khi nhận hàng (COD)",
            "Chuyển khoản ngân hàng",
            "Thẻ tín dụng/Thẻ ghi nợ",
            "Ví điện tử (MoMo, ZaloPay...)"
        });
        paymentPanel.add(cboPhuongThucThanhToan);
        
        panel.add(personalPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(paymentPanel);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnQuayLai = new JButton("Quay lại giỏ hàng");
        btnQuayLai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.cardLayout.show(mainFrame.contentPanel, "GioHang");
            }
        });
        
        JButton btnXacNhan = new JButton("Xác nhận đặt hàng");
        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    JOptionPane.showMessageDialog(ThongTinPanel.this, 
                        "Đặt hàng thành công! Cảm ơn bạn đã mua sắm.", 
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Reset form
                    resetForm();
                    
                    // Quay về trang sản phẩm
                    mainFrame.cardLayout.show(mainFrame.contentPanel, "SanPham");
                }
            }
        });
        
        panel.add(btnQuayLai);
        panel.add(btnXacNhan);
        
        return panel;
    }
    
    private boolean validateForm() {
        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtHoTen.requestFocus();
            return false;
        }
        
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }
        
        if (txtSDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }
        
        if (txtDiaChi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtDiaChi.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void resetForm() {
        txtHoTen.setText("");
        txtEmail.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        cboPhuongThucThanhToan.setSelectedIndex(0);
    }
}