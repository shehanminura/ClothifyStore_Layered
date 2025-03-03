package service.custom;

import dto.Admin;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface AdminService extends SuperService {
    boolean addAdmin(Admin admin) throws SQLException;

    List<Admin>getAll() throws SQLException;

    boolean deleteAdmin(String id) throws SQLException;

     boolean updateAdmin(Admin admin) throws SQLException;

    Admin searchAdmin(String id) throws SQLException;

    Admin findByAdmin(String email) throws SQLException;
}
