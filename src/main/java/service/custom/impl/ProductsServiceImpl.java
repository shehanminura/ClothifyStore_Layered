package service.custom.impl;

import dto.Products;
import entity.ProductsEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.ProductsDao;
import service.custom.ProductsService;
import util.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;


public class ProductsServiceImpl implements ProductsService {

    ProductsDao dao = DaoFactory.getInstance().getDaoType(DaoType.PRODUCTS);


    @Override
    public boolean addProducts(Products products) throws SQLException {
        ProductsEntity map = new ModelMapper().map(products, ProductsEntity.class);
        return dao.save(map);

    }

    @Override
    public ArrayList<Products> getAll() throws SQLException {
        ArrayList<Products> objectArrayList = new ArrayList<>();
        for (Object product : dao.getAll()){
            Products map = new ModelMapper().map(product, Products.class);
            objectArrayList.add(map);
        }
        return objectArrayList;
    }

    @Override
    public boolean deleteProducts(String id) throws SQLException {
        System.out.println(id);
        return dao.delete(id);
    }

    @Override
    public Products serchProducts(String id) throws SQLException {
        ProductsEntity productsEntity = dao.search(id);
        Products map = new ModelMapper().map(productsEntity, Products.class);
        return map;
    }

    @Override
    public boolean updateProducts(Products products) throws SQLException {
        ProductsEntity map = new ModelMapper().map(products, ProductsEntity.class);
        return dao.update(map);

    }
}
