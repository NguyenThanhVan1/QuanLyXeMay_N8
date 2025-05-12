package DAO;

import DTO.PurchaseOrderDetailDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;
import DAO.GenericDAO;
import DAO.Interface.RowMapper;

public class PurchaseOrderDetailDAO {
    private final GenericDAO genericDAO = new GenericDAO(); // Đổi tên biến để tuân theo quy ước
    private final RowMapper<PurchaseOrderDetailDTO> detailRowMapper = (ResultSet rs) -> {
        PurchaseOrderDetailDTO detail = new PurchaseOrderDetailDTO();
        detail.setPurchaseOrderId(rs.getLong("purchaseOrderId"));
        detail.setMaXe(rs.getString("maXe")); // Đã sửa lại tên cột
        detail.setQuantity(rs.getInt("quantity"));
        detail.setUnitPrice(rs.getBigDecimal("unitPrice"));
        detail.setSubTotal(rs.getBigDecimal("subTotal")); // Đã sửa lại tên cột
        return detail;
    };

    public PurchaseOrderDetailDTO findByCompositeKey(Long purchaseOrderId, String maXe) { // Tham số là String maXe
        String sql = "SELECT * FROM chitietdonhang WHERE MADH = ? AND MAXE = ?"; //Sửa tên bảng và cột cho đúng với database
        return genericDAO.queryForObject(sql, detailRowMapper, purchaseOrderId, maXe);
    }

    public List<PurchaseOrderDetailDTO> findAll() {
        String sql = "SELECT * FROM chitietdonhang";  //Sửa tên bảng cho đúng với database
        return genericDAO.queryForList(sql, detailRowMapper);
    }

    public List<PurchaseOrderDetailDTO> getDetailsByOrderId(Long orderId) {
        String sql = "SELECT * FROM chitietdonhang WHERE MADH = ?";  //Sửa tên bảng và cột cho đúng với database
        return genericDAO.queryForList(sql, detailRowMapper, orderId);
    }

    public Long create(PurchaseOrderDetailDTO detail) {
        String sql = "INSERT INTO chitietdonhang (MADH, MAXE, SOLUONG, GIATRI, THANHTIEN) VALUES (?, ?, ?, ?, ?)"; //Sửa tên bảng và cột cho đúng với database
        return genericDAO.insert(sql,
                detail.getPurchaseOrderId(),
                detail.getMaXe(), //Đã sửa lại getMaXe
                detail.getQuantity(),
                detail.getUnitPrice(),
                detail.getSubTotal() //Đã sửa lại getSubTotal()
        );
    }

    public boolean update(PurchaseOrderDetailDTO detail) {
        String sql = "UPDATE chitietdonhang SET SOLUONG = ?, GIATRI = ?, THANHTIEN = ? WHERE MADH = ? AND MAXE = ?";  //Sửa tên bảng và cột cho đúng với database
        return genericDAO.update(sql,
                detail.getQuantity(),
                detail.getUnitPrice(),
                detail.getSubTotal(),  //Đã sửa lại getSubTotal()
                detail.getPurchaseOrderId(),
                detail.getMaXe() //Đã sửa lại getMaXe()
        );
    }
    // Hàm này không cần thiết và gây nhầm lẫn vì đã có updateByCompositeKey
    // public boolean updateByOrderId(PurchaseOrderDetailDTO detail, Long oldOrderId) {
    //         String sql = "UPDATE purchaseorderdetails SET quantity = ?, unitPrice = ?, SubTotal = ? WHERE purchaseOrderId = ?";
    //         return genericDAL.update(sql,
    //                 detail.getQuantity(),
    //                 detail.getUnitPrice(),
    //                 detail.getSubTotal(),
    //                 oldOrderId
    //         );
    //     }


    public boolean delete(Long orderId) {
        String sql = "DELETE FROM chitietdonhang WHERE MADH = ?"; //Sửa tên bảng và cột cho đúng với database // Xóa theo PurchaseOrderID
        return genericDAO.delete(sql, orderId);
    }


    public boolean deleteByCompositeKey(Long purchaseOrderId, String maXe) { // Tham số là String maXe
        String sql = "DELETE FROM chitietdonhang WHERE MADH = ? AND MAXE = ?";  //Sửa tên bảng và cột cho đúng với database
        return genericDAO.delete(sql, purchaseOrderId, maXe);
    }

    public long getCurrentID() {
        String sql = "SELECT MAX(MADH) FROM chitietdonhang"; //Sửa tên bảng và cột cho đúng với database
        return genericDAO.getMaxID(sql);
    }
}
