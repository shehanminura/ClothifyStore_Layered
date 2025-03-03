package service.custom;

import dto.OrderDetail;
import service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsService extends SuperService {
    ArrayList<OrderDetail> getAll() throws SQLException;
}
