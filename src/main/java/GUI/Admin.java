package GUI;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import GUI.Orders; // Import the Orders class

public class Admin {

	private JFrame frame;
	private Orders ordersPanel; // Declare ordersPanel as a class-level variable

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin window = new Admin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Admin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		// Thiết lập full màn hình
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sử dụng BorderLayout thay vì null layout
		frame.getContentPane().setLayout(new BorderLayout());

		// Header panel
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setPreferredSize(new Dimension(0, 92));
		headerPanel.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

		// Sidebar panel
		JPanel sidebarPanel = new JPanel();
		sidebarPanel.setPreferredSize(new Dimension(212, 0));
		sidebarPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		sidebarPanel.setBackground(Color.DARK_GRAY);

		// Nút "Đơn hàng"
		JButton btnDonHang = new JButton("Đơn hàng");
		btnDonHang.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDonHang.setPreferredSize(new Dimension(190, 40));
		btnDonHang.setBackground(Color.GRAY);
		btnDonHang.setForeground(Color.WHITE);
		btnDonHang.setFocusPainted(false);
		btnDonHang.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		sidebarPanel.add(btnDonHang);

		// Nút "Thống kê"
		JButton btnThongKe = new JButton("Thống kê");
		btnThongKe.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnThongKe.setPreferredSize(new Dimension(190, 40));
		btnThongKe.setBackground(Color.GRAY);
		btnThongKe.setForeground(Color.WHITE);
		btnThongKe.setFocusPainted(false);
		btnThongKe.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		sidebarPanel.add(btnThongKe);

		frame.getContentPane().add(sidebarPanel, BorderLayout.WEST);

		// Main content panel
		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Add Orders panel
		ordersPanel = new Orders();
		contentPanel.add(ordersPanel, BorderLayout.CENTER);
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);

		// Add ActionListener to "Đơn hàng" button
		btnDonHang.addActionListener(e -> {
			ordersPanel.setVisible(true);
			// TODO: Ẩn panel thống kê nếu có
		});

		// Add ActionListener to "Thống kê" button
		btnThongKe.addActionListener(e -> {
			ordersPanel.setVisible(false);
			System.out.println("Đã ấn Thống kê");
			// TODO: Hiển thị panel thống kê
		});
	}

	/**
	 * Create an arrow button.
	 */
	protected JButton createArrowButton() {
		JButton button = new JButton("▼");
		button.setContentAreaFilled(false);
		button.setBackground(Color.WHITE);
		button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		return button;
	}
}
