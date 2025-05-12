package DTO;

import java.math.BigDecimal;

public class PurchaseOrderDetailDTO {
    private Long purchaseOrderId;
    private String maXe;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;

    public PurchaseOrderDetailDTO() {
    }

    public PurchaseOrderDetailDTO(Long purchaseOrderId, String maXe, int quantity, BigDecimal unitPrice, BigDecimal subTotal) {
        this.purchaseOrderId = purchaseOrderId;
        this.maXe = maXe;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getMaXe() {
        return maXe;
    }

    public void setMaXe(String maXe) {
        this.maXe = maXe;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
}
