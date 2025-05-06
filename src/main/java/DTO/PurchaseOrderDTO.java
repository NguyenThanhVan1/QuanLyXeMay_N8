package DTO;

import java.math.BigDecimal;
import java.util.Date;
import DTO.Enum.PurchaseStatus;

public class PurchaseOrderDTO {
    private long MaPN;
    private String MaNV;
    private String MANCC;
    private PurchaseStatus status;
    private BigDecimal totalAmount;
    private Date buyDate;

    public PurchaseOrderDTO() {
    }

    public PurchaseOrderDTO(long MaPN, String MaNV, String MANCC, PurchaseStatus status, BigDecimal totalAmount, Date buyDate) {
        this.MaPN = MaPN;
        this.MaNV = MaNV;
        this.MANCC = MANCC;
        this.status = status;
        this.totalAmount = totalAmount;
        this.buyDate = buyDate;
    }

    public long getMaPN() {
        return MaPN;
    }

    public void setMaPN(long MaPN) {
        this.MaPN = MaPN;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMANCC() {
        return MANCC;
    }

    public void setMANCC(String MANCC) {
        this.MANCC = MANCC;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
}
