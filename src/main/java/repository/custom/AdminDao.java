package repository.custom;

import entity.AdminEntity;
import repository.CrudRepository;

import java.sql.SQLException;

public interface AdminDao extends CrudRepository<AdminEntity,String>{
    AdminEntity findByAdmin(String email) throws SQLException;
}
