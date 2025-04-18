package DAO;

import DAO.Interface.Base;
import DTO.DetailOrderDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailOrderDAO implements Base<DetailOrderDTO, String> {
    @Override
    public boolean create(DetailOrderDTO entity) {
        String sql = "INSERT INTO chitietdonhang (MADH, MAXE, SOLUONG) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set parameters for the PreparedStatement
            ps.setString(1, entity.getOrderId());
            ps.setString(2, entity.getXeId());
            ps.setInt(3, entity.getQuantity());

            // Execute the SQL statement
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(DetailOrderDTO entity) {
        String sql = "UPDATE chitietdonhang SET SOLUONG = ? WHERE MADH = ? AND MAXE = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set parameters for the PreparedStatement
            ps.setInt(1, entity.getQuantity());
            ps.setString(2, entity.getOrderId());
            ps.setString(3, entity.getXeId());

            // Execute the SQL statement
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DetailOrderDTO getById(String id) {
        String sql = "SELECT * FROM chitietdonhang WHERE MADH = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DetailOrderDTO detailOrder = new DetailOrderDTO();
                detailOrder.setOrderId(rs.getString("MADH"));
                detailOrder.setXeId(rs.getString("MAXE"));
                detailOrder.setQuantity(rs.getInt("SOLUONG"));
                return detailOrder;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DetailOrderDTO> getAll() {
        String sql = "SELECT * FROM chitietdonhang";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<DetailOrderDTO> list = new ArrayList<>();
            while (rs.next()) {
                DetailOrderDTO detailOrder = new DetailOrderDTO();
                detailOrder.setOrderId(rs.getString("MADH"));
                detailOrder.setXeId(rs.getString("MAXE"));
                detailOrder.setQuantity(rs.getInt("SOLUONG"));
                list.add(detailOrder);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM chitietdonhang WHERE MADH = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
