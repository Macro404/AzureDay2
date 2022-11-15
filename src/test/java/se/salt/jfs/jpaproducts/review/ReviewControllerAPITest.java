package se.salt.jfs.jpaproducts.review;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import se.salt.jfs.jpaproducts.JfsLabJpaPostgresqlApplicationTests;
import se.salt.jfs.jpaproducts.product.CreateProductDTO;
import se.salt.jfs.jpaproducts.product.JpaProductRepository;
import se.salt.jfs.jpaproducts.product.ProductResponseDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewControllerAPITest extends JfsLabJpaPostgresqlApplicationTests {

  private static ProductResponseDTO sportsProduct;
  private static ProductResponseDTO foodProduct;


  @BeforeAll
  static void setUp(@Autowired RestTemplate template, @Value("${server.port}") int port) {
    String baseURL = "http://localhost:%s/api/products".formatted(port);
    CreateProductDTO prod = new CreateProductDTO("reviewTestProd", "A sports equipment product", 100.0, 3);
    ResponseEntity<ProductResponseDTO> response = template
      .postForEntity(baseURL, prod, ProductResponseDTO.class);
    assertThat(response.getStatusCode().value()).isEqualTo(201);
    sportsProduct = response.getBody();

    CreateProductDTO prod2 = new CreateProductDTO("revireTestProd2", "A Food product", 15.0, 1);
    response = template
      .postForEntity(baseURL, prod2, ProductResponseDTO.class);
    foodProduct = response.getBody();
  }

  @AfterAll
  static void tearDown(@Autowired JpaProductRepository repo, @Autowired MongoDBRepository mongoDbrepo) {
    repo.deleteAll();
    mongoDbrepo.deleteAll();
  }


  @Test
  @Order(1)
  void createReviewAddsReview(){
    CreateReviewDTO dto = new CreateReviewDTO(sportsProduct.id(),"Works" , 3 , "Ben Johnson", "Makes me run faster but also anger issues " );
    ResponseEntity<ReviewResponseDTO> exchange = restTemplate.exchange("http://localhost:%s/api/reviews".formatted(port), HttpMethod.POST, new HttpEntity<>(dto), ReviewResponseDTO.class);
    ReviewResponseDTO body = exchange.getBody();

    assertThat(exchange.getStatusCode().value()).isEqualTo(201);
    assertThat(exchange.getHeaders().getFirst("location")).endsWith(body.id());
  }

  @Test
  @Order(2)
  void shouldListReviews() {
    ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:%s/api/reviews".formatted(port), List.class);
    List reviews = response.getBody();
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(reviews).hasSize(1);

  }

  @Test
  @Order(3)
  void shouldListOnlyFoodReviews() {

    CreateReviewDTO dto = new CreateReviewDTO(foodProduct.id(),"Nice" , 5 , "Jamie Oliver", "Winner winner chicken dinner!" );
    ResponseEntity<ReviewResponseDTO> exchange = restTemplate.exchange("http://localhost:%s/api/reviews".formatted(port), HttpMethod.POST, new HttpEntity<>(dto), ReviewResponseDTO.class);
    ReviewResponseDTO foodReview = exchange.getBody();
    ParameterizedTypeReference<List<ReviewResponseDTO>> parameterizedTypeReference = new ParameterizedTypeReference<>(){};
    ResponseEntity<List<ReviewResponseDTO>> response = restTemplate.exchange("http://localhost:%s/api/reviews?group=Food".formatted(port), HttpMethod.GET, null, parameterizedTypeReference);
    List<ReviewResponseDTO> reviews = response.getBody();
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(reviews).hasSize(1);
    assertThat(reviews).contains(foodReview);

  }

  @Test
  @Order(3)
  void shouldListOnlyReviewsByProductId() {

    ParameterizedTypeReference<List<ReviewResponseDTO>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};
    ResponseEntity<List<ReviewResponseDTO>> response = restTemplate.exchange("http://localhost:%s/api/reviews?productId=%s".formatted(port, sportsProduct.id()), HttpMethod.GET, null, parameterizedTypeReference);
    List<ReviewResponseDTO> reviews = response.getBody();
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(reviews).hasSize(1);
    assertThat(reviews.get(0).productId()).isEqualTo(sportsProduct.id());

  }

  @Test
  @Order(4)
  void shouldGetNewReviewObjectWhenUpvoting() {

    // arrange
    CreateReviewDTO dto = new CreateReviewDTO(foodProduct.id(),"Nice" , 5 , "Jamie Oliver", "Winner winner chicken dinner!" );
    ResponseEntity<ReviewResponseDTO> exchange = restTemplate.exchange("http://localhost:%s/api/reviews".formatted(port), HttpMethod.POST, new HttpEntity<>(dto), ReviewResponseDTO.class);
    ReviewResponseDTO foodReview = exchange.getBody();

    //act

    ResponseEntity<ReviewResponseDTO> response = restTemplate.exchange("http://localhost:%s/api/reviews/%s/upvote".formatted(port, foodReview.id()), HttpMethod.POST, new HttpEntity<>(new UpvoteDTO("e2e")), ReviewResponseDTO.class);
    ReviewResponseDTO review = response.getBody();

    //assert

    assertThat(response.getStatusCode().value()).isEqualTo(202);
    assertThat(review.upvotes()).hasSize(1);

  }

  @Test
  @Order(6)
  void shouldRemoveNameFromUpvoting() {

    // arrange
    CreateReviewDTO dto = new CreateReviewDTO(foodProduct.id(),"Interesting" , 2 , "Jamie Oliver", "Looked a bit dodge..!" );
    ResponseEntity<ReviewResponseDTO> exchange = restTemplate.exchange("http://localhost:%s/api/reviews".formatted(port), HttpMethod.POST, new HttpEntity<>(dto), ReviewResponseDTO.class);
    ReviewResponseDTO foodReview = exchange.getBody();

    restTemplate.exchange("http://localhost:%s/api/reviews/%s/upvote".formatted(port, foodReview.id()), HttpMethod.POST, new HttpEntity<>(new UpvoteDTO("anonymous")), ReviewResponseDTO.class);
    ResponseEntity<ReviewResponseDTO> response = restTemplate.exchange("http://localhost:%s/api/reviews/%s/upvote".formatted(port, foodReview.id()), HttpMethod.POST, new HttpEntity<>(new UpvoteDTO("e2e")), ReviewResponseDTO.class);
    ReviewResponseDTO review = response.getBody();

    assertThat(response.getStatusCode().value()).isEqualTo(202);
    assertThat(review.upvotes()).hasSize(2);
    //act
    ResponseEntity<ReviewResponseDTO> deleteResponse = restTemplate.exchange("http://localhost:%s/api/reviews/%s/upvote".formatted(port, foodReview.id()), HttpMethod.DELETE, new HttpEntity<>(new UpvoteDTO("anonymous")), ReviewResponseDTO.class);


    //assert

    assertThat(deleteResponse.getStatusCode().value()).isEqualTo(202);
    assertThat(deleteResponse.getBody().upvotes()).hasSize(1);

  }


}