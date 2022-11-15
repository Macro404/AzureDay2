package se.salt.jfs.jpaproducts.review;

import java.util.List;

public record CreateReviewDTO(
                              long productId,
                              String title,
                              int rating,
                              String reviewer,
                              String description) {
}
