package DAO.Interface;

import java.sql.Connection;
import java.util.List;

public interface InvoicesDAOInterface<T, ID> {
    
    
    boolean create(T invoice, Connection conn);
    List<T> getAll();
    List<T> getById(ID id);
    boolean update(T invoice);
    boolean delete(ID id);
}
