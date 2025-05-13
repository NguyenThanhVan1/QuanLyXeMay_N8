package GUI.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoiMatKhauPanel extends JPanel {
    private JPasswordField txtMatKhauCu;
    private JPasswordField txtMatKhauMoi;
    private JPasswordField txtXacNhanMatKhau;
    private MainFrame mainFrame;
    
    public DoiMatKhauPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(50, 100, 50, 100));
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Tiêu đề
        JLabel lblTitle = new JLabel("Đổi mật khẩu");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(30));
        
        // Form đổi mật khẩu
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Panel nút bấm
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 20));
        panel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        panel.add(new JLabel("Mật khẩu cũ:"));
        txtMatKhauCu = new JPasswordField();
        panel.add(txtMatKhauCu);
        
        panel.add(new JLabel("Mật khẩu mới:"));
        txtMatKhauMoi = new JPasswordField();
        panel.add(txtMatKhauMoi);
        
        panel.add(new JLabel("Xác nhận mật khẩu mới:"));
        txtXacNhanMatKhau = new JPasswordField();
        panel.add(txtXacNhanMatKhau);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    JOptionPane.showMessageDialog(DoiMatKhauPanel.this, 
                                                "Đổi mật khẩu thành công!", 
                                                "Thông báo", 
                                                JOptionPane.INFORMATION_MESSAGE);
                    
                    // Reset form
                    resetForm();
                }
            }
        });
        
        JButton btnHuy = new JButton("Hủy");
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
                mainFrame.cardLayout.show(mainFrame.contentPanel, "SanPham");
            }
        });
        
        panel.add(btnXacNhan);
        panel.add(btnHuy);
        
        return panel;
    }
    
    private boolean validateForm() {
        if (txtMatKhauCu.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, 
                                        "Vui lòng nhập mật khẩu cũ!", 
                                        "Lỗi", 
                                        JOptionPane.ERROR_MESSAGE);
            txtMatKhauCu.requestFocus();
            return false;
        }
        
        if (txtMatKhauMoi.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, 
                                        "Vui lòng nhập mật khẩu mới!", 
                                        "Lỗi", 
                                        JOptionPane.ERROR_MESSAGE);
            txtMatKhauMoi.requestFocus();
            return false;
        }
        
        if (txtXacNhanMatKhau.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, 
                                        "Vui lòng xác nhận mật khẩu mới!", 
                                        "Lỗi", 
                                        JOptionPane.ERROR_MESSAGE);
            txtXacNhanMatKhau.requestFocus();
            return false;
        }
        
        String matKhauMoi = new String(txtMatKhauMoi.getPassword());
        String xacNhanMatKhau = new String(txtXacNhanMatKhau.getPassword());
        
        if (!matKhauMoi.equals(xacNhanMatKhau)) {
            JOptionPane.showMessageDialog(this, 
                                        "Mật khẩu xác nhận không khớp với mật khẩu mới!", 
                                        "Lỗi", 
                                        JOptionPane.ERROR_MESSAGE);
            txtXacNhanMatKhau.requestFocus();
            return false;
        }
        
        // Kiểm tra mật khẩu cũ (giả lập)
        String matKhauCu = new String(txtMatKhauCu.getPassword());
        if (!matKhauCu.equals("password")) { // Mật khẩu mặc định giả định là "password"
            JOptionPane.showMessageDialog(this, 
                                        "Mật khẩu cũ không chính xác!", 
                                        "Lỗi", 
                                        JOptionPane.ERROR_MESSAGE);
            txtMatKhauCu.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void resetForm() {
        txtMatKhauCu.setText("");
        txtMatKhauMoi.setText("");
        txtXacNhanMatKhau.setText("");
    }
}