package DAO;

import DTO.DetailOrderDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetailOrderDAO {

    // Thêm chi tiết đơn hàng mới
    public boolean create(DetailOrderDTO detailOrder) {
        String sql = "INSERT INTO chitietdonhang (MADH, MAXM, SOLUONG, GIATRI, THANHTIEN) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, detailOrder.getOrderId());
            ps.setString(2, detailOrder.getXeId());
            ps.setInt(3, detailOrder.getQuantity());
            ps.setBigDecimal(4, detailOrder.getUnitPrice());
            ps.setBigDecimal(5, detailOrder.getTotalPrice());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm chi tiết đơn hàng: " + e.getMessage(), e);
        }
    }

    // Lấy danh sách tất cả chi tiết đơn hàng
    public List<DetailOrderDTO> getAll() {
        List<DetailOrderDTO> detailOrders = new ArrayList<>();
        String sql = "SELECT * FROM chitietdonhang";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DetailOrderDTO detailOrder = new DetailOrderDTO(
                        rs.getString("MADH"),
                        rs.getString("MAXM"),
                        rs.getInt("SOLUONG"),
                        rs.getBigDecimal("GIATRI"),
                        rs.getBigDecimal("THANHTIEN")
                );
                detailOrders.add(detailOrder);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách chi tiết đơn hàng: " + e.getMessage(), e);
        }

        return detailOrders;
    }

    // Lấy danh sách chi tiết đơn hàng theo mã đơn hàng
    public List<DetailOrderDTO> getByOrderId(String orderId) {
        List<DetailOrderDTO> detailOrders = new ArrayList<>();
        String sql = "SELECT * FROM chitietdonhang WHERE MADH = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DetailOrderDTO detailOrder = new DetailOrderDTO(
                        rs.getString("MADH"),
                        rs.getString("MAXM"),
                        rs.getInt("SOLUONG"),
                        rs.getBigDecimal("GIATRI"),
                        rs.getBigDecimal("THANHTIEN")
                );
                detailOrders.add(detailOrder);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy chi tiết đơn hàng theo mã đơn hàng: " + e.getMessage(), e);
        }

        return detailOrders;
    }

    // Cập nhật chi tiết đơn hàng
    public boolean update(DetailOrderDTO detailOrder) {
        String sql = "UPDATE chitietdonhang SET SOLUONG = ?, GIATRI = ?, THANHTIEN = ? WHERE MADH = ? AND MAXM = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detailOrder.getQuantity());
            ps.setBigDecimal(2, detailOrder.getUnitPrice());
            ps.setBigDecimal(3, detailOrder.getTotalPrice());
            ps.setString(4, detailOrder.getOrderId());
            ps.setString(5, detailOrder.getXeId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật chi tiết đơn hàng: " + e.getMessage(), e);
        }
    }

    // Xóa chi tiết đơn hàng theo mã đơn hàng và mã xe
    public boolean delete(String orderId, String xeId) {
        String sql = "DELETE FROM chitietdonhang WHERE MADH = ? AND MAXM = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, orderId);
            ps.setString(2, xeId);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa chi tiết đơn hàng: " + e.getMessage(), e);
        }
    }
}