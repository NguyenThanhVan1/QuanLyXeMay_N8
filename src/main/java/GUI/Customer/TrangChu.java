package GUI.Customer;

import javax.swing.JFrame;

public class TrangChu extends JFrame{
    public TrangChu() {
        new MainFrame().setVisible(true);
    }
    public static void main(String[] args) {
        // Thiết lập look and feel hiện đại
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Khởi chạy ứng dụng với EDT (Event Dispatch Thread)
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}