package DAO.Interface;

import java.sql.Connection;
import java.util.List;

public interface OrdersDAOInterface<T, ID> {
    
    
    boolean create(T entity);
    boolean delete(ID orderId);
    boolean update(T entity, Connection conn);
    List<T> getAll();
    T getById(ID orderId);
    List<T> getByStatus(String status);
}
