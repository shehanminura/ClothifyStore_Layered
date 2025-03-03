package service.custom;

import dto.Products;
import service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductsService  extends SuperService {
    boolean addProducts(Products products) throws SQLException;
    ArrayList<Products> getAll() throws SQLException;

    boolean deleteProducts(String id) throws SQLException;

    Products serchProducts(String id) throws SQLException;

    boolean updateProducts(Products products) throws SQLException;
}
