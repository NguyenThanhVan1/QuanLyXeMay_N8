package GUI.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.math3.stat.descriptive.summary.Product;

import BUS.ProductsBUS;
import DTO.ProductsDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamPanel extends JPanel {
    private JTextField txtTimKiem;
    private JComboBox<String> cboLoai;
    private JComboBox<String> cboGia;
    private List<ProductsDTO> danhSachSanPham;
    private PhanTrangPanel phanTrangPanel;
    private MainFrame mainFrame;
    private JPanel productGridPanel;

    private ProductsBUS productsBUS;
    
    private final int ITEMS_PER_ROW = 4;
    private final int ROWS_PER_PAGE = 2;
    private final int ITEMS_PER_PAGE = ITEMS_PER_ROW * ROWS_PER_PAGE;
    int currentPage = 1;
    int totalPages = 1;
    
    // Color palette
    final Color PRIMARY_COLOR = new Color(40, 167, 69);
    private final Color SECONDARY_COLOR = new Color(33, 37, 41);
    final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private final Color CARD_COLOR = Color.WHITE;
    final Color TEXT_COLOR = new Color(73, 80, 87);
    private final Color BORDER_COLOR = new Color(222, 226, 230);
    
    public SanPhamPanel(MainFrame mainFrame) {
        this.productsBUS = new ProductsBUS();
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(0, 0));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Reduced border padding for more space
        
        // Initialize sample data
        // taoDataMau();

        // Left panel with search and filter
        JPanel leftPanel = new JPanel(new BorderLayout(0, 5)); // Added small gap
        leftPanel.setBackground(BACKGROUND_COLOR);
        
        // Top left for search
        JPanel topLeft = new JPanel(new BorderLayout());
        topLeft.setBackground(BACKGROUND_COLOR);
        topLeft.setBorder(null);
        
        // Bottom left for filters
        JPanel bottomLeft = new JPanel(new BorderLayout());
        bottomLeft.setBackground(BACKGROUND_COLOR);
        bottomLeft.setBorder(null);

        // Search panel
        JPanel searchPanel = createSearchPanel();
        topLeft.add(searchPanel, BorderLayout.NORTH);
        
        // Filter panel
        JPanel filterPanel = createFilterPanel();
        bottomLeft.add(filterPanel, BorderLayout.NORTH);

        leftPanel.add(topLeft, BorderLayout.NORTH);
        leftPanel.add(bottomLeft, BorderLayout.CENTER);
        
        // Create product display area
        JPanel productPanel = createProductGridPanel();
        

        // Create pagination panel
        phanTrangPanel = new PhanTrangPanel(this);

        // Right panel for product display
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BACKGROUND_COLOR);
        rightPanel.add(productPanel, BorderLayout.CENTER);
        rightPanel.add(phanTrangPanel, BorderLayout.SOUTH);

        // Create the split pane
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        mainSplit.setResizeWeight(0.3); // Left takes 30% of space
        mainSplit.setDividerSize(3); // Thinner divider
        mainSplit.setBorder(null); // Remove border
        mainSplit.setContinuousLayout(true); // Smoother resizing

        add(mainSplit, BorderLayout.CENTER);
        
        // Show first page
        showPage(1);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 5));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                "Tìm kiếm",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 12),
                PRIMARY_COLOR
            ),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Search field with icon
        JPanel searchFieldPanel = new JPanel(new BorderLayout(5, 0));
        searchFieldPanel.setBackground(CARD_COLOR);
        searchFieldPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(2, 8, 2, 2) // Reduced padding
        ));
        
        txtTimKiem = new JTextField();
        txtTimKiem.setBorder(BorderFactory.createEmptyBorder());
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Smaller font
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Nhập từ khóa tìm kiếm...");
        
        // Tạo nút tùy chỉnh có màu nền
        JButton btnSearch = new JButton("Tìm kiếm") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 120, 215));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSearch.setBorderPainted(false);
        btnSearch.setContentAreaFilled(false); // Tắt tô màu vùng nội dung mặc định
        btnSearch.setMaximumSize(new Dimension(150, 40));
        btnSearch.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnSearch.setFocusPainted(false); 
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(e -> timKiemSanPham());
        
        // Add key listener to search on Enter key
        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemSanPham();
                }
            }
        });
        
        searchFieldPanel.add(txtTimKiem, BorderLayout.CENTER);
        searchFieldPanel.add(btnSearch, BorderLayout.EAST);
        
        panel.add(searchFieldPanel, BorderLayout.CENTER);
        return panel;
    }

    
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                "Bộ lọc sản phẩm",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(0, 120, 215)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Tăng padding
        ));
        
        // Title no longer needed since we have titled border
        
        // Sort filter
        JPanel sortPanel = createFilterSection("Sắp xếp theo", new String[]{"Mặc định", "Giá tăng dần", "Giá giảm dần"});
        panel.add(sortPanel);
        
        // Category filter
        JPanel categoryPanel = createFilterSection("Loại sản phẩm", new String[]{"Tất cả", "Điện thoại", "Laptop", "Tablet", "Phụ kiện"});
        panel.add(categoryPanel);
        
        // Price range filter
        JPanel pricePanel = createFilterSection("Khoảng giá", new String[]{"Tất cả", "Dưới 5 triệu", "5-10 triệu", "10-20 triệu", "Trên 20 triệu"});
        panel.add(pricePanel);
        
        // Tạo nút tùy chỉnh có màu nền
        JButton btnApply = new JButton("Lọc") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 120, 215));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnApply.setForeground(Color.WHITE);
        btnApply.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnApply.setBorderPainted(false);
        btnApply.setContentAreaFilled(false); // Tắt tô màu vùng nội dung mặc định
        btnApply.setMaximumSize(new Dimension(150, 40));
        btnApply.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnApply.setFocusPainted(false); 
        btnApply.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnApply.addActionListener(e -> timKiemSanPham());

        // Tạo panel chứa nút để căn giữa
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(Box.createHorizontalGlue()); // Thêm khoảng trống co giãn bên trái
        buttonPanel.add(btnApply);
        buttonPanel.add(Box.createHorizontalGlue()); // Thêm khoảng trống co giãn bên phải

        panel.add(Box.createVerticalStrut(12)); // Tăng khoảng cách trước panel chứa nút
        panel.add(buttonPanel);
        
        return panel;
    }

    private JPanel createFilterSection(String title, String[] options) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 12, 0)); // Tăng khoảng cách giữa các phần
        
        // Section title
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Tăng kích thước font
        lblTitle.setForeground(TEXT_COLOR);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0)); // Tăng khoảng cách dưới tiêu đề
        panel.add(lblTitle);
        
        // Combo box
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Tăng kích thước font
        comboBox.setBackground(Color.WHITE);
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36)); // Tăng chiều cao combobox
        comboBox.setPreferredSize(new Dimension(120, 36)); // Thiết lập kích thước mặc định
        
        // Tùy chỉnh renderer để hiển thị text rõ hơn
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Thêm padding
                return label;
            }
        });
        
        if (title.equals("Loại sản phẩm")) {
            cboLoai = comboBox;
        } else if (title.equals("Khoảng giá")) {
            cboGia = comboBox;
        }
        
        panel.add(comboBox);
        
        return panel;
    }

    private JPanel createProductGridPanel() {
        // Sử dụng GridBagLayout thay vì GridLayout để kiểm soát vị trí các sản phẩm tốt hơn
        productGridPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        productGridPanel.setBackground(BACKGROUND_COLOR);
        
        // Create a wrapper panel with padding
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(BACKGROUND_COLOR);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        wrapperPanel.add(productGridPanel, BorderLayout.NORTH);
        
        // Add empty row at the bottom to prevent uneven resizing
        JPanel emptySpace = new JPanel();
        emptySpace.setBackground(BACKGROUND_COLOR);
        wrapperPanel.add(emptySpace, BorderLayout.CENTER);
        
        return wrapperPanel;
    }

    private JPanel createProductCard(ProductsDTO product) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Reduced padding
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(120, 300)); // Điều chỉnh số này để phù hợp

        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }
        });
        
        // Product image placeholder - smaller
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(100, 150)); // Reduced size
        imagePanel.setBackground(getColorForProductType(product.getBrand()));
        imagePanel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        
        // Product info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(CARD_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Less top space
        
        // Product name
        JLabel nameLabel = new JLabel(product.getProductName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Smaller font
        nameLabel.setForeground(SECONDARY_COLOR);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Product price
        JLabel priceLabel = new JLabel(String.format("%,f VND", product.getPrice()));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Smaller font
        priceLabel.setForeground(PRIMARY_COLOR);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 4, 0)); // Less space
        
        // Product category
        JLabel categoryLabel = new JLabel(product.getBrand());
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11)); // Smaller font
        categoryLabel.setForeground(TEXT_COLOR);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Tạo nút tùy chỉnh có màu nền
        JButton addButton = new JButton("Xem chi tiết") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 120, 215));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false); // Tắt tô màu vùng nội dung mặc định
        addButton.setMaximumSize(new Dimension(150, 40));
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        addButton.setFocusPainted(false); 
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> handleShowDetail(product));

        // Tạo panel chứa nút để căn giữa
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(Box.createHorizontalGlue()); // Thêm khoảng trống co giãn bên trái
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createHorizontalGlue()); 
        
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(Box.createVerticalStrut(8)); // Less space
        infoPanel.add(buttonPanel);
        
        card.add(imagePanel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void addToCart(ProductsDTO sp) {
        String soLuongStr = JOptionPane.showInputDialog(mainFrame, 
                "Nhập số lượng sản phẩm:", 
                "Thêm vào giỏ hàng", 
                JOptionPane.QUESTION_MESSAGE);
        
        if (soLuongStr != null && !soLuongStr.isEmpty()) {
            try {
                int soLuong = Integer.parseInt(soLuongStr);
                if (soLuong > 0) {
                    // Add to cart logic here
                    JOptionPane.showMessageDialog(mainFrame, 
                            "Đã thêm " + soLuong + " " + sp.getTen() + " vào giỏ hàng!",
                            "Thành công", 
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, 
                            "Số lượng phải lớn hơn 0!",
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, 
                        "Vui lòng nhập số lượng hợp lệ!",
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private Color getColorForProductType(String loai) {
        switch (loai) {
            case "Điện thoại": return new Color(100, 181, 246); // Light Blue
            case "Laptop": return new Color(129, 199, 132); // Light Green
            case "Tablet": return new Color(255, 183, 77);  // Orange
            case "Phụ kiện": return new Color(240, 98, 146);  // Pink
            default: return new Color(189, 189, 189); // Gray
        }
    }
    
    private void taoDataMau() {
        danhSachSanPham = new ArrayList<>();
        danhSachSanPham.add(new ProductsDTO("SP001", "iPhone 15 Pro Max", "Điện thoại", 32990000, 10));
        danhSachSanPham.add(new ProductsDTO("SP002", "Samsung Galaxy S24 Ultra", "Điện thoại", 28990000, 8));
        danhSachSanPham.add(new ProductsDTO("SP003", "Xiaomi 14 Pro", "Điện thoại", 19990000, 15));
        danhSachSanPham.add(new ProductsDTO("SP004", "MacBook Pro M3", "Laptop", 45990000, 7));
        danhSachSanPham.add(new ProductsDTO("SP005", "Dell XPS 15", "Laptop", 38990000, 5));
        danhSachSanPham.add(new ProductsDTO("SP006", "Lenovo ThinkPad X1", "Laptop", 32990000, 12));
        danhSachSanPham.add(new ProductsDTO("SP007", "iPad Pro M2", "Tablet", 23990000, 10));
        danhSachSanPham.add(new ProductsDTO("SP008", "Samsung Galaxy Tab S9", "Tablet", 19990000, 8));
        danhSachSanPham.add(new ProductsDTO("SP009", "AirPods Pro 2", "Phụ kiện", 4990000, 25));
        danhSachSanPham.add(new ProductsDTO("SP010", "Apple Watch Series 9", "Phụ kiện", 10990000, 15));
        danhSachSanPham.add(new ProductsDTO("SP011", "Samsung Galaxy Buds 3", "Phụ kiện", 3990000, 20));
        danhSachSanPham.add(new ProductsDTO("SP012", "Laptop Gaming Asus ROG", "Laptop", 32990000, 8));
        danhSachSanPham.add(new ProductsDTO("SP013", "Xiaomi Pad 6", "Tablet", 7990000, 12));
        danhSachSanPham.add(new ProductsDTO("SP014", "Sony WH-1000XM5", "Phụ kiện", 8990000, 10));
        danhSachSanPham.add(new ProductsDTO("SP015", "Google Pixel 8 Pro", "Điện thoại", 25990000, 7));
    }
    
    private void timKiemSanPham() {
        showPage(1);
    }
    
    public void showPage(int page) {
        this.currentPage = page;
        
        // Get filtered products
        // List<SanPham> filteredList = getFilteredProducts();

        List<ProductsDTO> products = this.productsBUS.getAll();
        
        // Clear current products
        productGridPanel.removeAll();
        
        // Calculate pagination
        int startIndex = (page - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, products.size());
        
        // Add products to grid
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Khoảng cách giữa các ô
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.25; // Đảm bảo mỗi cột chiếm 1/4 không gian ngang
        gbc.weighty = 1.0;
        
        int row = 0;
        int col = 0;
        
        for (int i = startIndex; i < endIndex; i++) {
            gbc.gridx = col;
            gbc.gridy = row;
            
            // Đảm bảo mỗi sản phẩm chỉ chiếm đúng 1 ô trong lưới
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            
            JPanel card = createProductCard(products.get(i));
            productGridPanel.add(card, gbc);
            
            // Tăng chỉ số cột
            col++;
            // Nếu đã đủ số cột trong một hàng, chuyển xuống hàng tiếp theo
            if (col >= ITEMS_PER_ROW) {
                col = 0;
                row++;
            }
        }
        
        // Calculate total pages
        totalPages = (int) Math.ceil((double) products.size() / ITEMS_PER_PAGE);
        if (totalPages == 0) totalPages = 1;
        
        // Update pagination panel
        phanTrangPanel.updatePageInfo(page, totalPages);
        
        // Refresh UI
        productGridPanel.revalidate();
        productGridPanel.repaint();
    }

    private List<ProductsDTO> getFilteredProducts() {
        String keyword = txtTimKiem.getText().toLowerCase();
        String loai = (String) cboLoai.getSelectedItem();
        String giaOption = (String) cboGia.getSelectedItem();
        
        List<ProductsDTO> filteredList = new ArrayList<>();
        
        for (ProductsDTO sp : danhSachSanPham) {
            // Filter by keyword
            boolean matchKeyword = keyword.isEmpty() || 
                                 sp.getTen().toLowerCase().contains(keyword) || 
                                 sp.getMa().toLowerCase().contains(keyword);
            
            // Filter by category
            boolean matchLoai = loai.equals("Tất cả") || sp.getLoai().equals(loai);
            
            // Filter by price
            boolean matchGia = giaOption.equals("Tất cả") || checkPriceRange(sp.getGia(), giaOption);
            
            if (matchKeyword && matchLoai && matchGia) {
                filteredList.add(sp);
            }
        }
        
        return filteredList;
    }
    
    private boolean checkPriceRange(long gia, String giaOption) {
        switch (giaOption) {
            case "Dưới 5 triệu": return gia < 5000000;
            case "5-10 triệu": return gia >= 5000000 && gia <= 10000000;
            case "10-20 triệu": return gia > 10000000 && gia <= 20000000;
            case "Trên 20 triệu": return gia > 20000000;
            default: return true;
        }
    }

    private void handleShowDetail(ProductsDTO sp) {
        ProductDetailDialog dialog = new ProductDetailDialog((Frame) SwingUtilities.getWindowAncestor(this), sp);
        dialog.setVisible(true);
    }

}

class PhanTrangPanel extends JPanel {
    private JButton btnFirst, btnPrev, btnNext, btnLast;
    private JLabel lblPageInfo;
    private SanPhamPanel sanPhamPanel;
    
    public PhanTrangPanel(SanPhamPanel sanPhamPanel) {
        this.sanPhamPanel = sanPhamPanel;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(16, 0, 0, 0));
        setBackground(sanPhamPanel.BACKGROUND_COLOR);
        
        JPanel paginationPanel = new JPanel();
        paginationPanel.setLayout(new BoxLayout(paginationPanel, BoxLayout.X_AXIS));
        paginationPanel.setBackground(sanPhamPanel.BACKGROUND_COLOR);
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        // First page button
        btnFirst = createPaginationButton("<<", sanPhamPanel.PRIMARY_COLOR);
        btnFirst.addActionListener(e -> sanPhamPanel.showPage(1));
        
        // Previous page button
        btnPrev = createPaginationButton("<", sanPhamPanel.PRIMARY_COLOR);
        btnPrev.addActionListener(e -> sanPhamPanel.showPage(Math.max(1, sanPhamPanel.currentPage - 1)));
        
        // Page info label
        lblPageInfo = new JLabel("Trang 1/1");
        lblPageInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPageInfo.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        lblPageInfo.setForeground(sanPhamPanel.TEXT_COLOR);
        
        // Next page button
        btnNext = createPaginationButton(">", sanPhamPanel.PRIMARY_COLOR);
        btnNext.addActionListener(e -> sanPhamPanel.showPage(Math.min(sanPhamPanel.totalPages, sanPhamPanel.currentPage + 1)));
        
        // Last page button
        btnLast = createPaginationButton(">>", sanPhamPanel.PRIMARY_COLOR);
        btnLast.addActionListener(e -> sanPhamPanel.showPage(sanPhamPanel.totalPages));
        
        paginationPanel.add(Box.createHorizontalGlue());
        paginationPanel.add(btnFirst);
        paginationPanel.add(Box.createHorizontalStrut(8));
        paginationPanel.add(btnPrev);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNext);
        paginationPanel.add(Box.createHorizontalStrut(8));
        paginationPanel.add(btnLast);
        paginationPanel.add(Box.createHorizontalGlue());
        
        add(paginationPanel, BorderLayout.CENTER);
    }
    
    private JButton createPaginationButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    public void updatePageInfo(int currentPage, int totalPages) {
        lblPageInfo.setText(String.format("Trang %d/%d", currentPage, totalPages));
        
        btnFirst.setEnabled(currentPage > 1);
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
        btnLast.setEnabled(currentPage < totalPages);
    }
}