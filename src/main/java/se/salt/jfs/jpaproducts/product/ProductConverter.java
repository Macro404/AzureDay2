package se.salt.jfs.jpaproducts.product;

public class ProductConverter {

  static Product fromDto(CreateProductDTO dto){
    return new Product(dto.name(), dto.description(), dto.price());
  }

  static ProductResponseDTO toResponseDTO(Product entity) {
    return new ProductResponseDTO(entity.getId()
      , entity.getName()
      , entity.getDescription()
      , entity.getPrice()
      , "/api/products/%d".formatted(entity.getId())
      , entity.getProductGroup().getName());
  }
}
