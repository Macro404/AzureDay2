package se.salt.jfs.jpaproducts.productgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductGroupRepository implements IProductGroupRepository {


  @Autowired
  private JpaProductGroupRepository repo;

  @Override
  public List<ProductGroup> listProductGroups(){
    return Streamable.of(repo.findAll()).toList();
  }


  public List<ProductGroup> findGroups(String[] groupNames) {
    return repo.findByNameIn(groupNames);
  }
}
