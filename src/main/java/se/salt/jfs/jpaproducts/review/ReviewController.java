package se.salt.jfs.jpaproducts.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.salt.jfs.jpaproducts.product.Product;
import se.salt.jfs.jpaproducts.product.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {


  @Autowired
  private ReviewService service;

  @Autowired
  ProductService productService;

  @GetMapping
  public ResponseEntity<List<ReviewResponseDTO>> listReviews(@RequestParam(required = false)  long[] productId, @RequestParam(name="group", required = false) String[] productGroups){

    List<Review> reviews = service.listReviews(productId, productGroups);
    List<ReviewResponseDTO> reviewResponseDTOS = reviews.stream().map(review ->
      buildDTO(review)
    ).toList();

    return ResponseEntity.ok(reviewResponseDTOS);

  }

  private ReviewResponseDTO buildDTO(Review review) {
    return new ReviewResponseDTO(review.getId(), review.getProductName(), review.getProductGroup()
      , review.getProductId(), review.getTitle(), review.getRating(), review.getReviewer(), review.getDescription(), review.getUpvotes(), review.getUpvotes().size());
  }


  @PostMapping
  public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody CreateReviewDTO dto) {
    if(dto.productId() <= 0 || dto.title() == null || dto.rating() <= 0 || dto.reviewer() == null) {
      return ResponseEntity.badRequest().build();

    }
    Product byId = productService.findById(dto.productId());
    Review review = new Review();
    review.setReviewer(dto.reviewer());
    review.setTitle(dto.title());
    review.setDescription(dto.description());
    review.setRating(dto.rating());
    review.setProductId(dto.productId());
    review.setProductName(byId.getName());
    review.setProductGroup(byId.getProductGroup().getName());

    Review created = service.saveReview(review);
    return ResponseEntity.created( URI.create("/api/reviews/"+created.getId().toString())).body(buildDTO(created));

  }

  @PostMapping("/{reviewId}/upvote")
  public ResponseEntity<ReviewResponseDTO> upvoteReview(@PathVariable String reviewId, @RequestBody UpvoteDTO upvote) {
    if(upvote.username() == null ) {
      return ResponseEntity.badRequest().build();
    }
    Review upvoted = service.upvoteReview(reviewId, upvote.username());
    ReviewResponseDTO dto = buildDTO(upvoted);
    return ResponseEntity.accepted().body(dto);

  }

  @DeleteMapping("/{reviewId}/upvote")
  public ResponseEntity<ReviewResponseDTO> removeUpvote(@PathVariable String reviewId, @RequestBody UpvoteDTO upvote) {
    if(upvote.username() == null ) {
      return ResponseEntity.badRequest().build();
    }
    Review upvoted = service.removeUpvoteFromReview(reviewId, upvote.username());
    ReviewResponseDTO dto = buildDTO(upvoted);
    return ResponseEntity.accepted().body(dto);

  }

}
