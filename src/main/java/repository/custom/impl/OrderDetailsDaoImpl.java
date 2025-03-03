package repository.custom.impl;

import dbconnection.DBConnection;
import entity.OrderDetailEntity;
import repository.custom.OrderDetailsDao;
import service.custom.OrderDetailsService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
