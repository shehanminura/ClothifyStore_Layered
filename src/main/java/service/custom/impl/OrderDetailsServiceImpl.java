package service.custom.impl;

import dto.OrderDetail;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.OrderDetailsDao;
import service.custom.OrderDetailsService;
import util.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsServiceImpl implements OrderDetailsService {
    OrderDetailsDao dao = DaoFactory.getInstance().getDaoType(DaoType.ORDERDETAILS);

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException {
        ArrayList<OrderDetail> objectArrayList = new ArrayList<>();
        for (Object detail : dao.getAll()){
            OrderDetail map = new ModelMapper().map(detail, OrderDetail.class);
            objectArrayList.add(map);
        }
        return objectArrayList;
    }
}
