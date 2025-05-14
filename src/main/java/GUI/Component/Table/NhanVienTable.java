// package GUI.Component.Table;

// import DTO.NhanVienDTO;
// import javax.swing.table.*;

// import BUS.NhanVienBUS;

// import javax.swing.*;
// import java.awt.*;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Vector;

// public class NhanVienTable extends JTableCustom {
//     private DefaultTableModel tblmodel;
//     private JTable table;   
//     private JScrollPane scrollPane;
//     private List<NhanVienDTO> employees;

//     public NhanVienTable() {
//         initTable();
//         loadData(NhanVienBUS.getInstance().getList()); 
//     }

//     private void initTable() {
//         String[] columns = {"Mã nhân viên", "Họ tên", "Ngày sinh", "Giới tính", "SĐT",
//                 "Địa chỉ", "Chức vụ", "Tên đăng nhập", "Mật khẩu", "Quyền"};
//         tblmodel = new DefaultTableModel(columns, 0);
//         table = new JTable(tblmodel);

//         // Cấu hình bảng
//         table.getColumnModel().getColumn(0).setPreferredWidth(80);
//         table.getColumnModel().getColumn(1).setPreferredWidth(120);
//         table.getColumnModel().getColumn(2).setPreferredWidth(60);
//         table.getColumnModel().getColumn(3).setPreferredWidth(60);
//         table.getColumnModel().getColumn(4).setPreferredWidth(60);
//         table.getColumnModel().getColumn(5).setPreferredWidth(120);
//         table.getColumnModel().getColumn(6).setPreferredWidth(100);
//         table.getColumnModel().getColumn(7).setPreferredWidth(100);
//         table.getColumnModel().getColumn(8).setPreferredWidth(100);
//         table.getColumnModel().getColumn(9).setPreferredWidth(110);

//         table.setRowHeight(30);
//         table.setShowGrid(true);
//         table.setGridColor(Color.GRAY); // Changed to GRAY
//         table.setBackground(Color.WHITE);
//         table.setFillsViewportHeight(true);

//         // Cấu hình header
//         JTableHeader header = table.getTableHeader();
//         header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
//         header.setBackground(new Color(51, 204, 255));
//         header.setForeground(Color.BLACK);
//         header.setFont(new Font("Arial", Font.BOLD, 13));

//         // Thêm sắp xếp
//         TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tblmodel);
//         table.setRowSorter(sorter);

//         // Tạo scroll pane
//         scrollPane = new JScrollPane(table);
//         employees = new ArrayList<>(); // Initialize the employees list.  This is important!
//     }

//     public void loadData(List<NhanVienDTO> list) {
//         tblmodel.setRowCount(0);
//         if (list != null) { // Added null check
//             for (NhanVienDTO nv : list) {
//                 Vector<String> row = new Vector<>();
//                 row.add(nv.getManv());
//                 row.add(nv.getHoten());
//                 row.add(nv.getNgaysinh());
//                 row.add(nv.getGioitinh());
//                 row.add(nv.getSdt());
//                 row.add(nv.getDiachi());
//                 row.add(nv.getChucvu());
//                 row.add(nv.getTendangnhap());
//                 row.add(nv.getMatkhau());
//                 row.add(nv.getQuyen());
//                 tblmodel.addRow(row);
//             }
//             this.employees = new ArrayList<>(list); //update employees list
//         }
//     }
//     @Override
// public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//     return table.prepareRenderer(renderer, row, column);
// }

// @Override
// public int getRowCount() {
//     return table.getRowCount();
// }

// @Override
// public int getColumnCount() {
//     return table.getColumnCount();
// }

//     public JTable getTable() {
//         return table;
//     }

//     public JScrollPane getScrollPane() {
//         return scrollPane;
//     }

//     public DefaultTableModel getModel() {
//         return tblmodel;
//     }

//     public String getSelectedMaNV() {
//         int row = table.getSelectedRow();
//         if (row >= 0) {
//             return table.getValueAt(row, 0).toString();
//         }
//         return null;
//     }

//     // Refactor getSelectedNhanVien
//     public NhanVienDTO getSelectedNhanVien() {
//         int row = table.getSelectedRow();
//         if (row >= 0) {
//             int modelRow = table.convertRowIndexToModel(row); // Use convertRowIndexToModel
//             return employees.get(modelRow);
//         }
//         return null;
//     }

//     public void setRowSorter(TableRowSorter<TableModel> sorter) {
//         table.setRowSorter(sorter);
//     }

//     public TableRowSorter<TableModel> getRowSorter() {
//         return (TableRowSorter<TableModel>) table.getRowSorter();
//     }

//     public void setEmployees(List<NhanVienDTO> employees) {
//         loadData(employees);
//     }
// }
package GUI.Component.Table;

import DTO.NhanVienDTO;
import javax.swing.table.*;

import BUS.NhanVienBUS;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class NhanVienTable extends JPanel {
    private DefaultTableModel tblmodel;
    private JTable table;   
    private JScrollPane scrollPane;
    private List<NhanVienDTO> employees;

    public NhanVienTable() {
        setLayout(new BorderLayout());
        initTable();
        loadData(NhanVienBUS.getInstance().getList()); 
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initTable() {
        String[] columns = {"Mã nhân viên", "Họ tên", "Ngày sinh", "Giới tính", "SĐT",
                "Địa chỉ", "Chức vụ", "Tên đăng nhập", "Mật khẩu", "Quyền"};
        tblmodel = new DefaultTableModel(columns, 0);
        table = new JTable(tblmodel);

        // Cấu hình bảng
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(0);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(60);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(100);
        table.getColumnModel().getColumn(8).setPreferredWidth(100);
        table.getColumnModel().getColumn(9).setPreferredWidth(110);

        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);

        // Cấu hình header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        header.setBackground(new Color(51, 204, 255));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 13));

        // Thêm sắp xếp
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tblmodel);
        table.setRowSorter(sorter);

        scrollPane = new JScrollPane(table);
        employees = new ArrayList<>();
    }

    public void loadData(List<NhanVienDTO> list) {
        tblmodel.setRowCount(0);
        if (list != null) {
            for (NhanVienDTO nv : list) {
                Vector<String> row = new Vector<>();
                row.add(nv.getManv());
                row.add(nv.getHoten());
                row.add(nv.getNgaysinh());
                row.add(nv.getGioitinh());
                row.add(nv.getSdt());
                row.add(nv.getDiachi());
                row.add(nv.getChucvu());
                row.add(nv.getTendangnhap());
                row.add(nv.getMatkhau());
                row.add(nv.getQuyen());
                tblmodel.addRow(row);
            }
            this.employees = new ArrayList<>(list);
        }
    }

    public JTable getTable() {
        return table;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public DefaultTableModel getModel() {
        return tblmodel;
    }

    public String getSelectedMaNV() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            return table.getValueAt(row, 0).toString();
        }
        return null;
    }

    public NhanVienDTO getSelectedNhanVien() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int modelRow = table.convertRowIndexToModel(row);
            return employees.get(modelRow);
        }
        return null;
    }

    public void setRowSorter(TableRowSorter<TableModel> sorter) {
        table.setRowSorter(sorter);
    }

    public TableRowSorter<TableModel> getRowSorter() {
        return (TableRowSorter<TableModel>) table.getRowSorter();
    }

    public void setEmployees(List<NhanVienDTO> employees) {
        loadData(employees);
    }
}
