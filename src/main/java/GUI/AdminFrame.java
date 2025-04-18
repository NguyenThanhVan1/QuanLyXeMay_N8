package GUI;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {
    public AdminFrame() {
        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, 600)); // Sử dụng chiều cao cố định
        sidebar.setBackground(Color.LIGHT_GRAY);

        // Add submenu buttons
        JButton btnDashboard = new JButton("Dashboard");
        JButton btnOrders = new JButton("Orders");
        JButton btnSettings = new JButton("Settings");

        sidebar.add(btnDashboard);
        sidebar.add(btnOrders);
        sidebar.add(btnSettings);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());

        // Add content for each submenu
        contentPanel.add(new JLabel("Welcome to Dashboard"), "Dashboard");
        contentPanel.add(new OrderTablePanel(), "Orders"); // Updated to use OrderTablePanel
        contentPanel.add(new JLabel("Settings Page"), "Settings");

        // Add action listeners to buttons
        btnDashboard.addActionListener(e -> switchContent(contentPanel, "Dashboard"));
        btnOrders.addActionListener(e -> switchContent(contentPanel, "Orders"));
        btnSettings.addActionListener(e -> switchContent(contentPanel, "Settings"));

        // Add panels to frame
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void switchContent(JPanel contentPanel, String name) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminFrame frame = new AdminFrame();
            frame.setVisible(true);
        });
    }
}
