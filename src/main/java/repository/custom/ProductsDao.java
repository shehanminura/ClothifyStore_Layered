package repository.custom;

import dto.OrderDetail;
import entity.ProductsEntity;
import repository.CrudRepository;

import java.util.List;

public interface ProductsDao extends CrudRepository<ProductsEntity,String> {
    boolean updateproduct(List<OrderDetail> orderDetails);
}
