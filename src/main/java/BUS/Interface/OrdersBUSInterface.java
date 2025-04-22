package BUS.Interface;

import DTO.OrdersDTO;
import DTO.ProductsDTO;

import java.util.Date;
import java.util.List;

public interface OrdersBUSInterface<T, ID>{

    List<T> getAll();

    T getById(ID orderId);

    boolean create(T order, List<ProductsDTO> productList);

    boolean update(T order);

    boolean delete(ID orderId);

    List<T> getOrdersByFilters(Date fromDate, Date toDate, String statusFilter);
}