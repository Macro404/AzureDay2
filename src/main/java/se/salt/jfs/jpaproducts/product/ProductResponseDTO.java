package se.salt.jfs.jpaproducts.product;

public record ProductResponseDTO(Long id, String name, String description, Double price, String url, String productGroup ) {
}
