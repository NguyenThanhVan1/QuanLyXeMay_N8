package GUI.Component.Dialog;

import BUS.*;
import DTO.*;
import DTO.Enum.PayStatus;
import DTO.Enum.PurchaseStatus;
import GUI.Component.Button.ButtonBack;
import GUI.Component.Button.ButtonChosen;
import GUI.Component.Button.ButtonIcon;
import GUI.Component.Panel.SanPhamPanel;
import GUI.Component.Panel.PurchaseOrderPanel;
import GUI.Component.Panel.Statistics.Components.DataRefreshListener;
import GUI.Component.Panel.Statistics.Components.EventBusManager;
import GUI.Component.Panel.Statistics.Components.PurchaseChangeEvent;
import GUI.Component.Table.PurchaseOrderTable;
import GUI.Component.Table.PurchaseOrderDetailsTable;
import GUI.Component.TextField.CustomTextField;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.toedter.calendar.demo.DateChooserPanel;

public class AddPurchaseOrderDialog extends JDialog {
    private PurchaseOrderBUS purchaseOrderBUS = new PurchaseOrderBUS();
    private PurchaseOrderDetailBUS purchaseOrderDetailBUS = new PurchaseOrderDetailBUS();
    private SupplierBUS supplierBUS = new SupplierBUS();
    private NhanVienBUS employeeBUS = new NhanVienBUS();
    private SanPhamBUS bookBUS = new SanPhamBUS();

    private JLabel supplierLabel;
    private JLabel supplierNameLabel;
    private JLabel supplierPhoneLabel;
    private JLabel supplierAddressLabel;
    

    private JLabel employeeLabel;
    private JLabel employeeNameLabel;

    private CustomTextField supplierField;
    private CustomTextField employeeField;
    private ButtonChosen supplierChosen;
    private ButtonChosen employeeChosen;

    private JLabel bookLabel;
    private PurchaseOrderDetailsTable purchaseOrderDetailsTable;

    private JButton addDetailButton;
    private JButton editDetailButton;
    private JButton deleteDetailButton;

    private JLabel buyDateLabel;
    private JLabel statusLabel;
    private JLabel statusValueLabel;
    private JLabel totalAmountLabel;
    private JLabel totalAmountValueLabel;

    private JDateChooser buyDateChooser;
    private JComboBox<String> statusComboBox;

    private SupplierDTO currentSupplier;
    private NhanVienDTO currentEmployee;
    private PurchaseOrderPanel purchaseOrderPanel;
    private List<PurchaseOrderDetailDTO> pendingOrderDetails = new ArrayList<>();
    private Long currentPurchaseId;

    public AddPurchaseOrderDialog(JFrame parent, PurchaseOrderPanel purchaseOrderPanel) {
        super(parent, "Thêm Phiếu Nhập", true);
        this.purchaseOrderPanel = purchaseOrderPanel;
        initComponents();
        setSize(800, 700);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 120, 215));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // ButtonBack backButton = new ButtonBack();
        // backButton.addActionListener(e -> dispose());
        // panel.add(backButton, BorderLayout.WEST);

        JLabel title = new JLabel("Thêm Phiếu Nhập");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Main content panel with vertical layout
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));

        // Panel for supplier information
        JPanel supplierPanel = new JPanel(new BorderLayout(5, 5));
        supplierLabel = new JLabel("Mã nhà cung cấp:");
        supplierField = new CustomTextField();
        supplierField.setPreferredSize(new Dimension(100, 30));
        supplierChosen = new ButtonChosen();
        supplierChosen.addActionListener(e -> chooseSupplier());

        supplierField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { showSupplierInfo(); }
            @Override public void removeUpdate(DocumentEvent e) { showSupplierInfo(); }
            @Override public void changedUpdate(DocumentEvent e) { showSupplierInfo(); }
        });

        JPanel supplierInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        supplierInputPanel.add(supplierLabel);
        supplierInputPanel.add(supplierField);
        supplierInputPanel.add(supplierChosen);

        JPanel supplierDetailsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        supplierNameLabel = new JLabel("          Tên Nhà cung cấp: ");
        supplierPhoneLabel = new JLabel("          Điện thoại: ");
        supplierAddressLabel = new JLabel("          Địa chỉ: ");
        supplierDetailsPanel.add(supplierNameLabel);
        supplierDetailsPanel.add(supplierPhoneLabel);
        supplierDetailsPanel.add(supplierAddressLabel);

        supplierPanel.add(supplierInputPanel, BorderLayout.NORTH);
        supplierPanel.add(supplierDetailsPanel, BorderLayout.CENTER);

        // Panel for employee information
        JPanel employeePanel = new JPanel(new BorderLayout(5, 5));
        employeeLabel = new JLabel("Nhân viên (Mã NV):");
        employeeField = new CustomTextField();
        employeeField.setPreferredSize(new Dimension(100, 30));
        employeeChosen = new ButtonChosen();
        employeeChosen.addActionListener(e -> chooseEmployee());

        employeeField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { showEmployeeInfo(); }
            @Override public void removeUpdate(DocumentEvent e) { showEmployeeInfo(); }
            @Override public void changedUpdate(DocumentEvent e) { showEmployeeInfo(); }
        });

        JPanel employeeInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        employeeInputPanel.add(employeeLabel);
        employeeInputPanel.add(employeeField);
        employeeInputPanel.add(employeeChosen);

        JPanel employeeDetailsPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        employeeNameLabel = new JLabel("          Tên NV: ");
        employeeDetailsPanel.add(employeeNameLabel);

        employeePanel.add(employeeInputPanel, BorderLayout.NORTH);
        employeePanel.add(employeeDetailsPanel, BorderLayout.CENTER);

        // Panel for order details
        JPanel detailsPanel = new JPanel(new BorderLayout(5, 5));
        bookLabel = new JLabel("Chi Tiết Phiếu Nhập:");
        detailsPanel.add(bookLabel, BorderLayout.NORTH);

        purchaseOrderDetailsTable = new PurchaseOrderDetailsTable();
        JScrollPane scrollPane = new JScrollPane(purchaseOrderDetailsTable);
        scrollPane.setPreferredSize(new Dimension(0, 200));

        JPanel detailButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        addDetailButton = new ButtonIcon("/icons/addbook.svg");
        addDetailButton.addActionListener(e -> addOrderDetail());
        deleteDetailButton = new ButtonIcon("/icons/deleteDetails.svg");
        deleteDetailButton.addActionListener(e -> deleteOrderDetails());
        editDetailButton = new ButtonIcon("/icons/editDetails.svg");
        editDetailButton.setEnabled(false);
        editDetailButton.addActionListener(e -> UpdateOrderDetails());

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(250, 0));
        totalAmountLabel = new JLabel("Tổng tiền:");
        totalAmountValueLabel = new JLabel("0 VND");

        detailButtonPanel.add(addDetailButton);
        detailButtonPanel.add(editDetailButton);
        detailButtonPanel.add(deleteDetailButton);
        detailButtonPanel.add(emptyPanel);
        detailButtonPanel.add(totalAmountLabel);
        detailButtonPanel.add(totalAmountValueLabel);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);
        detailsPanel.add(detailButtonPanel, BorderLayout.SOUTH);

        // Panel for date and status
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buyDateLabel = new JLabel("Ngày nhập:");
        buyDateChooser = new JDateChooser();
        buyDateChooser.setDateFormatString("yyyy-MM-dd");
        buyDateChooser.setDate(new Date());

        statusLabel = new JLabel("Trạng thái:");
        statusComboBox = new JComboBox<>(new String[]{"Đang_Chờ","Hoàn thành","Đã hủy"});

        infoPanel.add(buyDateLabel);
        infoPanel.add(buyDateChooser);
        infoPanel.add(statusLabel);
        infoPanel.add(statusComboBox);

        mainContentPanel.add(supplierPanel);
        mainContentPanel.add(Box.createVerticalStrut(10));
        mainContentPanel.add(employeePanel);
        mainContentPanel.add(Box.createVerticalStrut(10));
        mainContentPanel.add(detailsPanel);
        mainContentPanel.add(Box.createVerticalStrut(10));
        mainContentPanel.add(infoPanel);

        panel.add(mainContentPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        JButton cancelButton = new JButton("Hủy bỏ");
        cancelButton.setBackground(new Color(255, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(new Dimension(120, 30));
        cancelButton.addActionListener(e -> dispose());

        JButton addButton = new JButton("Thêm");
        addButton.setPreferredSize(new Dimension(120, 30));
        addButton.setBackground(new Color(0, 120, 215));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> addPurchaseOrder());

        panel.add(cancelButton);
        panel.add(addButton);
        return panel;
    }

    private void showSupplierInfo() {
        String supplierId = supplierField.getText();
        if (supplierId.isEmpty()) {
            return;
        } else {
            try {
                currentSupplier = supplierBUS.getSupplierById(supplierId);
                // Cập nhật thông tin nhà cung cấp lên giao diện
                supplierNameLabel.setText("    Tên nhà cung cấp: " + currentSupplier.getTENNCC());
                supplierPhoneLabel.setText("    Điện thoại: " + currentSupplier.getSODIENTHOAI());
                supplierAddressLabel.setText("    Địa chỉ: " + currentSupplier.getDIACHI());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void showEmployeeInfo() {
        String employeeId = employeeField.getText();
        if (employeeId.isEmpty()) {
            return;
        } else {
            try {
                currentEmployee = employeeBUS.getNhanVienById(employeeId);
                // Update labels with employee info
                employeeNameLabel.setText("    Tên NV: " + currentEmployee.getHoten());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void chooseSupplier() {
        ChooseSupplierDialog chooseSupplierDialog = new ChooseSupplierDialog(this);
        chooseSupplierDialog.setVisible(true);
        if (chooseSupplierDialog.getSelectedSupplier() != null) {
            currentSupplier = chooseSupplierDialog.getSelectedSupplier();
            supplierField.setText(currentSupplier.getMANCC());
            showSupplierInfo();
            // Update supplier info labels
        }
    }

    private void chooseEmployee() {
    // System.out.println("Opening employee selection dialog..."); // Debug
    ChooseEmployeeDialog chooseEmployeeDialog = new ChooseEmployeeDialog(this);
    chooseEmployeeDialog.setVisible(true);
    
    if (chooseEmployeeDialog.getSelectedEmployee() != null) {
        currentEmployee = chooseEmployeeDialog.getSelectedEmployee();
        System.out.println("Selected employee: " + currentEmployee.getHoten()); // Debug
        employeeField.setText(currentEmployee.getManv());
        showEmployeeInfo();
    }
}
private void setCurrentID() {
        try {
            currentPurchaseId = purchaseOrderBUS.getCurrentID() + 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lấy ID phiếu mượn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addOrderDetail() {
    AddPurchaseOrderDetailsDialog addDetailDialog = new AddPurchaseOrderDetailsDialog(this, 0L);
    addDetailDialog.setVisible(true);
    if (addDetailDialog.getCurrentOrderDetail() != null) {
        PurchaseOrderDetailDTO newDetail = addDetailDialog.getCurrentOrderDetail();
        for (PurchaseOrderDetailDTO detail : pendingOrderDetails) {
            if (detail.getMaXe().equals(newDetail.getMaXe())) {
                detail.setSoLuong(detail.getSoLuong() + newDetail.getSoLuong());
                detail.setThanhTien(detail.getDonGia() * detail.getSoLuong());
                purchaseOrderDetailsTable.updatePurchaseOrderDetails(detail);
                purchaseOrderDetailsTable.refreshTable();
                updateTotalAmount();
                return;
            }
        }
        pendingOrderDetails.add(newDetail);
        purchaseOrderDetailsTable.setPurchaseOrderDetails(pendingOrderDetails);
        purchaseOrderDetailsTable.refreshTable();
        updateTotalAmount();
    }
}
    

    private void UpdateOrderDetails() {
        int selectedRow = purchaseOrderDetailsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
//        PurchaseOrderDetailDTO selectedDetail = pendingOrderDetails.get(selectedRow);
//        UpdatePurchaseOrderDetail updateDialog = new UpdatePurchaseOrderDetail(this, selectedDetail);
//        updateDialog.setVisible(true);
//
//        if (updateDialog.getUpdatedDetail() != null) {
//            pendingOrderDetails.set(selectedRow, updateDialog.getUpdatedDetail());
//            purchaseOrderDetailsTable.setPurchaseOrderDetails(pendingOrderDetails);
//            purchaseOrderDetailsTable.refreshTable();
//            updateTotalAmount();
//        }
    }

    private void deleteOrderDetails() {
    int selectedRow = purchaseOrderDetailsTable.getSelectedRow();

    if (selectedRow != -1) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa chi tiết đã chọn?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            pendingOrderDetails.remove(selectedRow);
            purchaseOrderDetailsTable.setPurchaseOrderDetails(pendingOrderDetails);
            purchaseOrderDetailsTable.refreshTable();
            updateTotalAmount();

            JOptionPane.showMessageDialog(
                    this,
                    "Xóa chi tiết thành công",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    } else {
        JOptionPane.showMessageDialog(
                this,
                "Vui lòng chọn một chi tiết để xóa",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE
        );
    }
}

    private void updateTotalAmount() {
    int total = 0;
    for (PurchaseOrderDetailDTO detail : pendingOrderDetails) {
        total += detail.getThanhTien();
    }
    totalAmountValueLabel.setText(total + " VND");
}

    private boolean validateInput() {
        if (supplierField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (employeeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (buyDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày nhập", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (pendingOrderDetails.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một chi tiết phiếu nhập", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    
    private void addPurchaseOrder() {
    if (!validateInput()) return;

    try {
        setCurrentID();
        String supplierId = supplierField.getText().trim();
        String employeeId = employeeField.getText().trim();
        Date selectedDate = buyDateChooser.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String buyDate = sdf.format(selectedDate);

        PurchaseStatus status = PurchaseStatus.valueOf(statusComboBox.getSelectedItem().toString());

        // Tính tổng tiền từ danh sách chi tiết
        int totalAmount = 0;
        for (PurchaseOrderDetailDTO detail : pendingOrderDetails) {
            totalAmount += detail.getThanhTien();
        }

        // Tạo đối tượng phiếu nhập
        PurchaseOrderDTO newOrder = new PurchaseOrderDTO();
        newOrder.setMaPN(currentPurchaseId);
        newOrder.setMANCC(supplierId);
        newOrder.setMaNV(employeeId);
        newOrder.setBuyDate(buyDate);
        newOrder.setTongTien(totalAmount);
        newOrder.setStatus(status);

        // Gọi BUS để thêm phiếu nhập
        purchaseOrderBUS.addPurchaseOrder(newOrder);

        // Thêm chi tiết phiếu nhập
        for (PurchaseOrderDetailDTO detail : pendingOrderDetails) {
            detail.setMaPN(currentPurchaseId); // Gán mã phiếu nhập cho mỗi chi tiết
            purchaseOrderDetailBUS.addPurchaseOrderDetail(detail);
        }

        // Update stock levels after adding the purchase order details
        purchaseOrderDetailBUS.capNhatTonKhoSauKhiNhap(pendingOrderDetails);

        JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        // Cập nhật lại bảng hiển thị bên ngoài nếu cần
        if (purchaseOrderPanel != null) {
            purchaseOrderPanel.refreshData();
        }

        dispose(); // Đóng dialog sau khi thêm xong
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Lỗi khi thêm phiếu nhập: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}


}