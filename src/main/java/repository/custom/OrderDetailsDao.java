package repository.custom;

import dto.Order;
import dto.OrderDetail;
import entity.OrderDetailEntity;
import repository.SuperDao;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDao extends SuperDao {
    List<OrderDetailEntity> getAll() throws SQLException;
    boolean addOrderDetail(List<OrderDetail> orderDetail) throws SQLException;

    }
