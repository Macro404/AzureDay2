package se.salt.jfs.jpaproducts.review;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Review {

  @Id
  private String documentId;

  @Field("id")
  private String id;
  private String productName;
  private String productGroup;
  private long productId;
  private String title;
  private int rating;
  private String reviewer;
  private String description;
  private List<String> upvotes = new ArrayList<>();

  public Review() {
  }

  public Review(String productName, long productId, int rating, String reviewer) {
    this(productName, null, productId, null, rating, reviewer, null, new ArrayList<>());
  }

  public Review(String productName, String productGroup, long productId, String title, int rating, String reviewer, String description, List<String> upvotes) {
    this.productName = productName;
    this.productGroup = productGroup;
    this.productId = productId;
    this.title = title;
    this.rating = rating;
    this.reviewer = reviewer;
    this.description = description;
    this.upvotes = upvotes;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getReviewer() {
    return reviewer;
  }

  public void setReviewer(String reviewer) {
    this.reviewer = reviewer;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  public String getProductGroup() {
    return productGroup;
  }

  public void setProductGroup(String productGroup) {
    this.productGroup = productGroup;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getUpvotes() {
    return upvotes;
  }

  public void setUpvotes(List<String> upvotes) {
    this.upvotes = upvotes;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }


  @Override
  public String toString() {
    return "Review{" +
      "id='" + id.toString() + '\'' +
      ", productName='" + productName + '\'' +
      ", productId=" + productId +
      ", rating=" + rating +
      ", username='" + reviewer + '\'' +
      '}';
  }
}
