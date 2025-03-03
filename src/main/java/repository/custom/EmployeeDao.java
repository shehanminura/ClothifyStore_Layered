package repository.custom;

import entity.EmployeeEntity;
import repository.CrudRepository;

import java.sql.SQLException;

public interface EmployeeDao extends CrudRepository<EmployeeEntity,String> {
    EmployeeEntity findEmpByEmail(String email) throws SQLException;
}
