// filepath: c:\xampp\htdocs\pttk\pttk\src\main\java\DAO\Interface\Base.java
package DAO.Interface;

import java.util.List;

public interface Base<T, ID> {
    boolean create(T entity);
    boolean update(T entity);
    T getById(ID id);
    List<T> getAll(); // Sử dụng List thay vì ArrayList
    boolean delete(ID id);
}