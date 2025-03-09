package repository.custom;

import dto.Order;
import entity.OrderEntity;
import repository.SuperDao;
import service.SuperService;

import java.sql.SQLException;

public interface OrderDao extends SuperDao {
    boolean addOrder(OrderEntity order) throws SQLException;


}
