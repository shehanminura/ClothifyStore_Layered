package service.custom;

import dto.Order;
import service.SuperService;

import java.sql.SQLException;

public interface OrderService extends SuperService {

    boolean addOrder(Order order) throws SQLException;
}
