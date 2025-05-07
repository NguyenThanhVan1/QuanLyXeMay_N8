package DTO;
import java.math.BigDecimal;
import java.util.Date;

public class OrdersDTO {
    private int orderId;
    private Date createdDate;
    private int customerId;
    private String address;
    private BigDecimal totalAmount;
    private String status;


    public OrdersDTO(Date createdDate, int customerId, String address, BigDecimal totalAmount,
        String status) {
        this.createdDate = createdDate;
        this.customerId = customerId;
        this.address = address;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public OrdersDTO(int orderId, Date createdDate, int customerId, String address,
        BigDecimal totalAmount, String status) {
        this.orderId = orderId;
        this.createdDate = createdDate;
        this.customerId = customerId;
        this.address = address;
        this.totalAmount = totalAmount;
        this.status = status;
    }


    public OrdersDTO() {
        //TODO Auto-generated constructor stub
    }



    public int getOrderId() {
        return orderId;
    }


    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public Date getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public int getCustomerId() {
        return customerId;
    }


    public void setCustomerId(int customerId) {
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

    @Override
    public String toString() {
        return String.format("%-10s | %-12s | %-10s | %-30s | %-12s | %-15s\n",
                orderId, createdDate, customerId, address, totalAmount, status);
    }
}