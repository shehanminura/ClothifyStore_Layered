package service.custom.impl;

import dto.Supplier;
import entity.SupplierEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.SupplierDao;
import service.custom.SupplierService;
import util.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;


public class SupplierServiceImpl implements SupplierService {


    SupplierDao dao = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
    @Override
    public boolean addSupplier(Supplier supplier) throws SQLException {
        return dao.save(new ModelMapper().map(supplier, SupplierEntity.class));

    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        ArrayList<Supplier> objectArrayList = new ArrayList<>();
        for (Object supplier : dao.getAll()){
            Supplier map = new ModelMapper().map(supplier, Supplier.class);
            objectArrayList.add(map);
        }
        return objectArrayList;
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException {
      return dao.delete(id);

    }

    @Override
    public Supplier serchSupplier(String id) throws SQLException {
        SupplierEntity search = dao.search(id);
        Supplier map = new ModelMapper().map(search, Supplier.class);
        return map;
    }

    @Override
    public boolean updateSupplier(Supplier supplier) throws SQLException {
        SupplierEntity map = new ModelMapper().map(supplier, SupplierEntity.class);
        return dao.update(map);

    }
}
