package DTO;
import java.math.BigDecimal;
import java.util.Date;

public class OrderDTO {
    private String orderId;
    private Date createdDate;
    private String customerId;
    private String address;
    private BigDecimal totalAmount;
    private String status;


    public OrderDTO(String orderId, Date createdDate, String customerId, String address, BigDecimal totalAmount,
        String status) {
        this.orderId = orderId;
        this.createdDate = createdDate;
        this.customerId = customerId;
        this.address = address;
        this.totalAmount = totalAmount;
        this.status = status;
    }


    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public Date getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public String getCustomerId() {
        return customerId;
    }


    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }


    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    
}
