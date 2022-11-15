package se.salt.jfs.jpaproducts.review;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MongoDBRepository extends MongoRepository<Review, String> {

  List<Review> findByProductId(int productId);

  @Query("{productGroup : { $in: ?0 } }")
  List<Review> findProductsByProductGroups(String[] groups);

  @Query("{productId : { $in: ?0 } }")
  List<Review> findProductsWhereProductIdIn(long[] productIds);

  @Query("{ id : { $eq: ?0 } }")
  Review findByIdReviewId(String uuidString);
}
