package GUI;

import BUS.OrdersBUS;

import javax.swing.*;
import java.awt.*;

public class Orders extends JPanel {
    private OrdersFilterPanel filterPanel;
    private OrdersTablePanel tablePanel;
    private OrdersSummaryPanel summaryPanel;

    public Orders() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(248, 249, 250)); // Màu nền tổng thể

        // === Phần thống kê ===
        summaryPanel = OrdersSummaryPanel.getInstance(this, false);
        add(summaryPanel);

        // === Phần bộ lọc ===
        filterPanel =  OrdersFilterPanel.getInstance(this, false);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding xung quanh
        add(filterPanel);

        // === Phần bảng ===
        tablePanel = OrdersTablePanel.getInstance(this, false);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding xung quanh
        add(tablePanel);
    }

    public OrdersSummaryPanel getSummaryPanel() {
        return this.summaryPanel;
    }

    public void setSummaryPanel(OrdersSummaryPanel summaryPanel) {
        this.summaryPanel = summaryPanel;
    }

    public OrdersTablePanel getTablePanel() {
        return tablePanel;
    }

    public void setTablePanel(OrdersTablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    public OrdersFilterPanel getFilterPanel() {
        return filterPanel;
    }

    public void setFilterPanel(OrdersFilterPanel filterPanel) {
        this.filterPanel = filterPanel;
    }
    
    public void reRender(){
        removeAll(); // Xóa tất cả các thành phần cũ     

        OrdersSummaryPanel summaryPanel = OrdersSummaryPanel.getInstance(this, true);
        OrdersFilterPanel filterPanel = OrdersFilterPanel.getInstance(this, true);
        OrdersTablePanel tablePanel = OrdersTablePanel.getInstance(this, true);
        add(summaryPanel, BorderLayout.NORTH); // Thêm panel mới
        add(filterPanel, BorderLayout.CENTER); // Thêm bộ lọc mới
        add(tablePanel, BorderLayout.SOUTH); // Thêm bảng mới

        revalidate();
        repaint(); // Vẽ lại giao diện
    }
}

