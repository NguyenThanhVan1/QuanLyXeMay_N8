package DTO;

public class DetailOrderDTO {
    private String orderId;
    private String xeId;
    private int quantity;

    public DetailOrderDTO(String orderId, String xeId, int quantity) {
        this.orderId = orderId;
        this.xeId = xeId;
        this.quantity = quantity;
    }

    public DetailOrderDTO() {
        // Default constructor
    }

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
}
