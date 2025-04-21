package DAO;

import DAO.Interface.Base;
import DTO.OrderDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements Base<OrderDTO, String> {

    @Override
    public boolean create(OrderDTO entity) {
        String sql = "INSERT INTO donhang (MADH, NGAYLAP, MAKH, DIACHI, TONGTIEN, TRANGTHAI) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getOrderId());
            ps.setDate(2, new java.sql.Date(entity.getCreatedDate().getTime()));
            ps.setString(3, entity.getCustomerId());
            ps.setString(4, entity.getAddress());
            ps.setBigDecimal(5, entity.getTotalAmount());
            ps.setString(6, entity.getStatus());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm đơn hàng: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM donhang WHERE MADH = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa đơn hàng: " + e.getMessage(), e);
        }
    }

    @Override
    public List<OrderDTO> getAll() {
        List<OrderDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM donhang";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrderDTO order = new OrderDTO(
                        rs.getString("MADH"),
                        rs.getDate("NGAYLAP"),
                        rs.getString("MAKH"),
                        rs.getString("DIACHI"),
                        rs.getBigDecimal("TONGTIEN"),
                        rs.getString("TRANGTHAI")
                );
                list.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách đơn hàng: " + e.getMessage(), e);
        }

        return list;
    }

    @Override
    public OrderDTO getById(String id) {
        String sql = "SELECT * FROM donhang WHERE MADH = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new OrderDTO(
                        rs.getString("MADH"),
                        rs.getDate("NGAYLAP"),
                        rs.getString("MAKH"),
                        rs.getString("DIACHI"),
                        rs.getBigDecimal("TONGTIEN"),
                        rs.getString("TRANGTHAI")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy đơn hàng theo ID: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public boolean update(OrderDTO entity) {
        String sql = "UPDATE donhang SET NGAYLAP = ?, MAKH = ?, DIACHI = ?, TONGTIEN = ?, TRANGTHAI = ? WHERE MADH = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(entity.getCreatedDate().getTime()));
            ps.setString(2, entity.getCustomerId());
            ps.setString(3, entity.getAddress());
            ps.setBigDecimal(4, entity.getTotalAmount());
            ps.setString(5, entity.getStatus());
            ps.setString(6, entity.getOrderId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật đơn hàng: " + e.getMessage(), e);
        }
    }

    public List<OrderDTO> getOrdersByStatus(String status){
        List<OrderDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM donhang WHERE TRANGTHAI = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDTO order = new OrderDTO(
                        rs.getString("MADH"),
                        rs.getDate("NGAYLAP"),
                        rs.getString("MAKH"),
                        rs.getString("DIACHI"),
                        rs.getBigDecimal("TONGTIEN"),
                        rs.getString("TRANGTHAI")
                );
                list.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách đơn hàng theo trạng thái: " + e.getMessage(), e);
        }

        return list;
    }
}