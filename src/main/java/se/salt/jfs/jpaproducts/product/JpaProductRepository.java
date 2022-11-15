package se.salt.jfs.jpaproducts.product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.salt.jfs.jpaproducts.productgroup.ProductGroup;

import java.util.List;

public interface JpaProductRepository extends CrudRepository<Product, Long> {

  List<Product> findByName(String name);

  @Query(value = "SELECT p FROM Product p WHERE p.productGroup in :groups ")
  List<Product> listByGroup(@Param("groups") List<ProductGroup> groups);

}
