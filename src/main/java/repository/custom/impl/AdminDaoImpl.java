package repository.custom.impl;

import dbconnection.DBConnection;
import entity.AdminEntity;
import repository.custom.AdminDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDao {

    @Override
    public boolean save(AdminEntity entity) throws SQLException {
        System.out.println(entity);
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into admin values(?,?,?,?)";
        PreparedStatement psTm = connection.prepareStatement(sql);
        psTm.setString(1, entity.getUserID());
        psTm.setString(2, entity.getName());
        psTm.setString(3, entity.getEmail());
        psTm.setString(4, entity.getPassword());
        return psTm.executeUpdate() > 0;
    }

    @Override
    public boolean update(AdminEntity entity) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE admin SET  Name = ? , Email = ? , Password = ? where UserId =?");
        psTm.setObject(1, entity.getName());
        psTm.setObject(2, entity.getEmail());
        psTm.setObject(3, entity.getPassword());
        psTm.setObject(4, entity.getUserID());
        return psTm.executeUpdate() > 0;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("DELETE from admin where userid= ?");
        psTm.setObject(1, id);
        return psTm.executeUpdate() > 0;
    }

    @Override
    public List<AdminEntity> getAll() throws SQLException {
        List<AdminEntity> adminEntities = new ArrayList<>();
        ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM admin");
        while (res.next()) {
            adminEntities.add(new AdminEntity(res.getString(1), res.getString(2), res.getString(3), res.getString(4)));
        }
        System.out.println(adminEntities);

        return adminEntities;
    }

    @Override
    public AdminEntity search(String id) throws SQLException {
        ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * FROM admin where UserID ='" + id + "'");
        if (res.next()) {
            return new AdminEntity(res.getString(1), res.getString(2),
                    res.getString(3), res.getString(4));
        }
        return null;
    }

    @Override
    public AdminEntity findByAdmin(String email) throws SQLException {
        String SQL = "SELECT * FROM admin where email =" + "'" + email + "'";
        Connection connection = DBConnection.getInstance().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(SQL);
        if (resultSet.next()) {
            return new AdminEntity(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }
}


