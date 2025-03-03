package service.custom.impl;

import dto.Admin;
import entity.AdminEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.AdminDao;
import service.custom.AdminService;
import util.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AdminServiceImpl implements AdminService {

    AdminDao dao = DaoFactory.getInstance().getDaoType(DaoType.ADMIN);

    @Override
    public boolean addAdmin(Admin admin) throws SQLException {
        System.out.println(admin);
        AdminEntity map = new ModelMapper().map(admin, AdminEntity.class);
        boolean save = dao.save(map);
        if (save){
            return true;
        }
            return false;
    }

    @Override
    public List<Admin> getAll() throws SQLException {
        ArrayList<Admin> adminArrayList = new ArrayList<>();
        for (Object entity : dao.getAll()) {
            Admin map = new ModelMapper().map(entity, Admin.class);
            adminArrayList.add(map);
        }
        return adminArrayList ;
    }

    @Override
    public boolean deleteAdmin(String id) throws SQLException {
        boolean delete = dao.delete(id);
        System.out.println("delete");
        return delete;
    }


    @Override
    public boolean updateAdmin(Admin admin) throws SQLException {
        AdminEntity map = new ModelMapper().map(admin, AdminEntity.class);
        System.out.println(map);
        return dao.update(map);
    }

    @Override
    public Admin searchAdmin(String id) throws SQLException {
        AdminEntity entity =dao.search(id);
        Admin map = new ModelMapper().map(entity, Admin.class);
        return map;
    }

    @Override
    public Admin findByAdmin(String email) throws SQLException {
        AdminEntity byAdmin = dao.findByAdmin(email);
        Admin map = new ModelMapper().map(byAdmin, Admin.class);
        return map;
    }
}
