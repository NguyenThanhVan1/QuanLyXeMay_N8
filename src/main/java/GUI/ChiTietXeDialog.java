package GUI;

import DTO.XeMay;

import javax.swing.*;
import java.awt.*;

public class ChiTietXeDialog extends JDialog {

    public ChiTietXeDialog(JFrame parent, XeMay xe) {
        super(parent, "Chi Tiết Xe", true);
        setLayout(new BorderLayout());
        setSize(450, 300); // Tăng chiều rộng để chứa ảnh
        setLocationRelativeTo(parent);

        // Panel thông tin
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS)); // Sử dụng BoxLayout theo chiều ngang
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel bên trái (Thông tin xe)
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(5, 1, 10, 10));

        detailsPanel.add(new JLabel("Mã xe: " + xe.getMaXe()));
        detailsPanel.add(new JLabel("Tên xe: " + xe.getTenXe()));
        detailsPanel.add(new JLabel("Hãng xe: " + xe.getHangXe()));
        detailsPanel.add(new JLabel("Giá bán: " + String.format("%,d", xe.getGiaBan()) + " VNĐ"));
        detailsPanel.add(new JLabel("Số lượng còn: " + xe.getSoLuong()));

        // Panel bên phải (Ảnh xe)
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

        // Tải ảnh xe
        ImageIcon icon;
        try {
            icon = new ImageIcon(getClass().getResource("/images/" + xe.getAnh()));
            // Thay đổi kích thước ảnh để nó vừa vặn với JLabel
            Image img = icon.getImage().getScaledInstance(250, 220, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        } catch (Exception e) {
            icon = new ImageIcon(getClass().getResource("/images/xemay1.png"));
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setPreferredSize(new Dimension(200, 120)); // Kích thước ảnh
        imagePanel.add(imageLabel);

        // Thêm hai panel vào infoPanel
        infoPanel.add(detailsPanel);
        infoPanel.add(Box.createHorizontalStrut(20)); // Khoảng cách giữa thông tin và ảnh
        infoPanel.add(imagePanel);

        JButton btnThemGio = new JButton("Thêm vào giỏ hàng");
        btnThemGio.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Đã thêm vào giỏ hàng!");
            dispose(); // Đóng dialog
        });

        // Thiết lập chiều cao cho nút
        btnThemGio.setPreferredSize(new Dimension(btnThemGio.getPreferredSize().width, 40));

        add(infoPanel, BorderLayout.CENTER);
        add(btnThemGio, BorderLayout.SOUTH);
    }
}
