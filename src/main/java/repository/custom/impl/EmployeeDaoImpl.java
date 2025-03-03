package repository.custom.impl;

import dbconnection.DBConnection;
import dto.Supplier;
import entity.EmployeeEntity;
import repository.custom.EmployeeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public boolean save(EmployeeEntity entity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql ="insert into employees values (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,entity.getEmployeeid());
        preparedStatement.setString(2,entity.getUserid());
        preparedStatement.setString(3,entity.getName());
        preparedStatement.setString(4,entity.getCompany());
        preparedStatement.setString(5,entity.getEmail());
        preparedStatement.setString(6,entity.getPassword());
        return preparedStatement.executeUpdate() > 0;

    }

    @Override
    public boolean update(EmployeeEntity entity) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Employees set UserID = ?,Name= ?,Company=?,Email=?,Password=? where EmployeeID= ?");
        psTm.setObject(1,entity.getUserid());
        psTm.setObject(2,entity.getName());
        psTm.setObject(3,entity.getCompany());
        psTm.setObject(4,entity.getEmail());
        psTm.setObject(5,entity.getPassword());
        psTm.setObject(6,entity.getEmployeeid());
        return psTm.executeUpdate()>0;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("Delete from employees where EmployeeID = ?");
        psTm.setObject(1,id);
        return psTm.executeUpdate()>0;

    }

    @Override
    public List<EmployeeEntity> getAll() throws SQLException {
        List<EmployeeEntity>employeeEntities =new ArrayList<>();
        ResultSet rest = DBConnection.getInstance().getConnection().createStatement().executeQuery("Select * from employees");
        while (rest.next()){
            employeeEntities.add(new EmployeeEntity(rest.getString(1),rest.getString(2),rest.getString(3),rest.getString(4),rest.getString(5),rest.getString(6)));
        }
        return employeeEntities;
    }

    @Override
    public EmployeeEntity search(String id) throws SQLException {
        ResultSet res= DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from employees where EmployeeID ='" + id + "'");
        while (res.next()){
            return new EmployeeEntity(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6));

        }

        return null;
    }

    @Override
    public EmployeeEntity findEmpByEmail(String email) throws SQLException {
        String SQL = "SELECT * FROM Employees where email=" + "'" + email + "'";
        Connection connection = DBConnection.getInstance().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(SQL);
        if (resultSet.next()) {
            return new EmployeeEntity(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)

            );
        }
        return null;
    }

}
