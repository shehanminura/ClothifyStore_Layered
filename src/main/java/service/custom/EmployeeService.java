package service.custom;

import dto.Employee;
import service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeService extends SuperService {

    boolean addEmployee(Employee employee) throws SQLException;

    ArrayList<Employee> getAll() throws SQLException;

    boolean deleteCustomer(String id) throws SQLException;

    Employee searchEmployee(String id) throws SQLException;

    boolean updateEmployee(Employee employee) throws SQLException;


    Employee getEmpByEmail(String text) throws SQLException;
}
