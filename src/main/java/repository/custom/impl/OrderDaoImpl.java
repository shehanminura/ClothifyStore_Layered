package repository.custom.impl;

import dbconnection.DBConnection;
import dto.Order;
import dto.OrderDetail;
import entity.OrderEntity;
import repository.DaoFactory;
import repository.custom.OrderDao;
import repository.custom.OrderDetailsDao;
import repository.custom.ProductsDao;
import util.DaoType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {
    OrderDetailsDao detailsDao = DaoFactory.getInstance().getDaoType(DaoType.ORDERDETAILS);
    ProductsDao productsDao = DaoFactory.getInstance().getDaoType(DaoType.PRODUCTS);
    @Override
    public boolean addOrder(OrderEntity order) throws SQLException {
        String SQL = "INSERT INTO orders Values(?,?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setString(1, order.getOrderid());
            psTm.setString(2, order.getEmployeeId());
            psTm.setString(3, order.getTotalcost());
            psTm.setString(4, order.getOrderdate());
            boolean isOrderAdd = psTm.executeUpdate() > 0;

            if (isOrderAdd){
                boolean isOrderDetailAdd = detailsDao.addOrderDetail(order.getOrderDetails());
                if (isOrderDetailAdd){
                    boolean isUpdate = productsDao.updateproduct(order.getOrderDetails());
                    if (isUpdate){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
