package GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

import DAO.Database;
import DTO.XeMay;

public class TrangChu extends JFrame {
    private JPanel panelXe;
    private JTextField txtTen, txtGiaTu, txtGiaDen;
    private ArrayList<XeMay> danhSachXe = new ArrayList<>();

    public TrangChu() {
        setTitle("Trang Chủ Cửa Hàng Xe");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel hiển thị xe
        panelXe = new JPanel(new GridLayout(0, 4, 15, 15));
        panelXe.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(panelXe);

        // Panel tìm kiếm
        JPanel panelTimKiem = createSearchPanel();

        // Chia trái/phải
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelTimKiem, scrollPane);
        splitPane.setDividerLocation(250);
        add(splitPane);

        loadData();
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        panel.setPreferredSize(new Dimension(250, 0));

        // Tên xe
        JLabel lblTen = new JLabel("Tên xe:");
        lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtTen = new JTextField();
        txtTen.setMaximumSize(new Dimension(200, 25));
        txtTen.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Giá từ
        JLabel lblGiaTu = new JLabel("Giá từ:");
        lblGiaTu.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtGiaTu = new JTextField();
        txtGiaTu.setMaximumSize(new Dimension(200, 25));
        txtGiaTu.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Giá đến
        JLabel lblGiaDen = new JLabel("Giá đến:");
        lblGiaDen.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtGiaDen = new JTextField();
        txtGiaDen.setMaximumSize(new Dimension(200, 25));
        txtGiaDen.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nút tìm
        JButton btnTim = new JButton("Tìm Kiếm");
        btnTim.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTim.setBackground(new Color(0, 123, 255)); // Màu xanh dương
        btnTim.setForeground(Color.WHITE);
        btnTim.setMaximumSize(new Dimension(200, 35));

        // Thêm các thành phần tìm kiếm vào panel
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblTen);
        panel.add(txtTen);
        panel.add(Box.createVerticalStrut(10));

        panel.add(lblGiaTu);
        panel.add(txtGiaTu);
        panel.add(Box.createVerticalStrut(10));

        panel.add(lblGiaDen);
        panel.add(txtGiaDen);
        panel.add(Box.createVerticalStrut(20));

        panel.add(btnTim);

        // Tạo một Box để chứa các nút ở dưới cùng
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nút "Xem lại đơn hàng"
        JButton btnXemDonHang = new JButton("Xem lại đơn hàng");
        btnXemDonHang.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnXemDonHang.setMaximumSize(new Dimension(200, 50)); // Kích thước cố định cho nút

        // Nút "Xem lại lịch sử mua hàng"
        JButton btnXemLichSu = new JButton("Xem lại lịch sử mua hàng");
        btnXemLichSu.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnXemLichSu.setMaximumSize(new Dimension(200, 50)); // Kích thước cố định cho nút

        // Nút "Đăng xuất" (màu đỏ)
        JButton btnDangXuat = new JButton("Đăng xuất");
        btnDangXuat.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDangXuat.setMaximumSize(new Dimension(200, 50)); // Kích thước cố định cho nút
        btnDangXuat.setBackground(Color.RED);
        btnDangXuat.setForeground(Color.WHITE);

        // Thêm các nút vào panel dưới cùng
        bottomPanel.add(Box.createVerticalStrut(10)); // Khoảng cách trên cùng
        bottomPanel.add(btnXemDonHang);
        bottomPanel.add(Box.createVerticalStrut(10)); // Khoảng cách giữa các nút
        bottomPanel.add(btnXemLichSu);
        bottomPanel.add(Box.createVerticalStrut(10)); // Khoảng cách giữa các nút
        bottomPanel.add(btnDangXuat);

        // Thêm bottomPanel vào panel chính
        panel.add(Box.createVerticalGlue()); // Căn giữa các phần trên và dưới
        panel.add(bottomPanel);

        // Sự kiện tìm kiếm
        btnTim.addActionListener(e -> locVaHienThiXe());

        return panel;
    }

    private void loadData() {
        danhSachXe.clear();
        try {
            Connection conn = Database.getConnection();
            String sql = "SELECT * FROM XEMAY";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                XeMay xe = new XeMay(
                        rs.getString("MAXE"),
                        rs.getString("TENXE"),
                        rs.getString("HANGXE"),
                        rs.getInt("GIABAN"),
                        rs.getInt("SOLUONG"),
                        rs.getString("ANH"));
                danhSachXe.add(xe);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hienThiDanhSachXe(danhSachXe);
    }

    private void hienThiDanhSachXe(ArrayList<XeMay> ds) {
        panelXe.removeAll();
        for (XeMay xe : ds) {
            panelXe.add(createXePanel(xe));
        }
        panelXe.revalidate();
        panelXe.repaint();
    }

    private void locVaHienThiXe() {
        String tuKhoa = txtTen.getText().trim().toLowerCase();
        int giaTu = txtGiaTu.getText().isEmpty() ? 0 : Integer.parseInt(txtGiaTu.getText());
        int giaDen = txtGiaDen.getText().isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(txtGiaDen.getText());

        ArrayList<XeMay> ketQua = new ArrayList<>();
        for (XeMay xe : danhSachXe) {
            boolean tenHop = xe.getTenXe().toLowerCase().contains(tuKhoa);
            boolean giaHop = xe.getGiaBan() >= giaTu && xe.getGiaBan() <= giaDen;
            if (tenHop && giaHop) {
                ketQua.add(xe);
            }
        }
        hienThiDanhSachXe(ketQua);
    }

    private JPanel createXePanel(XeMay xe) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        p.setBackground(Color.WHITE);

        // Load ảnh
        ImageIcon icon;
        try {
            icon = new ImageIcon(getClass().getResource("/images/" + xe.getAnh()));

        } catch (Exception e) {
            icon = new ImageIcon(getClass().getResource("/images/default.png")); // ảnh mặc định nếu lỗi
        }
        JPanel imgPanel = new ImagePanel(icon);
        imgPanel.setPreferredSize(new Dimension(250, 120));
        imgPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        imgPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(xe.getTenXe());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = new JLabel("Giá: " + String.format("%,d", xe.getGiaBan()) + " VNĐ");
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(imgPanel);
        p.add(Box.createVerticalStrut(5));
        p.add(nameLabel);
        p.add(priceLabel);
        // Sự kiện click panel
        p.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new ChiTietXeDialog(TrangChu.this, xe).setVisible(true);
            }
        });
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrangChu().setVisible(true));
    }
}
