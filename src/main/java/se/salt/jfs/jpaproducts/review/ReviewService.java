package se.salt.jfs.jpaproducts.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

  @Autowired
  IProductReviewRepository repo;


  public List<Review> listReviews(long[] productId, String[] productGroups){
    if(productId != null && productId.length > 0 ){
      return repo.getReviewsForProductIds(productId);
    }

    if(productGroups != null && productGroups.length > 0 ) {
      return repo.getReviewsForGroups(productGroups);
    }


    return repo.listReviews();
  }

  public Review saveReview(Review review) {
    return repo.saveProductReview(review);
  }

  public Review upvoteReview(String reviewId, String reviewer) {
    Review reviewById = repo.getReviewById(reviewId);
    reviewById.getUpvotes().add(reviewer);
    return repo.saveProductReview(reviewById);
  }

  public Review removeUpvoteFromReview(String reviewId, String reviewer) {
    Review reviewById = repo.getReviewById(reviewId);
    List<String> upvotes = reviewById.getUpvotes().stream().filter(r -> !r.equals(reviewer)).toList();
    reviewById.setUpvotes(upvotes);
    return repo.saveProductReview(reviewById);
  }
}
