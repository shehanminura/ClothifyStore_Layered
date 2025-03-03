package service.custom.impl;

import dto.Employee;
import entity.EmployeeEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.EmployeeDao;
import service.custom.EmployeeService;
import util.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeServiceImpl implements EmployeeService {
    
    EmployeeDao dao = DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);
    @Override
    public boolean addEmployee(Employee employee) throws SQLException {
        EmployeeEntity map = new ModelMapper().map(employee, EmployeeEntity.class);
        return dao.save(map);

    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ArrayList<Employee> objectArrayList = new ArrayList<>();
        for (Object entity : dao.getAll()){
            Employee map = new ModelMapper().map(entity, Employee.class);
            objectArrayList.add(map);
        }
        return objectArrayList;

    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return dao.delete(id);
    }

    @Override
    public Employee searchEmployee(String id) throws SQLException {
        EmployeeEntity entity = (EmployeeEntity) dao.search(id);
        Employee map  = new ModelMapper().map(entity, Employee.class);
        return map;
    }

    @Override
    public boolean updateEmployee(Employee employee) throws SQLException {
        EmployeeEntity map = new ModelMapper().map(employee, EmployeeEntity.class);
        return dao.update(map);
    }

    @Override
    public Employee getEmpByEmail(String email) throws SQLException {
        EmployeeEntity empByEmail = dao.findEmpByEmail(email);
        Employee map = new ModelMapper().map(empByEmail, Employee.class);
        return map;
    }


}
