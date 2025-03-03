package repository.custom.impl;

import dbconnection.DBConnection;
import dto.Supplier;
import entity.SupplierEntity;
import repository.custom.SupplierDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public boolean save(SupplierEntity entity) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO suppliers values(?,?,?,?)");
        psTm.setObject(1, entity.getSupplierID());
        psTm.setObject(2, entity.getName());
        psTm.setObject(3, entity.getCompany());
        psTm.setObject(4, entity.getEmail());
        return psTm.executeUpdate() > 0;

    }

    @Override
    public boolean update(SupplierEntity entity) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE suppliers SET Name=?, Company=?, Email=? where SupplierID =? ");
        psTm.setObject(1, entity.getName());
        psTm.setObject(2, entity.getCompany());
        psTm.setObject(3, entity.getEmail());
        psTm.setObject(4, entity.getSupplierID());
        return psTm.executeUpdate() > 0;
    }


    @Override
    public boolean delete(String id) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("delete from suppliers where SupplierID = ? ");
        psTm.setObject(1, id);
        return psTm.executeUpdate() > 0;
    }

    @Override
    public List<SupplierEntity> getAll() throws SQLException {
        List<SupplierEntity> supplierList = new ArrayList<>();
        ResultSet rest = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM suppliers");
        while (rest.next()) {
            supplierList.add(new SupplierEntity(
                    rest.getString(1),
                    rest.getString(2),
                    rest.getString(3),
                    rest.getString(4)
            ));
        }
        return supplierList;
    }



    @Override
    public SupplierEntity search(String id) throws SQLException {
        ResultSet rest = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from suppliers where SupplierID ='" + id + "'");
        if (rest.next()) {
            return new SupplierEntity(rest.getString(1), rest.getString(2), rest.getString(3), rest.getString(4));

        }
        return null;
    }
}
