package DAO.Interface;

import java.sql.Connection;
import java.util.List;



public interface ShoppingCartsDAOInterface <T, ID>{
    boolean create(T entity, Connection conn); 
    boolean delete(ID id, Connection conn); 
    List<T> getAll(Connection conn); 
    List<T> getByIdCustomer(String idCustomer, Connection conn); 
    boolean update(T entity, Connection conn);    
    boolean insert(T entity, Connection conn);
    T checkExist(String idCustomer, String idProduct, Connection conn);
}
