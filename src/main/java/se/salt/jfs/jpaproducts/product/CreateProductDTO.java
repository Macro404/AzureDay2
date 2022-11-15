package se.salt.jfs.jpaproducts.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateProductDTO(@JsonProperty String name, @JsonProperty  String description, @JsonProperty  Double price, @JsonProperty Integer group) {
}
