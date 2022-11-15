package se.salt.jfs.jpaproducts.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProductReviewRepository implements IProductReviewRepository {

  @Autowired
  MongoDBRepository repo;

  @Override
  public Review saveProductReview(Review review) {
    if(review.getId() == null) {
      review.setId(UUID.randomUUID().toString());
    }
    return repo.save(review);
  }


  @Override
  public List<Review> listReviews(){
    return repo.findAll();
  }

  @Override
  public List<Review> getReviewsForGroups(String[] productGroups) {
    return repo.findProductsByProductGroups(productGroups);
  }

  @Override
  public List<Review> getReviewsForProductIds(long[] ids){
    return repo.findProductsWhereProductIdIn(ids);
  }

  @Override
  public Review getReviewById(String reviewId) {
    return repo.findByIdReviewId(reviewId);
  }
}
