package service.custom.impl;

import dto.Order;
import entity.OrderEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.OrderDao;
import service.custom.OrderService;
import util.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;


public class OrderServiceImpl implements OrderService {
    OrderDao dao= DaoFactory.getInstance().getDaoType(DaoType.ORDER);
    @Override
    public boolean addOrder(Order order) throws SQLException {
        OrderEntity map = new ModelMapper().map(order, OrderEntity.class);
        return dao.addOrder(map);
    }
}
