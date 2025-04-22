package BUS.Interface;

import java.sql.Connection;
import java.util.List;

public interface InvoicesBUSInterface<InvoicesDTO, Integer> {

    List<InvoicesDTO> getAll();

    List<InvoicesDTO> getById(Integer invoiceId);

    boolean create(Integer customerID, Integer employerID, Integer orderID);

    boolean update(InvoicesDTO invoice);

    boolean delete(Integer invoiceId);
    
}
