package se.salt.jfs.jpaproducts.product;

import se.salt.jfs.jpaproducts.productgroup.ProductGroup;

import javax.persistence.*;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private Double price;

  @ManyToOne(optional = false)
  @JoinColumn(name = "product_group_id")
  private ProductGroup productGroup;


  public Product() {
  }

  public Product(String description) {
    this.description = description;
  }

  Product(String name, String description, Double price) {
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }


  public ProductGroup getProductGroup() {
    return productGroup;
  }

  public void setProductGroup(ProductGroup productGroup) {
    this.productGroup = productGroup;
  }

  @Override
  public String toString() {
    return "Product{" +
      "id=" + id +
      ", description='" + description + '\'' +
      ", group='" + productGroup.getName() + '\'' +
      '}';
  }
}
