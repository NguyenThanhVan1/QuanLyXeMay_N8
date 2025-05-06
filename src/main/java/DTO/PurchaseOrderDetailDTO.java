package DTO;

import java.math.BigDecimal;

public class PurchaseOrderDetailDTO {
    private Long purchaseOrderId;
    private Long productId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;

    public PurchaseOrderDetailDTO() {
    }

    public PurchaseOrderDetailDTO(Long purchaseOrderId, Long productId, int quantity, BigDecimal unitPrice, BigDecimal subTotal) {
        this.purchaseOrderId = purchaseOrderId;
        this.productId = productId;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
