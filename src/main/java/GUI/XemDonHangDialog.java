package GUI;

import DAO.OrdersDAO;
import DTO.OrdersDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class XemDonHangDialog extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;

    public XemDonHangDialog(JFrame parent, String maKH, String trangThai) {
        super(parent, "Đơn hàng của bạn", true);
        setSize(800, 400);
        setLocationRelativeTo(parent);

        // Giao diện
        setLayout(new BorderLayout());

        // Tiêu đề các cột
        String[] columnNames = { "Mã ĐH", "Ngày lập", "Địa chỉ", "Tổng tiền", "Trạng thái" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // Lấy danh sách đơn hàng từ DAO
        loadDonHang(maKH, trangThai);

        // Nút đóng
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnClose);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadDonHang(String maKH, String trangThai) {
        try {
            OrdersDAO dao = new OrdersDAO();
            int customerId = Integer.parseInt(maKH);
            List<OrdersDTO> donHangList = dao.getAll();

            for (OrdersDTO dh : donHangList) {
                if (dh.getCustomerId() == customerId && dh.getStatus().equalsIgnoreCase(trangThai)) {
                    Object[] row = {
                            dh.getOrderId(),
                            dh.getCreatedDate(),
                            dh.getAddress(),
                            dh.getTotalAmount(),
                            dh.getStatus()
                    };
                    tableModel.addRow(row);
                }
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có đơn hàng nào với trạng thái \"" + trangThai + "\"");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải đơn hàng: " + e.getMessage());
        }
    }
}
