package se.salt.jfs.jpaproducts.productgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductGroupService {

  @Autowired
  public IProductGroupRepository repo;

  public ProductGroupService(IProductGroupRepository repo) {
    this.repo = repo;
  }

  public List<ProductGroup> listProductGroups(){
    return repo.listProductGroups();
  }

  public List<ProductGroup> getProductGroup(String[] groupNames) {
    return repo.findGroups(groupNames);
  }

}
