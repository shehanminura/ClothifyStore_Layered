package repository.custom;

import entity.ProductsEntity;
import repository.CrudRepository;

public interface ProductsDao extends CrudRepository<ProductsEntity,String> {
}
