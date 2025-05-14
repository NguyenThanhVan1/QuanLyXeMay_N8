package GUI.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BUS.ShoppingCartsBUS;
import BUS.UsersBUS;
import DTO.OrdersDTO;
import DTO.ProductsDTO;
import DTO.ShoppingCartsDTO;
import DTO.UsersDTO;
import GUI.IdCurrentUser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GioHangPanel extends JPanel {
    private JTable tblGioHang;
    private DefaultTableModel modelGioHang;
    private JLabel lblTongTien;
    private MainFrame mainFrame;
    
    private List<ProductsDTO> danhSachSanPhamTrongGio;
    private List<ShoppingCartsDTO> shoppingCarts;
    
    private ShoppingCartsBUS shoppingCartsBUS;
    
    public GioHangPanel(MainFrame mainFrame) {
        System.out.println("GioHangPanel");
        
        
       
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        
        
        // Panel thông tin thanh toán
        JPanel panelThanhToan = createPaymentPanel();
        add(panelThanhToan, BorderLayout.SOUTH);

        // Panel hiển thị giỏ hàng
        JPanel panelGioHang = createCartPanel();
        add(panelGioHang, BorderLayout.CENTER);
    }
    
    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Giỏ hàng của bạn"));
        
        // Tạo model và table
        String[] columns = {"Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền", ""};
        modelGioHang = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 5; // Chỉ cho phép sửa cột số lượng và cột xóa
            }
        };
        
        tblGioHang = new JTable(modelGioHang);
        tblGioHang.getTableHeader().setReorderingAllowed(false);
        tblGioHang.setRowHeight(40);
        
        // Thiết lập renderer cho cột button xóa
        tblGioHang.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("Xóa"));
        tblGioHang.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "Xóa"));
        
        // Thiết lập kích thước cột
        tblGioHang.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblGioHang.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblGioHang.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblGioHang.getColumnModel().getColumn(3).setPreferredWidth(60);
        tblGioHang.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblGioHang.getColumnModel().getColumn(5).setPreferredWidth(60);
        
        JScrollPane scrollPane = new JScrollPane(tblGioHang);
        panel.add(scrollPane, BorderLayout.CENTER);
        updateGioHang();
        
        return panel;
    }
    
    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin thanh toán"));
        
        JPanel panelInfo = new JPanel(new GridLayout(3, 1, 5, 5));
        panelInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        lblTongTien = new JLabel("Tổng tiền: 0 VND");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfo.add(lblTongTien);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnThanhToan = new JButton("Tiến hành thanh toán");
        btnThanhToan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (danhSachSanPhamTrongGio.isEmpty()) {
                    JOptionPane.showMessageDialog(GioHangPanel.this, 
                        "Giỏ hàng của bạn đang trống. Vui lòng thêm sản phẩm!", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                BigDecimal tongTien = BigDecimal.ZERO;
                for (ProductsDTO sp : danhSachSanPhamTrongGio) {
                    BigDecimal thanhTien = sp.getPrice().multiply(BigDecimal.valueOf(sp.getQuantity()));
                    tongTien = tongTien.add(thanhTien);
                }
                // Hiển thị dialog thanh toán
                ThanhToanDialog dialog = new ThanhToanDialog((Frame) SwingUtilities.getWindowAncestor(GioHangPanel.this), 
                                                        danhSachSanPhamTrongGio, 
                                                        tongTien);
                dialog.setVisible(true);
                
            }
        });
        
       
        

        buttonPanel.add(btnThanhToan);
        
        panel.add(panelInfo, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    public void updateGioHang() {
        // System.out.println(this.danhSachSanPhamTrongGio);
        this.shoppingCartsBUS = new ShoppingCartsBUS();
        IdCurrentUser idCurrentUser = new IdCurrentUser();
        idCurrentUser.setCurrentUserId("1");
        // System.out.println("IdCurrentUser: " + idCurrentUser.getCurrentUserId());
        this.shoppingCarts = this.shoppingCartsBUS.getByIdCustomer(idCurrentUser.getCurrentUserId());
        System.out.println("ShoppingCarts: " + this.shoppingCarts);
        this.danhSachSanPhamTrongGio = this.shoppingCartsBUS.getByShoppingCart(idCurrentUser.getCurrentUserId());
        // Xóa dữ liệu cũ
        modelGioHang.setRowCount(0);
        BigDecimal tongTien = BigDecimal.ZERO;
        // Thêm dữ liệu mới
        for (int i = 0; i < danhSachSanPhamTrongGio.size(); i++) {
            ProductsDTO sp = danhSachSanPhamTrongGio.get(i);
            BigDecimal thanhTien = sp.getPrice().multiply(BigDecimal.valueOf(sp.getQuantity()));
            tongTien = tongTien.add(thanhTien);
            modelGioHang.addRow(new Object[]{
                sp.getProductId(),
                sp.getProductName(),
                String.format("%,f", sp.getPrice()),
                sp.getQuantity(),
                String.format("%,f", thanhTien),
                "Xóa"
            });
        }
        System.out.println("Tong tien: " + tongTien);
        // Cập nhật tổng tiền
        // this.lblTongTien = new JLabel();
        lblTongTien.setText("Tổng tiền: " + String.format("%,f", tongTien) + " VND");
        this.revalidate();
        this.repaint();
    }
    
    public static void themVaoGioHang(ProductsDTO sp) {
        boolean daCoTrongGio = false;
        
        for (int i = 0; i < danhSachSanPhamTrongGio.size(); i++) {
            if (danhSachSanPhamTrongGio.get(i).getMa().equals(sp.getMa())) {
                danhSachSoLuong.set(i, danhSachSoLuong.get(i) + sp.getSoLuong());
                daCoTrongGio = true;
                break;
            }
        }
        
        if (!daCoTrongGio) {
            danhSachSanPhamTrongGio.add(sp);
            danhSachSoLuong.add(sp.getSoLuong());
        }
    }
    
    // Class ButtonRenderer để hiển thị nút trong bảng
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setOpaque(true);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    
    // Class ButtonEditor để xử lý sự kiện khi nhấn nút
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        
        public ButtonEditor(JCheckBox checkBox, String text) {
            super(checkBox);
            button = new JButton(text);
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Xử lý khi nhấn nút "Xóa"
                int row = tblGioHang.getSelectedRow();
                if (row >= 0) {
                    // Xóa sản phẩm khỏi danh sách
                    danhSachSanPhamTrongGio.remove(row);
                    danhSachSoLuong.remove(row);
                    
                    // Cập nhật lại giỏ hàng
                    updateGioHang();
                    JOptionPane.showMessageDialog(GioHangPanel.this, 
                                                 "Đã xóa sản phẩm khỏi giỏ hàng!",
                                                 "Thông báo", 
                                                 JOptionPane.INFORMATION_MESSAGE);
                }
            }
            isPushed = false;
            return label;
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}