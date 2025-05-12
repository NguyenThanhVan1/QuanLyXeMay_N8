package BUS;

import DAO.PurchaseOrderDetailDAO;
import DTO.SanPhamDTO;
import DTO.PurchaseOrderDetailDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PurchaseOrderDetailBUS {
    private PurchaseOrderDetailDAO purchaseOrderDetailDAL = new PurchaseOrderDetailDAO();
    public static List<PurchaseOrderDetailDTO> purchaseOrderDetailList;
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    public PurchaseOrderDetailBUS() {
        if (purchaseOrderDetailList == null) {
            getPurchaseOrderDetailList();
        }
    }

    public void getPurchaseOrderDetailList() {
        try {
            purchaseOrderDetailList = purchaseOrderDetailDAL.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách chi tiết phiếu nhập!", e);
        }
    }

    // public void addPurchaseOrderDetail(Long purchaseOrderId, Long bookId, int quantity) {
    //     try {
    //         Book book = bookBUS.getBookById(bookId);
    //         double unitPrice = book.getUnitPrice();
    //         double subTotal = unitPrice * quantity;

    //         PurchaseOrderDetailDTO detail = new PurchaseOrderDetailDTO(
    //                 purchaseOrderId,
    //                 bookId,
    //                 quantity,
    //                 unitPrice,
    //                 subTotal
    //         );

    //         purchaseOrderDetailDAL.create(detail);
    //         purchaseOrderDetailList.add(detail);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         throw new RuntimeException("Lỗi khi thêm chi tiết phiếu nhập!");
    //     }
    // }
    public void addPurchaseOrderDetail(PurchaseOrderDetailDTO detail) {
        try {
            purchaseOrderDetailDAL.create(detail);
            purchaseOrderDetailList.add(detail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm chi tiết phiếu nhập!");
        }
    }
    

    public void deletePurchaseOrderDetailsByOrderId(Long purchaseOrderId) {
        try {
            Iterator<PurchaseOrderDetailDTO> iterator = purchaseOrderDetailList.iterator();
            while (iterator.hasNext()) {
                PurchaseOrderDetailDTO detail = iterator.next();
                if (detail.getPurchaseOrderId().equals(purchaseOrderId)) {
                    iterator.remove();
                    purchaseOrderDetailDAL.deleteByCompositeKey(detail.getPurchaseOrderId(), detail.getMaXe());

                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa chi tiết phiếu nhập!", e);
        }
    }
    public boolean updatePurchaseOrderDetail(PurchaseOrderDetailDTO detail) {
        try {
            boolean updated = purchaseOrderDetailDAL.update(detail);
            if (updated) {
                for (int i = 0; i < purchaseOrderDetailList.size(); i++) {
                    PurchaseOrderDetailDTO existing = purchaseOrderDetailList.get(i);
                    if (existing.getPurchaseOrderId().equals(detail.getPurchaseOrderId()) &&
                        existing.getMaXe().equals(detail.getMaXe())) {
                        purchaseOrderDetailList.set(i, detail);
                        break;
                    }
                }
            }
            return updated;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật chi tiết phiếu nhập!", e);
        }
    }
    public double getTotalAmount(Long purchaseOrderId) {
        double total = 0;
        for (PurchaseOrderDetailDTO detail : purchaseOrderDetailList) {
            if (detail.getPurchaseOrderId().equals(purchaseOrderId)) {
                // total += detail.getSubTotal();
            }
        }
        return total;
    }
    public List<PurchaseOrderDetailDTO> getPurchaseOrderDetailsByOrderId(Long purchaseOrderId) {
        List<PurchaseOrderDetailDTO> details = new ArrayList<>();
        for (PurchaseOrderDetailDTO detail : purchaseOrderDetailList) {
            if (detail.getPurchaseOrderId().equals(purchaseOrderId)) {
                details.add(detail);
            }
        }
        return details;
    }
}
