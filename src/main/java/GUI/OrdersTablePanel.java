package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;

import BUS.DetailOrdersBUS;
import BUS.OrdersBUS;
import DTO.DetailOrderDTO;
import DTO.OrderDTO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class OrdersTablePanel extends JPanel {
    private static OrdersTablePanel instance;
    private Orders parentPanel;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private OrdersBUS ordersBUS = new OrdersBUS();

    public OrdersTablePanel(Orders parentPanel) {
        this.parentPanel = parentPanel;
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250)); // Màu nền tổng thể
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding xung quanh

        // Tạo bảng với các cột
        String[] columns = {"Mã Đơn Hàng", "Ngày Lập", "Khách Hàng", "Địa Chỉ", "Tổng Tiền", "Trạng Thái", "", ""};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };

        ordersTable = new JTable(tableModel);
        ordersTable.setRowHeight(30);
        ordersTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ordersTable.setBackground(Color.WHITE); // Nền trắng cho các ô
        ordersTable.setForeground(Color.BLACK); // Chữ màu đen
        ordersTable.setGridColor(new Color(222, 226, 230)); // Đường viền bảng

        // Đặt màu nền mặc định cho các ô không phải nút
        ordersTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa nội dung
                if (!isSelected) {
                    c.setBackground(Color.WHITE); // Màu trắng
                    c.setForeground(Color.BLACK); // Màu đen
                }
                return c;
            }
        });

        // Tùy chỉnh header của bảng
        JTableHeader header = ordersTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.WHITE); // Bootstrap primary
        header.setForeground(Color.BLACK); // Chữ màu trắng cho header

        // Tùy chỉnh renderer cho cột "Chi Tiết" và "Cập Nhật"
        ordersTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Chi Tiết", new Color(40, 167, 69))); // Xanh lá
        ordersTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Cập Nhật", new Color(13, 110, 253))); // Xanh dương

        JScrollPane tableScrollPane = new JScrollPane(ordersTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(206, 212, 218))); // Viền bảng

        add(tableScrollPane, BorderLayout.CENTER);

        loadTableData();
    }

    public static OrdersTablePanel getInstance(Orders parentPanel, boolean reNew) {
        if (instance == null) {
            instance = new OrdersTablePanel(parentPanel);  // Tạo mới nếu chưa có instance
        }
        
        if (reNew) {
            instance = new OrdersTablePanel(parentPanel); // Tạo mới nếu reNew là true
        }
        return instance;
    }
    
    // Renderer cho các nút
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        private Color backgroundColor;

        public ButtonRenderer(String text, Color backgroundColor) {
            this.backgroundColor = backgroundColor;
            setText(text);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setBackground(backgroundColor); // Màu nền của nút
            setForeground(Color.WHITE); // Chữ màu trắng
            setFocusPainted(false);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(backgroundColor.darker(), 1), // Viền đậm hơn màu nền
                    BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding bên trong
            ));
            setContentAreaFilled(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String status = table.getValueAt(row, 5).toString();

            if((status.equals("Đã hoàn thành") || status.equals("Đã hủy")) && column == 7) {
                setEnabled(false); // Vô hiệu hóa nút nếu đơn hàng đã hoàn thành
            } else {
                setEnabled(true); // Kích hoạt nút nếu đơn hàng chưa hoàn thành
            }

            if (isSelected) {
                setBackground(backgroundColor.darker()); // Màu đậm hơn khi chọn
            } else {
                setBackground(backgroundColor); // Màu mặc định
            }
            return this;
        }
    }

    private void loadTableData() {
        List<OrderDTO> ordersData = ordersBUS.getAllOrders(); // Giả sử phương thức này trả về danh sách dữ liệu đơn hàng
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        for (OrderDTO order : ordersData) {
            Object[] rowData = {
                    order.getOrderId(),
                    order.getCreatedDate(),
                    order.getCustomerId(),
                    order.getAddress(),
                    order.getTotalAmount(),
                    order.getStatus(),
                    "Chi Tiết", // Nút Chi Tiết
                    "Cập Nhật"  // Nút Cập Nhật
            };
            tableModel.addRow(rowData);
        }
        handleButton(); // Xử lý sự kiện cho các nút
    }

    //xử lí sự kiện
    private void handleButton(){
        ordersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = ordersTable.rowAtPoint(e.getPoint());
                int column = ordersTable.columnAtPoint(e.getPoint());

                if (column == 6) { // Cột "Chi Tiết"
                    String orderId = tableModel.getValueAt(row, 0).toString();
                    showOrderDetails(orderId);
                } else if (column == 7) { // Cột "Cập Nhật"
                    String orderId = tableModel.getValueAt(row, 0).toString();
                    showUpdate(orderId);
                }
            }
        });
    }

    private void showOrderDetails(String orderId) {
        // Tạo cửa sổ chi tiết đơn hàng
        JDialog detailDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Chi Tiết Đơn Hàng", true);
        detailDialog.setSize(800, 600);
        detailDialog.setLocationRelativeTo(this);
    
        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 249, 250)); // Bootstrap bg-light
    
        // === Thông tin đơn hàng ===
        JPanel orderInfoPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        orderInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                "Thông Tin Đơn Hàng",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(33, 37, 41) // Bootstrap dark
        ));
        orderInfoPanel.setBackground(Color.WHITE);
    
        // Lấy thông tin đơn hàng từ OrdersBUS
        OrderDTO order = ordersBUS.getOrderById(orderId); // Giả sử OrdersBUS có phương thức này
        if (order != null) {
            orderInfoPanel.add(new JLabel("Mã Đơn Hàng:"));
            orderInfoPanel.add(new JLabel(order.getOrderId()));
    
            orderInfoPanel.add(new JLabel("Ngày Lập:"));
            orderInfoPanel.add(new JLabel(order.getCreatedDate().toString()));
    
            orderInfoPanel.add(new JLabel("Khách Hàng:"));
            orderInfoPanel.add(new JLabel(order.getCustomerId()));
    
            orderInfoPanel.add(new JLabel("Địa Chỉ:"));
            orderInfoPanel.add(new JLabel(order.getAddress()));
    
            orderInfoPanel.add(new JLabel("Tổng Tiền:"));
            orderInfoPanel.add(new JLabel(order.getTotalAmount() + " VND"));
    
            orderInfoPanel.add(new JLabel("Trạng Thái:"));
            orderInfoPanel.add(new JLabel(order.getStatus()));
        }
    
        // === Chi tiết sản phẩm ===
        JPanel productDetailsPanel = new JPanel(new BorderLayout());
        productDetailsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                "Chi Tiết Sản Phẩm",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(33, 37, 41) // Bootstrap dark
        ));
        productDetailsPanel.setBackground(Color.WHITE);
    
        String[] columns = {"Sản Phẩm", "Số Lượng", "Giá", "Thành Tiền"};
        DefaultTableModel productTableModel = new DefaultTableModel(columns, 0);
        JTable productTable = new JTable(productTableModel);
    
        // Lấy chi tiết sản phẩm từ DetailOrdersBUS
        List<DetailOrderDTO> detailOrders = new DetailOrdersBUS().getDetailOrdersByOrderId(orderId); // Giả sử DetailOrdersBUS có phương thức này
        for (DetailOrderDTO detail : detailOrders) {
            productTableModel.addRow(new Object[]{
                    detail.getXeId(),
                    detail.getQuantity(),
                    detail.getUnitPrice() + " VND",
                    detail.getTotalPrice() + " VND"
            });
        }
    
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBorder(BorderFactory.createLineBorder(new Color(206, 212, 218)));
        productDetailsPanel.add(productScrollPane, BorderLayout.CENTER);
    
        // Thêm các panel vào mainPanel
        mainPanel.add(orderInfoPanel, BorderLayout.NORTH);
        mainPanel.add(productDetailsPanel, BorderLayout.CENTER);
    
        // Thêm mainPanel vào dialog
        detailDialog.add(mainPanel);
        detailDialog.setVisible(true);
    }

    private void showUpdate(String orderId) {
        OrderDTO order = ordersBUS.getOrderById(orderId); 

        // Tạo cửa sổ cập nhật đơn hàng
        JDialog updateDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Cập Nhật Đơn Hàng", true);
        updateDialog.setSize(400, 200);
        updateDialog.setLocationRelativeTo(this);
        updateDialog.setLayout(new BorderLayout());
    
        // Panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Panel thông báo
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout());
        String question;
        String choice;
        if(order.getStatus().equals("Đang giao hàng")){
            question = "Xác nhận giao đơn hàng này?";
            choice = "Giao hàng";
        } else {
            question = "Xác nhận duyệt đơn hàng này?";
            choice = "Duyệt đơn hàng";
        }
        
        JLabel confirmLabel = new JLabel(question);
        confirmLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messagePanel.add(confirmLabel);
    
        // Panel nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    
        // Nút Hủy
        JButton cancelButton = new JButton("Hủy đơn hàng");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBackground(new Color(220, 53, 69)); // Bootstrap danger
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        //hàm xử lý hủy đơn hàng
        cancelButton.addActionListener(e -> {
            // Hiển thị dialog xác nhận hủy đơn hàng
            int option = JOptionPane.showConfirmDialog(
                updateDialog,
                "Bạn có chắc chắn muốn hủy đơn hàng này không?",
                "Xác nhận hủy đơn hàng",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            // Nếu người dùng chọn YES
            if (option == JOptionPane.YES_OPTION) {
                order.setStatus("Đã hủy");
                ordersBUS.updateOrder(order);
                JOptionPane.showMessageDialog(updateDialog, "Đã xác nhận hủy đơn hàng!");
                updateDialog.dispose();
                parentPanel.reRender();
            }
        });
    
        // Nút Xác nhận
        JButton confirmButton = new JButton("Xác nhận");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confirmButton.setBackground(new Color(40, 167, 69)); // Bootstrap success
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);

        confirmButton.addActionListener(e -> {
            // Xử lý cập nhật tại đây
            if(choice.equals("Giao hàng")){
                order.setStatus("Đã hoàn thành");
                ordersBUS.updateOrder(order);
            } else {
                order.setStatus("Đang giao hàng");
                ordersBUS.updateOrder(order);
            }

            JOptionPane.showMessageDialog(updateDialog, "Đã xác nhận cập nhật!");
            updateDialog.dispose();
            parentPanel.reRender();
        });
    
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);
    
        // Thêm các panel vào mainPanel
        mainPanel.add(messagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        // Thêm mainPanel vào dialog
        updateDialog.add(mainPanel);
        updateDialog.setVisible(true);
    }

    public void showTableByRow(List <OrderDTO> ordersData) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        for (OrderDTO order : ordersData) {
            Object[] rowData = {
                    order.getOrderId(),
                    order.getCreatedDate(),
                    order.getCustomerId(),
                    order.getAddress(),
                    order.getTotalAmount(),
                    order.getStatus(),
                    "Chi Tiết", // Nút Chi Tiết
                    "Cập Nhật"  // Nút Cập Nhật
            };
            tableModel.addRow(rowData);
        }

        parentPanel.revalidate();
        parentPanel.repaint(); // Vẽ lại giao diện
    }
}
