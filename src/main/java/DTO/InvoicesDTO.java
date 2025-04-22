package DTO;

import java.math.BigDecimal;
import java.util.Date;

public class InvoicesDTO {
    private int id;
    private Date date;
    private int customerId;
    private int employerID;
    private BigDecimal totalPrice;
    private int orderID;


    @Override
    public String toString() {
        return "InvoicesDTO [id=" + id + ", date=" + date + ", customerId=" + customerId + ", employerID=" + employerID
                + ", totalPrice=" + totalPrice + ", orderID=" + orderID + "]";
    }

    public InvoicesDTO(int id, Date date, int customerId, int employerID, BigDecimal totalPrice, int orderID) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.employerID = employerID;
        this.totalPrice = totalPrice;
        this.orderID = orderID;
    }

    public InvoicesDTO(Date date, int customerId, int employerID, BigDecimal totalPrice, int orderID) {
        this.date = date;
        this.customerId = customerId;
        this.employerID = employerID;
        this.totalPrice = totalPrice;
        this.orderID = orderID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderID() {
        return orderID;
    }

    
}
