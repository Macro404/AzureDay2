package se.salt.jfs.jpaproducts.productgroup;

import java.util.List;

public interface IProductGroupRepository {
  List<ProductGroup> listProductGroups();
  List<ProductGroup> findGroups(String[] groupNames);
}
