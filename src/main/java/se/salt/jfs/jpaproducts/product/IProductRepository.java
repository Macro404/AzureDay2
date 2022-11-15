package se.salt.jfs.jpaproducts.product;

import se.salt.jfs.jpaproducts.productgroup.ProductGroup;

import java.util.List;

public interface IProductRepository {
  List<Product> listProducts();

  List<Product> listAllProducts();

  Product getById(Long id);

  Product saveProduct(Product newProduct);

  void deleteProduct(Product product);

  void deleteProduct(Long productId);

  List<Product> findProductByName(String productName);

  List<Product> productsForGroups(List<ProductGroup> groups);
}
