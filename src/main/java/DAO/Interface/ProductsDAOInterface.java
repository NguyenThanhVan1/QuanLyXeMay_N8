package DAO.Interface;

import java.util.List;

public interface ProductsDAOInterface<T, ID> {
    
    
    boolean create(T entity);
    boolean delete(ID productId);
    boolean update(T entity);
    List<T> getAll();
    T getById(ID productId);
}
