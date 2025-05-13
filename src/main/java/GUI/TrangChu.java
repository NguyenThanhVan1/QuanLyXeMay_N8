package GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import DAO.Database;
import DTO.XeMay;

public class TrangChu extends JFrame {
    // Constants
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);  // Màu chủ đạo
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);    // Màu nhấn mạnh
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Màu nền
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final int PADDING = 10;
    private static final int PRODUCTS_PER_PAGE = 9; // Số sản phẩm mỗi trang
    
    // Components
    private JPanel panelXe;
    private JPanel paginationPanel;
    private JTextField txtTen, txtGiaTu, txtGiaDen;
    private JComboBox<String> cboHangXe;
    private JComboBox<String> cboSapXep;
    private JComboBox<String> cboKhoangGia;
    private ArrayList<XeMay> danhSachXe = new ArrayList<>();
    private ArrayList<XeMay> danhSachXeLoc = new ArrayList<>();
    private int currentPage = 1;
    private int totalPages = 1;

    public TrangChu() {
        setupUI();
        loadData();
        addSampleData();
        loadBrands(); // Load hãng xe vào combobox
        danhSachXeLoc = new ArrayList<>(danhSachXe); // Khởi tạo danh sách lọc
        displayProductsForCurrentPage(); // Hiển thị sản phẩm trang đầu tiên
        createPaginationControls(); // Tạo điều khiển phân trang
    }
    
    private void setupUI() {
        // Cài đặt JFrame
        setTitle("Cửa Hàng Xe Máy");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        mainPanel.setBackground(Color.WHITE);
        
        // Panel bên phải (danh sách xe + phân trang)
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(Color.WHITE);
        
        // Panel chứa tiêu đề và bộ sắp xếp
        JPanel headerPanel = createHeaderPanel();
        
        // Panel hiển thị xe (grid)
        panelXe = new JPanel(new GridLayout(3, 3, 15, 15)); // Sửa thành 3 hàng, 3 cột
        panelXe.setBackground(Color.WHITE);
        
        // Panel phân trang
        paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.setBackground(Color.WHITE);
        
        // Thêm vào panel bên phải
        JScrollPane scrollPane = new JScrollPane(panelXe);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        rightPanel.add(headerPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(paginationPanel, BorderLayout.SOUTH);
        
        // Panel tìm kiếm (bên trái)
        JPanel searchPanel = createSearchPanel();
        
        // Chia layout với JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, searchPanel, rightPanel);
        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(1);
        splitPane.setBorder(null);
        
        mainPanel.add(splitPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(0, 0, 10, 0)));
        
        // Tiêu đề
        JLabel titleLabel = new JLabel("Danh sách xe máy");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        
        // Dropdown sắp xếp
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sortPanel.setBackground(Color.WHITE);
        
        JLabel sortLabel = new JLabel("Sắp xếp theo: ");
        sortLabel.setFont(NORMAL_FONT);
        
        String[] sortOptions = {
            "Mặc định", 
            "Giá thấp đến cao", 
            "Giá cao đến thấp", 
            "Tên A-Z", 
            "Tên Z-A"
        };
        
        cboSapXep = new JComboBox<>(sortOptions);
        cboSapXep.setFont(NORMAL_FONT);
        cboSapXep.setPreferredSize(new Dimension(150, 28));
        cboSapXep.addActionListener(e -> sortAndDisplayProducts());
        
        sortPanel.add(sortLabel);
        sortPanel.add(cboSapXep);
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(sortPanel, BorderLayout.EAST);
        
        return panel;
    }

    private JPanel createSearchPanel() {
        // Panel tìm kiếm chính
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        // Panel chứa các control tìm kiếm
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        controlsPanel.setBackground(BACKGROUND_COLOR);
        
        // Tiêu đề
        JLabel titleLabel = new JLabel("BỘ LỌC");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(200, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // == Phần tìm kiếm theo tên ==
        JLabel lblTen = new JLabel("Tên xe:");
        lblTen.setFont(NORMAL_FONT);
        lblTen.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtTen = createStyledTextField();
        
        // == Phần lọc theo danh mục ==
        JLabel lblHangXe = new JLabel("Hãng xe:");
        lblHangXe.setFont(NORMAL_FONT);
        lblHangXe.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // ComboBox sẽ được điền sau khi tải dữ liệu
        cboHangXe = new JComboBox<>();
        cboHangXe.setFont(NORMAL_FONT);
        cboHangXe.setMaximumSize(new Dimension(200, 28));
        cboHangXe.setPreferredSize(new Dimension(200, 28));
        cboHangXe.setAlignmentX(Component.LEFT_ALIGNMENT);
        cboHangXe.addActionListener(e -> applyFilters());
        
        // == Phần lọc theo khoảng giá ==
        JLabel lblKhoangGia = new JLabel("Khoảng giá:");
        lblKhoangGia.setFont(NORMAL_FONT);
        lblKhoangGia.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] priceRanges = {
            "Tất cả giá",
            "Dưới 20 triệu",
            "20 triệu - 30 triệu", 
            "30 triệu - 50 triệu",
            "50 triệu - 80 triệu",
            "Trên 80 triệu"
        };
        
        cboKhoangGia = new JComboBox<>(priceRanges);
        cboKhoangGia.setFont(NORMAL_FONT);
        cboKhoangGia.setMaximumSize(new Dimension(200, 28));
        cboKhoangGia.setPreferredSize(new Dimension(200, 28));
        cboKhoangGia.setAlignmentX(Component.LEFT_ALIGNMENT);
        cboKhoangGia.addActionListener(e -> applyFilters());
        
        // == Phần nhập khoảng giá tùy chỉnh ==
        JLabel lblGiaTu = new JLabel("Giá từ:");
        lblGiaTu.setFont(NORMAL_FONT);
        lblGiaTu.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtGiaTu = createStyledTextField();
        
        JLabel lblGiaDen = new JLabel("Giá đến:");
        lblGiaDen.setFont(NORMAL_FONT);
        lblGiaDen.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtGiaDen = createStyledTextField();
        
        // Nút tìm kiếm
        JButton btnTim = createStyledButton("Áp dụng bộ lọc", PRIMARY_COLOR, Color.WHITE);
        btnTim.addActionListener(e -> applyFilters());
        
        // Nút làm mới
        JButton btnReset = createStyledButton("Làm mới", new Color(189, 195, 199), Color.WHITE);
        btnReset.addActionListener(e -> resetFilters());
        
        // Thêm vào panel controls
        controlsPanel.add(titleLabel);
        controlsPanel.add(separator);
        controlsPanel.add(Box.createVerticalStrut(15));
        
        // Thêm phần tìm theo tên
        controlsPanel.add(lblTen);
        controlsPanel.add(Box.createVerticalStrut(5));
        controlsPanel.add(txtTen);
        controlsPanel.add(Box.createVerticalStrut(15));
        
        // Thêm phần lọc theo hãng
        controlsPanel.add(lblHangXe);
        controlsPanel.add(Box.createVerticalStrut(5));
        controlsPanel.add(cboHangXe);
        controlsPanel.add(Box.createVerticalStrut(15));
        
        // Thêm phần khoảng giá
        controlsPanel.add(lblKhoangGia);
        controlsPanel.add(Box.createVerticalStrut(5));
        controlsPanel.add(cboKhoangGia);
        controlsPanel.add(Box.createVerticalStrut(15));
        
        // Thêm phần giá tùy chỉnh
        controlsPanel.add(lblGiaTu);
        controlsPanel.add(Box.createVerticalStrut(5));
        controlsPanel.add(txtGiaTu);
        controlsPanel.add(Box.createVerticalStrut(10));
        
        controlsPanel.add(lblGiaDen);
        controlsPanel.add(Box.createVerticalStrut(5));
        controlsPanel.add(txtGiaDen);
        controlsPanel.add(Box.createVerticalStrut(15));
        
        // Thêm nút áp dụng và làm mới
        controlsPanel.add(btnTim);
        controlsPanel.add(Box.createVerticalStrut(8));
        controlsPanel.add(btnReset);
        
        // Panel nút điều hướng
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(BACKGROUND_COLOR);
        navPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 0, 0, 0)));
        
        // Các nút điều hướng
        JButton btnXemDonHang = createStyledButton("Đơn hàng", PRIMARY_COLOR, Color.WHITE);
        JButton btnXemLichSu = createStyledButton("Lịch sử mua hàng", PRIMARY_COLOR, Color.WHITE);
        JButton btnDangXuat = createStyledButton("Đăng xuất", ACCENT_COLOR, Color.WHITE);
        
        navPanel.add(btnXemDonHang);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnXemLichSu);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnDangXuat);
        
        // Thêm vào panel chính
        panel.add(controlsPanel, BorderLayout.CENTER);
        panel.add(navPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(200, 28));
        textField.setPreferredSize(new Dimension(200, 28));
        textField.setFont(NORMAL_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        return textField;
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(NORMAL_FONT);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(200, 35));
        button.setPreferredSize(new Dimension(200, 35));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        return button;
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
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createXePanel(XeMay xe) {
        // Panel sản phẩm
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(240, 280)); // Đặt kích thước cố định cho panel xe
        
        // Load ảnh
        ImageIcon icon;
        try {
            // Tải ảnh
            icon = new ImageIcon(getClass().getResource("/images/" + xe.getAnh()));
            // Scale ảnh kích thước nhỏ hơn
            Image img = icon.getImage().getScaledInstance(180, 130, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        } catch (Exception e) {
            icon = new ImageIcon(getClass().getResource("/images/xemay1.png")); // ảnh mặc định
        }
        
        // Panel chứa ảnh
        JLabel imageLabel = new JLabel(icon, JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(0, 130)); // Giảm chiều cao ảnh
        panel.add(imageLabel, BorderLayout.CENTER);
        
        // Panel thông tin
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 8, 8, 8)); // Giảm padding
        
        // Tên xe - giảm font size
        JLabel nameLabel = new JLabel(xe.getTenXe());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        nameLabel.setForeground(new Color(44, 62, 80));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Hãng xe
        JLabel brandLabel = new JLabel(xe.getHangXe());
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        brandLabel.setForeground(new Color(127, 140, 141));
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Giá xe
        JLabel priceLabel = new JLabel(String.format("%,d VNĐ", xe.getGiaBan()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        priceLabel.setForeground(PRIMARY_COLOR);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Thêm vào panel thông tin
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(2)); // Giảm khoảng cách
        infoPanel.add(brandLabel);
        infoPanel.add(Box.createVerticalStrut(3)); // Giảm khoảng cách
        infoPanel.add(priceLabel);
        
        panel.add(infoPanel, BorderLayout.SOUTH);
        
        // Thêm hiệu ứng hover
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChiTietXeDialog(TrangChu.this, xe).setVisible(true);
            }
        });
        
        return panel;
    }

    private void applyFilters() {
        try {
            // Lấy giá trị từ bộ lọc
            String tuKhoa = txtTen.getText().trim().toLowerCase();
            String hangXe = cboHangXe.getSelectedIndex() == 0 ? "" : cboHangXe.getSelectedItem().toString();
            
            // Xử lý khoảng giá từ combobox
            int giaTu = 0;
            int giaDen = Integer.MAX_VALUE;
            
            // Xử lý khoảng giá từ dropdown
            String khoangGia = cboKhoangGia.getSelectedItem().toString();
            if (!khoangGia.equals("Tất cả giá")) {
                switch (khoangGia) {
                    case "Dưới 20 triệu":
                        giaTu = 0;
                        giaDen = 20000000;
                        break;
                    case "20 triệu - 30 triệu":
                        giaTu = 20000000;
                        giaDen = 30000000;
                        break;
                    case "30 triệu - 50 triệu":
                        giaTu = 30000000;
                        giaDen = 50000000;
                        break;
                    case "50 triệu - 80 triệu":
                        giaTu = 50000000;
                        giaDen = 80000000;
                        break;
                    case "Trên 80 triệu":
                        giaTu = 80000000;
                        giaDen = Integer.MAX_VALUE;
                        break;
                }
            }
            
            // Ghi đè các giá trị nếu có nhập giá tùy chỉnh
            if (!txtGiaTu.getText().isEmpty()) {
                giaTu = Integer.parseInt(txtGiaTu.getText().replaceAll("[.,]", ""));
            }
            
            if (!txtGiaDen.getText().isEmpty()) {
                giaDen = Integer.parseInt(txtGiaDen.getText().replaceAll("[.,]", ""));
            }
            
            // Lọc danh sách
            danhSachXeLoc = new ArrayList<>();
            for (XeMay xe : danhSachXe) {
                boolean tenHop = xe.getTenXe().toLowerCase().contains(tuKhoa);
                boolean hangHop = hangXe.isEmpty() || xe.getHangXe().equals(hangXe);
                boolean giaHop = xe.getGiaBan() >= giaTu && xe.getGiaBan() <= giaDen;
                
                if (tenHop && hangHop && giaHop) {
                    danhSachXeLoc.add(xe);
                }
            }
            
            // Sắp xếp kết quả theo cách đã chọn
            sortAndDisplayProducts();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập giá trị số hợp lệ cho giá xe", 
                    "Lỗi định dạng", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void sortAndDisplayProducts() {
        if (danhSachXeLoc == null || danhSachXeLoc.isEmpty()) {
            danhSachXeLoc = new ArrayList<>(danhSachXe);
        }
        
        String sortOption = cboSapXep.getSelectedItem().toString();
        
        switch (sortOption) {
            case "Giá thấp đến cao":
                Collections.sort(danhSachXeLoc, Comparator.comparingInt(XeMay::getGiaBan));
                break;
            case "Giá cao đến thấp":
                Collections.sort(danhSachXeLoc, Comparator.comparingInt(XeMay::getGiaBan).reversed());
                break;
            case "Tên A-Z":
                Collections.sort(danhSachXeLoc, Comparator.comparing(XeMay::getTenXe));
                break;
            case "Tên Z-A":
                Collections.sort(danhSachXeLoc, Comparator.comparing(XeMay::getTenXe).reversed());
                break;
            default: // Mặc định
                // Giữ nguyên thứ tự
                break;
        }
        
        // Hiển thị trang đầu tiên
        currentPage = 1;
        displayProductsForCurrentPage();
        createPaginationControls();
    }

    private void resetFilters() {
        // Reset các control
        txtTen.setText("");
        txtGiaTu.setText("");
        txtGiaDen.setText("");
        cboHangXe.setSelectedIndex(0);
        cboKhoangGia.setSelectedIndex(0);
        cboSapXep.setSelectedIndex(0);
        
        // Reset danh sách lọc về danh sách gốc
        danhSachXeLoc = new ArrayList<>(danhSachXe);
        
        // Hiển thị lại danh sách
        currentPage = 1;
        displayProductsForCurrentPage();
        createPaginationControls();
    }

    private void createPaginationControls() {
        paginationPanel.removeAll();
        
        // Tính tổng số trang
        totalPages = (int) Math.ceil((double) danhSachXeLoc.size() / PRODUCTS_PER_PAGE);
        if (totalPages == 0) totalPages = 1;
        
        // Kiểm tra trang hiện tại hợp lệ
        if (currentPage > totalPages) currentPage = totalPages;
        if (currentPage < 1) currentPage = 1;
        
        // Nút previous
        JButton btnPrev = new JButton("«");
        btnPrev.setEnabled(currentPage > 1);
        btnPrev.addActionListener(e -> {
            currentPage--;
            displayProductsForCurrentPage();
            createPaginationControls();
        });
        
        // Hiển thị số trang
        JLabel lblPageInfo = new JLabel(String.format("Trang %d/%d", currentPage, totalPages));
        lblPageInfo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        
        // Nút next
        JButton btnNext = new JButton("»");
        btnNext.setEnabled(currentPage < totalPages);
        btnNext.addActionListener(e -> {
            currentPage++;
            displayProductsForCurrentPage();
            createPaginationControls();
        });
        
        // Thêm vào panel
        paginationPanel.add(btnPrev);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNext);
        
        paginationPanel.revalidate();
        paginationPanel.repaint();
    }

    private void displayProductsForCurrentPage() {
        panelXe.removeAll();
        
        int startIdx = (currentPage - 1) * PRODUCTS_PER_PAGE;
        int endIdx = Math.min(startIdx + PRODUCTS_PER_PAGE, danhSachXeLoc.size());
        
        // Nếu không có sản phẩm nào, hiển thị thông báo
        if (danhSachXeLoc.isEmpty()) {
            JLabel lblNoProduct = new JLabel("Không tìm thấy sản phẩm nào phù hợp");
            lblNoProduct.setFont(TITLE_FONT);
            lblNoProduct.setForeground(Color.GRAY);
            panelXe.add(lblNoProduct);
        } else {
            // Hiển thị các sản phẩm trong trang hiện tại
            for (int i = startIdx; i < endIdx; i++) {
                panelXe.add(createXePanel(danhSachXeLoc.get(i)));
            }
        }
        
        panelXe.revalidate();
        panelXe.repaint();
    }

    private void loadBrands() {
        // Xóa items cũ
        cboHangXe.removeAllItems();
        
        // Thêm mục "Tất cả"
        cboHangXe.addItem("Tất cả hãng xe");
        
        // Lấy danh sách hãng xe không trùng lặp
        Set<String> brandSet = new HashSet<>();
        for (XeMay xe : danhSachXe) {
            brandSet.add(xe.getHangXe());
        }
        
        // Thêm vào combobox
        for (String brand : brandSet) {
            cboHangXe.addItem(brand);
        }
    }

    class ChiTietXeDialog extends JDialog {
        private XeMay xe;
        private JSpinner spinnerSoLuong;
        private JLabel lblTongTien;
        
        public ChiTietXeDialog(Frame owner, XeMay xe) {
            super(owner, "Chi tiết xe máy", true);
            this.xe = xe;
            setupUI();
        }
        
        private void setupUI() {
            // Thiết lập cơ bản
            setSize(800, 500);
            setLocationRelativeTo(getOwner());
            setResizable(false);
            
            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout(20, 0));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(Color.WHITE);
            
            // Panel hình ảnh bên trái
            JPanel imagePanel = createImagePanel();
            
            // Panel thông tin bên phải
            JPanel infoPanel = createInfoPanel();
            
                        // Thêm vào panel chính
            mainPanel.add(imagePanel, BorderLayout.WEST);
            mainPanel.add(infoPanel, BorderLayout.CENTER);
            
            // Panel nút ở phía dưới
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(Color.WHITE);
            
            // Nút đóng
            JButton btnClose = createStyledButton("Đóng", new Color(189, 195, 199), Color.WHITE);
            btnClose.addActionListener(e -> dispose());
            
            // Nút thêm vào giỏ hàng
            JButton btnAddToCart = createStyledButton("Thêm vào giỏ hàng", PRIMARY_COLOR, Color.WHITE);
            btnAddToCart.addActionListener(e -> addToCart());
            
            buttonPanel.add(btnClose);
            buttonPanel.add(Box.createHorizontalStrut(10));
            buttonPanel.add(btnAddToCart);
            
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            add(mainPanel);
        }
        
        private JPanel createImagePanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.WHITE);
            panel.setPreferredSize(new Dimension(350, 0));
            
            // Load ảnh
            ImageIcon icon;
            try {
                icon = new ImageIcon(getClass().getResource("/images/" + xe.getAnh()));
                Image img = icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            } catch (Exception e) {
                icon = new ImageIcon(getClass().getResource("/images/xemay1.png")); // ảnh mặc định
            }
            
            JLabel imageLabel = new JLabel(icon, JLabel.CENTER);
            panel.add(imageLabel, BorderLayout.CENTER);
            
            return panel;
        }
        
        private JPanel createInfoPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);
            
            // Tên xe
            JLabel lblTenXe = new JLabel(xe.getTenXe());
            lblTenXe.setFont(new Font("Arial", Font.BOLD, 20));
            lblTenXe.setForeground(new Color(44, 62, 80));
            lblTenXe.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // Hãng xe
            JLabel lblHangXe = new JLabel("Hãng: " + xe.getHangXe());
            lblHangXe.setFont(NORMAL_FONT);
            lblHangXe.setForeground(new Color(127, 140, 141));
            lblHangXe.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // Giá xe
            JLabel lblGia = new JLabel("Giá: " + String.format("%,d VNĐ", xe.getGiaBan()));
            lblGia.setFont(new Font("Arial", Font.BOLD, 16));
            lblGia.setForeground(PRIMARY_COLOR);
            lblGia.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // Số lượng còn
            JLabel lblSoLuong = new JLabel("Số lượng còn: " + xe.getSoLuong());
            lblSoLuong.setFont(NORMAL_FONT);
            lblSoLuong.setForeground(new Color(127, 140, 141));
            lblSoLuong.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // Separator
            JSeparator separator = new JSeparator();
            separator.setMaximumSize(new Dimension(Short.MAX_VALUE, 1));
            separator.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // Label chọn số lượng
            JLabel lblChonSL = new JLabel("Chọn số lượng:");
            lblChonSL.setFont(NORMAL_FONT);
            lblChonSL.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // Spinner số lượng
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, xe.getSoLuong(), 1);
            spinnerSoLuong = new JSpinner(spinnerModel);
            spinnerSoLuong.setMaximumSize(new Dimension(100, 30));
            spinnerSoLuong.setAlignmentX(Component.LEFT_ALIGNMENT);
            spinnerSoLuong.addChangeListener(e -> updateTotalPrice());
            
            // Tổng tiền
            JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            totalPanel.setBackground(Color.WHITE);
            totalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel lblTotalText = new JLabel("Tổng tiền:");
            lblTotalText.setFont(new Font("Arial", Font.BOLD, 14));
            
            lblTongTien = new JLabel(String.format("%,d VNĐ", xe.getGiaBan()));
            lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
            lblTongTien.setForeground(ACCENT_COLOR);
            
            totalPanel.add(lblTotalText);
            totalPanel.add(lblTongTien);
            
            // Thêm các thành phần vào panel
            panel.add(lblTenXe);
            panel.add(Box.createVerticalStrut(10));
            panel.add(lblHangXe);
            panel.add(Box.createVerticalStrut(5));
            panel.add(lblGia);
            panel.add(Box.createVerticalStrut(5));
            panel.add(lblSoLuong);
            panel.add(Box.createVerticalStrut(15));
            panel.add(separator);
            panel.add(Box.createVerticalStrut(15));
            panel.add(lblChonSL);
            panel.add(Box.createVerticalStrut(5));
            panel.add(spinnerSoLuong);
            panel.add(Box.createVerticalStrut(15));
            panel.add(totalPanel);
            
            return panel;
        }
        
        private void updateTotalPrice() {
            int quantity = (int) spinnerSoLuong.getValue();
            int total = quantity * xe.getGiaBan();
            lblTongTien.setText(String.format("%,d VNĐ", total));
        }
        
        private void addToCart() {
            int quantity = (int) spinnerSoLuong.getValue();
            
            // Kiểm tra số lượng hợp lệ
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, 
                        "Số lượng phải lớn hơn 0", 
                        "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (quantity > xe.getSoLuong()) {
                JOptionPane.showMessageDialog(this, 
                        "Số lượng trong kho không đủ", 
                        "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Thêm vào giỏ hàng (cần implement logic thêm vào giỏ hàng)
            // Ví dụ:
            // CartManager.addToCart(xe, quantity);
            
            JOptionPane.showMessageDialog(this, 
                    "Đã thêm " + quantity + " " + xe.getTenXe() + " vào giỏ hàng", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
        }
        
        private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
            JButton button = new JButton(text);
            button.setFont(NORMAL_FONT);
            button.setBackground(bgColor);
            button.setForeground(fgColor);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension(120, 35));
            return button;
        }
    }

    /**
     * Thêm dữ liệu mẫu vào danh sách xe
     */
    private void addSampleData() {
        // Chỉ thêm dữ liệu nếu danh sách xe trống hoặc ít hơn 9 xe
        if (danhSachXe.size() >= 9) {
            return;
        }
        
        // Mảng dữ liệu mẫu: mã, tên, hãng, giá, số lượng, ảnh
        Object[][] sampleData = {
            {"XM001", "Honda Wave Alpha", "Honda", 17800000, 15, "xemay1.png"},
            {"XM002", "Yamaha Exciter 155", "Yamaha", 49900000, 10, "xemay2.png"},
            {"XM003", "Suzuki Raider R150", "Suzuki", 49990000, 8, "xemay3.png"},
            {"XM004", "Honda SH 150i", "Honda", 84990000, 12, "xemay4.png"},
            {"XM005", "Yamaha NVX 155", "Yamaha", 52690000, 7, "xemay5.png"},
            {"XM006", "Honda Vision", "Honda", 31690000, 20, "xemay6.png"},
            {"XM007", "Piaggio Liberty", "Piaggio", 57900000, 5, "xemay7.png"},
            {"XM008", "Honda Air Blade", "Honda", 41190000, 15, "xemay8.png"},
            {"XM009", "Yamaha Grande", "Yamaha", 49900000, 8, "xemay9.png"},
            {"XM010", "Honda Lead", "Honda", 39990000, 12, "xemay10.png"},
            {"XM011", "Vespa Primavera", "Piaggio", 77500000, 6, "xemay11.png"},
            {"XM012", "Yamaha Sirius", "Yamaha", 21100000, 18, "xemay12.png"}
        };
        
        // Kiểm tra từng mã xe trong danh sách mẫu
        Set<String> existingIds = new HashSet<>();
        for (XeMay xe : danhSachXe) {
            existingIds.add(xe.getMaXe());
        }
        
        // Thêm mẫu xe nếu chưa tồn tại
        for (Object[] data : sampleData) {
            if (!existingIds.contains(data[0])) {
                XeMay xe = new XeMay(
                    (String) data[0],
                    (String) data[1],
                    (String) data[2],
                    (int) data[3],
                    (int) data[4],
                    (String) data[5]
                );
                danhSachXe.add(xe);
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrangChu trangChu = new TrangChu();
            trangChu.setVisible(true);
        });
    }
}