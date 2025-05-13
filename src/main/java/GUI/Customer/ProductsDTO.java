package GUI.Customer;

public class ProductsDTO {
    private String ma;
    private String ten;
    private String loai;
    private long gia;
    private int soLuong;
    // Thêm các biến này vào các thuộc tính của class SanPhamPanel
    private long giaTu = 0;
    private long giaDen = 100000000;
    private int kieuSapXep = 0; // 0: Mặc định, 1: Tăng dần, 2: Giảm dần
    
    public ProductsDTO(String ma, String ten, String loai, long gia, int soLuong) {
        this.ma = ma;
        this.ten = ten;
        this.loai = loai;
        this.gia = gia;
        this.soLuong = soLuong;
    }
    
    // Getters and Setters
    public String getMa() {
        return ma;
    }
    
    public void setMa(String ma) {
        this.ma = ma;
    }
    
    public String getTen() {
        return ten;
    }
    
    public void setTen(String ten) {
        this.ten = ten;
    }
    
    public String getLoai() {
        return loai;
    }
    
    public void setLoai(String loai) {
        this.loai = loai;
    }
    
    public long getGia() {
        return gia;
    }
    
    public void setGia(long gia) {
        this.gia = gia;
    }
    
    public int getSoLuong() {
        return soLuong;
    }
    
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    @Override
    public String toString() {
        return ten + " - " + String.format("%,d", gia) + " VND";
    }
}