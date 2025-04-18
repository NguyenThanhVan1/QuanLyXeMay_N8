package GUI;

// ...existing imports...
import DAO.OrderDAO;
import DTO.OrderDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OrderTablePanel extends JPanel {
    public OrderTablePanel() {
        setLayout(new BorderLayout());

        // Table model and table
        String[] columnNames = {"Order ID", "Created Date", "Customer ID", "Address", "Total Amount", "Status", "Action"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only the "Action" column is editable
            }
        };
        JTable table = new JTable(tableModel);

        // Fetch data from OrderDAO and populate the table
        OrderDAO orderDAO = new OrderDAO();
        List<OrderDTO> orders = orderDAO.getAll();
        for (OrderDTO order : orders) {
            tableModel.addRow(new Object[]{
                order.getOrderId(),
                order.getCreatedDate(),
                order.getCustomerId(),
                order.getAddress(),
                order.getTotalAmount(),
                order.getStatus(),
                "Action" // Placeholder for buttons
            });
        }

        // Add custom renderer and editor for the "Action" column
        TableColumn actionColumn = table.getColumnModel().getColumn(6);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Custom renderer for buttons
    private static class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton btnDetails = new JButton("Details");
            JButton btnApprove = new JButton("Approve");
            add(btnDetails);
            add(btnApprove);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Custom editor for buttons
    private static class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton btnDetails;
        private JButton btnApprove;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            btnDetails = new JButton("Details");
            btnApprove = new JButton("Approve");

            btnDetails.addActionListener(this::handleDetailsAction);
            btnApprove.addActionListener(this::handleApproveAction);

            panel.add(btnDetails);
            panel.add(btnApprove);
        }

        private void handleDetailsAction(ActionEvent e) {
            // Implement the "Details" button action
            JOptionPane.showMessageDialog(null, "Details button clicked");
        }

        private void handleApproveAction(ActionEvent e) {
            // Implement the "Approve" button action
            JOptionPane.showMessageDialog(null, "Approve button clicked");
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Action";
        }
    }
}
