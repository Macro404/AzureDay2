package se.salt.jfs.jpaproducts.productgroup;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import se.salt.jfs.jpaproducts.JfsLabJpaPostgresqlApplicationTests;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductGroupRepositoryTest  extends JfsLabJpaPostgresqlApplicationTests  {


  @Autowired
  ProductGroupRepository repo;
  @Test
  void shouldListThreeProductGroups(){
    List<ProductGroup> productGroups = repo.listProductGroups();
    assertThat(productGroups).hasSize(3);
  }

  @Test
  void shouldGetSpecificGroups(){
    List<ProductGroup> productGroups = repo.findGroups(new String[]{"Food", "Toys"});
    assertThat(productGroups).hasSize(2);
  }
}