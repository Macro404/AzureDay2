package se.salt.jfs.jpaproducts.productgroup;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaProductGroupRepository extends CrudRepository<ProductGroup, Long> {

  List<ProductGroup> findByNameIn(String[] names);
}
