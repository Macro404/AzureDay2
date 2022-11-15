package se.salt.jfs.jpaproducts.product;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import se.salt.jfs.jpaproducts.JfsLabJpaPostgresqlApplicationTests;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class APITests extends JfsLabJpaPostgresqlApplicationTests {

  private static ProductResponseDTO product;


  @BeforeAll
  static void setUp(@Autowired RestTemplate template, @Value("${server.port}") int port){
    String baseURL = "http://localhost:%s/api/products".formatted(port);
    CreateProductDTO prod = new CreateProductDTO("apiTestProd", "A sports equipment product", 100.0, 3 );
    ResponseEntity<ProductResponseDTO> response = template
      .postForEntity(baseURL, prod, ProductResponseDTO.class);
    assertThat(response.getStatusCode().value()).isEqualTo(201);
    product = response.getBody();
  }

  @AfterAll
  static void tearDown(@Autowired ProductRepository repo){
    repo.deleteProduct(product.id());
  }


  @Test
  @Order(1)
  void shouldListProducts(){
    //act
    ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:%s/api/products".formatted(port), List.class);
    List products = response.getBody();

    //assert
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(products).hasSize(1);
  }

  @Test
  @Order(1)
  void shouldListProductsByGroup(){
    //act
    ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:%s/api/products?group=Toys".formatted(port), List.class);
    List products = response.getBody();

    //assert
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(products).hasSize(0);
  }

  @Test
  @Order(1)
  void shouldGetProduct(){
    String baseURL = "http://localhost:%s/api/products/%d".formatted(port, product.id());
    ResponseEntity<ProductResponseDTO> response = restTemplate.exchange(baseURL, HttpMethod.GET, HttpEntity.EMPTY, ProductResponseDTO.class);
    ProductResponseDTO product = response.getBody();
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(product.id()).isEqualTo(product.id());
  }

  @Test
  @Order(2)
  void shouldAddProductWithPost(){

    //assert
    assertThat(product.id()).isNotNull();
    assertThat(product.productGroup()).isEqualTo("Sports Gear");
  }


  @Test
  @Order(3)
  void shouldReplaceExistingProductWithPut(){
    //arrange
    String baseURL = "http://localhost:%s/api/products/%d".formatted(port, product.id());
    CreateProductDTO prod = new CreateProductDTO("apiTestProd", "Its Food", null, 1 );

    //act
    ResponseEntity<ProductResponseDTO> response = restTemplate
      .exchange(baseURL, HttpMethod.PUT,  new HttpEntity<CreateProductDTO>(prod),  ProductResponseDTO.class);
    ProductResponseDTO product = response.getBody();
    //assert
    assertThat(response.getStatusCode().value()).isEqualTo(202);
    assertThat(product.id()).isNotNull();
    assertThat(product.price()).isNull();
    assertThat(product.productGroup()).isEqualTo("Food");
  }


  @Test
  @Order(4)
  void shouldUpdateExistingProductWithPatch(){
    //arrange
    String baseURL = "http://localhost:%s/api/products/%d".formatted(port, product.id());
    CreateProductDTO prod = new CreateProductDTO(null, "Patched", null, null);

    //act
    ResponseEntity<ProductResponseDTO> response = restTemplate
      .exchange(baseURL, HttpMethod.PATCH,  new HttpEntity<CreateProductDTO>(prod),  ProductResponseDTO.class);
    ProductResponseDTO product = response.getBody();
    //assert
    assertThat(response.getStatusCode().value()).isEqualTo(202);
    assertThat(product.id()).isNotNull();
    assertThat(product.name()).isEqualTo("apiTestProd");
    assertThat(product.description()).isEqualTo("Patched");
  }


  @Test
  @Order(5)
  void shouldDeleteItemWithDelete(){
    String baseURL = "http://localhost:%s/api/products/%d".formatted(port, product.id());
    ResponseEntity<Void> exchange = restTemplate.exchange(baseURL, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
    assertThat(exchange.getStatusCode().value()).isEqualTo(204);
  }

  @Test
  @Order(6)
  void shouldGet204WhenDeletingSameItemTwice(){
    String baseURL = "http://localhost:%s/api/products/%d".formatted(port, product.id());
    ResponseEntity<Void> exchange = restTemplate.exchange(baseURL, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
    assertThat(exchange.getStatusCode().value()).isEqualTo(204);
  }



}
