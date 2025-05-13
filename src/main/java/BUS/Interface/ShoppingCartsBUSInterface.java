package BUS.Interface;

import java.util.List;

public interface ShoppingCartsBUSInterface<T, ID> {
    boolean create(T entity); 
    boolean delete(ID id); 
    java.util.List<T> getAll(); 
    List<T> getByIdCustomer(String idCustomer); 
    boolean update(T entity);
    boolean insert(T entity);
    T checkExist(String idCustomer, String idProduct);
}
