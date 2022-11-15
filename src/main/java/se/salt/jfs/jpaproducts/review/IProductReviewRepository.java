package se.salt.jfs.jpaproducts.review;

import java.util.List;

public interface IProductReviewRepository {
  Review saveProductReview(Review review);

  List<Review> listReviews();

  List<Review> getReviewsForGroups(String[] productGroups);

  List<Review> getReviewsForProductIds(long[] ids);

  Review getReviewById(String reviewId);
}
