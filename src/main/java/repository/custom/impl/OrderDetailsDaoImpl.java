package repository.custom.impl;

import dbconnection.DBConnection;
import dto.OrderDetail;
import entity.OrderDetailEntity;
import repository.custom.OrderDetailsDao;
import service.custom.OrderDetailsService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {

    @Override
    public ArrayList<OrderDetailEntity> getAll() throws SQLException {
        ArrayList<OrderDetailEntity> objectArrayList = new ArrayList<>();
        ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM orderdetails");
        while (res.next()) {
            objectArrayList.add(new OrderDetailEntity(res.getString(1), res.getString(2), res.getInt(3), res.getDouble(4)));
        }
        return objectArrayList;
    }

    public boolean addOrderDetail(List<OrderDetail> orderDetails) throws SQLException {
        for (OrderDetail orderDetail : orderDetails){
            boolean isOrderDetailAdd = addOrderDetail(orderDetail);
            if (!isOrderDetailAdd){
                return false;
            }
        }
        return true;
    }

    private boolean addOrderDetail(OrderDetail orderDetail) throws SQLException {
        String SQL = "INSERT INTO orderdetails values (?,?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setString(1,orderDetail.getOrderID());
        psTm.setString(2,orderDetail.getProductID());
        psTm.setString(3, String.valueOf(orderDetail.getQuantity()));
        psTm.setString(4, String.valueOf(orderDetail.getSubTotal()));
        return psTm.executeUpdate()>0;
    }

}
