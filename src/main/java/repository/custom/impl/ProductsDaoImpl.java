package repository.custom.impl;

import dbconnection.DBConnection;
import dto.OrderDetail;
import entity.ProductsEntity;
import repository.custom.ProductsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsDaoImpl implements ProductsDao {

    @Override
    public boolean save(ProductsEntity entity) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO products (productID, name, category, size, price, quantity, supplierID, imageURL) VALUES (?,?,?,?,?,?,?,?)");
        psTm.setObject(1,entity.getProductID());
        psTm.setObject(2,entity.getName());
        psTm.setObject(3,entity.getCategory());
        psTm.setObject(4,entity.getSize());
        psTm.setObject(5,entity.getPrice());
        psTm.setObject(6,entity.getQuantity());
        psTm.setObject(7,entity.getSupplierID());
        psTm.setObject(8,entity.getImageURL());
        return psTm.executeUpdate()>0;
    }

    @Override
    public boolean update(ProductsEntity entity) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE products SET Name=?,Category = ? ,Size = ?, Price = ?, Quantity =?,SupplierID=?,ImageURL=? where ProductID = ?");
        psTm.setObject(1,entity.getName());
        psTm.setObject(2,entity.getCategory());
        psTm.setObject(3,entity.getSize());
        psTm.setObject(4,entity.getPrice());
        psTm.setObject(5,entity.getQuantity());
        psTm.setObject(6,entity.getSupplierID());
        psTm.setObject(7,entity.getImageURL());
        psTm.setObject(8,entity.getProductID());
        return psTm.executeUpdate()>0;

    }

    @Override
    public boolean delete(String id) throws SQLException {
        System.out.println("entity"+id);
        PreparedStatement pSsTm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM products where ProductID = ? ");
        pSsTm.setObject(1,id);
        return pSsTm.executeUpdate()>0;
    }

    @Override
    public List<ProductsEntity> getAll() throws SQLException {
        List<ProductsEntity> productsEntities = new ArrayList<>();
        ResultSet rest = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM products");
        System.out.println(rest);
        while (rest.next()){
            productsEntities.add(new ProductsEntity(
                    rest.getString(1),
                    rest.getString(2),
                    rest.getString(3),
                    rest.getString(4),
                    rest.getDouble(5),
                    rest.getInt(6),
                    rest.getString(7),
                    rest.getString(8)
            ));
        }
        return productsEntities;
    }

    @Override
    public ProductsEntity search(String id) throws SQLException {
        ResultSet rest = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from products where ProductID ='" + id + "'");
        if (rest.next()){
            return new ProductsEntity(rest.getString(1),rest.getString(2),rest.getString(3),rest.getString(4),rest.getDouble(5),
                    rest.getInt(6),rest.getString(7),rest.getString(8));
        }
        return null;
    }

    public boolean updateproduct(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail :orderDetails){
            boolean isUpdateStock = updateproducts(orderDetail);
            if (isUpdateStock){
                return false;
            }
        }
        return true;
    }

    private boolean updateproducts(OrderDetail orderDetail) {
        String SQL = "UPDATE PRODUCTS SET Quantity = Quantity-? WHERE ProductID = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,orderDetail.getQuantity());
            psTm.setObject(2,orderDetail.getOrderID());
            return psTm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
