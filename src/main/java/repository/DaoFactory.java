package repository;

import repository.custom.impl.*;
import service.custom.impl.SupplierServiceImpl;
import util.DaoType;

public class DaoFactory {

    private static DaoFactory Instance;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return Instance == null ? Instance=new DaoFactory():Instance;
    }
    public <T extends SuperDao> T getDaoType(DaoType type){
        switch (type){
            case ADMIN:return (T) new AdminDaoImpl();
            case EMPLOYEE:return (T) new EmployeeDaoImpl();
            case PRODUCTS:return (T) new ProductsDaoImpl();
            case SUPPLIER:return (T) new SupplierDaoImpl();
            case ORDER:return (T) new OrderDaoImpl();
            case ORDERDETAILS:return (T) new OrderDetailsDaoImpl();
        }
        return null;
    }
}
