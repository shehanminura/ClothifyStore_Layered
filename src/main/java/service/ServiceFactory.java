package service;

import repository.custom.impl.EmployeeDaoImpl;
import service.custom.EmployeeService;
import service.custom.impl.*;
import util.ServiceType;

public class ServiceFactory {

    private static ServiceFactory Instance;

    ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return Instance==null ? Instance= new ServiceFactory():Instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType type){
        switch (type){
            case ADMIN:return (T) new AdminServiceImpl();
            case EMPLOYEE:return (T) new EmployeeServiceImpl();
            case PRODUCTS:return (T) new ProductsServiceImpl();
            case SUPPLIER:return (T) new SupplierServiceImpl();
            case ORDER:return (T) new OrderServiceImpl();
            case ORDERDETAILS:return (T) new OrderDetailsServiceImpl();
        }
        return null;
    }
}
