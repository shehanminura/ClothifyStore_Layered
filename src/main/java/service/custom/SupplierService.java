package service.custom;

import dto.Supplier;
import service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierService extends SuperService {
    boolean addSupplier(Supplier supplier) throws SQLException;

    ArrayList<Supplier> getAll() throws SQLException;

    boolean deleteSupplier(String id) throws SQLException;

    Supplier serchSupplier(String id) throws SQLException;

    boolean updateSupplier(Supplier supplier) throws SQLException;
}
