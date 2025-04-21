package DTO;

import java.math.BigDecimal;

public class DetailOrderDTO {
    private String orderId; // MADH
    private String xeId; // MAXM
    private int quantity; // SOLUONG
    private BigDecimal unitPrice; // GIATRI
    private BigDecimal totalPrice; // THANHTIEN

    // Constructor đầy đủ
    public DetailOrderDTO(String orderId, String xeId, int quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.xeId = xeId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    // Constructor mặc định
    public DetailOrderDTO() {
        // Default constructor
    }

    // Getter và Setter
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getXeId() {
        return xeId;
    }

    public void setXeId(String xeId) {
        this.xeId = xeId;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "DetailOrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", xeId='" + xeId + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
