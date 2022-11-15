package se.salt.jfs.jpaproducts.review;

import java.util.List;

public record ReviewResponseDTO(
   String id,
   String productName,
   String productGroup,
   long productId,
   String title,
   int rating,
   String reviewer,
   String description,
   List<String> upvotes,
   int numberOfUpvotes
) {
}
