package DTO;

import java.math.BigDecimal;

public class DetailInvoicesDTO {
    private int orderID;
    private int productID;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;


    public DetailInvoicesDTO(int orderID, int productID, int quantity, BigDecimal price, BigDecimal totalPrice) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }


    public DetailInvoicesDTO() {
        //TODO Auto-generated constructor stub
    }


    public int getOrderID() {
        return orderID;
    }


    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


    public int getProductID() {
        return productID;
    }


    public void setProductID(int productID) {
        this.productID = productID;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public BigDecimal getPrice() {
        return price;
    }


    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }


    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public String toString() {
        return "DetailInvoicesDTO [orderID=" + orderID + ", productID=" + productID + ", quantity=" + quantity
                + ", price=" + price + ", totalPrice=" + totalPrice + "]";
    }

    

    
}