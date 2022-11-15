package se.salt.jfs.jpaproducts.product;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import se.salt.jfs.jpaproducts.JfsLabJpaPostgresqlApplicationTests;
import se.salt.jfs.jpaproducts.productgroup.ProductGroup;
import se.salt.jfs.jpaproducts.productgroup.ProductGroupService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest extends JfsLabJpaPostgresqlApplicationTests  {

  @Autowired
  ProductService service;

  @Autowired
  ProductGroupService groupService;


  private static Product theProduct;

  @BeforeEach
  void setUp() {
    if (theProduct == null) { // initialise only once
      System.out.println("Adding");
      theProduct = new Product("A test product", "testprod1", 100.0);
      theProduct.setProductGroup(groupService.listProductGroups().get(0));
      theProduct = service.saveProduct(theProduct);
    }

  }

  @AfterAll
  static void tearDown(@Autowired ProductService service){
    service.deleteProduct(theProduct.getId());
  }

  @Test
  @Order(1)
  void shouldAddProductToDatabase() {
    //Arrange

    // act

    //assert
    List<Product> products = service.findByName(theProduct.getName());
    assertThat(products).hasSize(1);
    assertThat(products.get(0).getId()).isEqualTo(theProduct.getId());
  }

  @Test
  @Order(1)
  void shouldListAllProductsInTheDb() {
    //Arrange

    // act

    //assert
    List<Product> products = service.findByName(theProduct.getName());
    assertThat(products).hasSize(1);
  }

  @Test
  @Order(2)
  void shouldReplaceProductInDatabase() {
    //Arrange

    Product replacement = new Product("Updated name", null, 100.0);
    replacement.setId(theProduct.getId());
    replacement.setProductGroup(groupService.listProductGroups().get(0));
    // act
    service.saveProduct(replacement);

    //assert
    List<Product> products = service.findByName(replacement.getName());
    assertThat(products).hasSize(1);
    assertThat(products.get(0).getId()).isEqualTo(theProduct.getId());
    assertThat(products.get(0).getDescription()).isNull();
  }


  @Test
  @Order(3)
  void shouldUpdateProductInDatabase() {
    //Arrange
    Product prodToUpdate = new Product(null, "Description is back", 100.0);
    prodToUpdate.setId(theProduct.getId());
    // act
    service.updateProductData(prodToUpdate);

    //assert
    List<Product> products = service.findByName("Updated name");
    assertThat(products).hasSize(1);
    assertThat(products.get(0).getId()).isEqualTo(theProduct.getId());
    assertThat(products.get(0).getDescription()).isEqualTo("Description is back");
  }

  @Test
  @Order(4)
  void shouldDeleteProduct() {
    // arrange

    // act
    service.removeProduct(theProduct);

    //assert
    assertThat(service.getAllProducts()).hasSize(0);
    theProduct = null;
  }

  @Test
  @Order(5)
  void shouldHandleDeleteOfNonExistingProduct() {
    // arrange
    // act
    service.removeProduct(new Product());

    //assert
    assertThat(service.getAllProducts()).hasSize(1);
  }


  @Test
  void shouldUpdateProductGroup(){
    ProductGroup productGroup = groupService.listProductGroups().get(1);

    theProduct.setProductGroup(productGroup);
    service.saveProduct(theProduct);

    Product byId = service.findById(theProduct.getId());

    assertThat(byId.getProductGroup().getId()).isEqualTo(productGroup.getId());
  }

  @Test
  void shouldFindProductsByProductGroups(){
    //arrange

    Product newProduct = new Product("Another product", "testprod2", 100.0);
    newProduct.setProductGroup(groupService.listProductGroups().get(1));
    service.saveProduct(newProduct);

    List<ProductGroup> productGroups = groupService.listProductGroups();

    List<Product> products = service.findByGroup(List.of(productGroups.get(0), productGroups.get(2)));
    assertThat(products).hasSize(1);

    products = service.findByGroup(List.of(productGroups.get(0), productGroups.get(1)));
    assertThat(products).hasSize(2);

    service.deleteProduct(newProduct.getId());

  }

}

