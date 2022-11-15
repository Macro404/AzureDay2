package se.salt.jfs.jpaproducts.review;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import se.salt.jfs.jpaproducts.JfsLabJpaPostgresqlApplicationTests;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductReviewRepositoryTest extends JfsLabJpaPostgresqlApplicationTests {

  @Autowired
  IProductReviewRepository repo;

  @Autowired
  MongoDBRepository mongo;

  static Review review; 
  
  @BeforeEach
  void setUp(){
    review = new Review("Keychain", 1, 3, "johan" );
    review.setId(UUID.randomUUID().toString());
    review.setProductGroup("food");
    review = repo.saveProductReview(review);

    Review review2 = new Review("Ferrari", 2, 5, "Niki Lauda" );
    review2.setId(UUID.randomUUID().toString());
    review2.setProductGroup("toys");

    review = repo.saveProductReview(review2);
    
  }

  @AfterEach
  void tearDown(@Autowired MongoDBRepository mongoRepo) {
    mongoRepo.deleteAll();
  }

  @Test
  void shouldACreateNewReview(){
    //arrange

    // act
    List<Review> reviews = repo.listReviews();
    // assert
    assertThat(reviews).hasSize(2);
    assertThat(reviews.get(0).getDocumentId()).isNotNull();
    assertThat(reviews.get(0).getId()).isNotNull();
    assertThat(reviews.get(0).getRating()).isEqualTo(3);

  }

  @Test
  void shouldFindAllReviewsForGroup(){
    //arrange

    // act
    List<Review> reviews = repo.getReviewsForGroups(new String[]{"toys"});
    assertThat(reviews).hasSize(1);

  }

  @Test
  void shouldFindBothReviewsForProduct(){
    repo.saveProductReview(new Review("tst", 3, 5, "me"));
    repo.saveProductReview(new Review("tst", 2, 2, "someoneElse"));

    List<Review> reviewsForProductIds = repo.getReviewsForProductIds(new long[]{2, 3});
    assertThat(reviewsForProductIds).hasSize(3);

  }

  @Test
  void shouldGetReviewByReviewUUID(){

    Review review = repo.saveProductReview(new Review("tst", 3, 5, "me"));
    Review reviewById = repo.getReviewById(review.getId());
    assertThat(reviewById.getId()).isEqualTo(review.getId());


  }
}