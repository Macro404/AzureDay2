package se.salt.jfs.jpaproducts.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import se.salt.jfs.jpaproducts.productgroup.ProductGroup;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class ProductRepository implements IProductRepository {

  Logger log = Logger.getLogger(ProductRepository.class.getName());

  @Autowired
  JpaProductRepository repo;


  public ProductRepository() {
  }

  public ProductRepository(JpaProductRepository repo) {
    this.repo = repo;
  }

  @Override
  public List<Product> listProducts() {
    return Streamable.of(repo.findAll()).toList();
  }

  @Override
  public List<Product> listAllProducts() {
    Iterable<Product> all = repo.findAll();
    List<Product> products = Streamable.of(all).toList();
    return products;
  }

  @Override
  public Product getById(Long id) {
    Optional<Product> byId = repo.findById(id);
    return byId.orElse(null);
  }

  @Override
  public Product saveProduct(Product newProduct) {
    if(newProduct != null) {
      return repo.save(newProduct);
    }
    return null;
  }

  @Override
  public void deleteProduct(Product product) {
    if(product != null) {
      repo.delete(product);
    }
  }

  @Override
  public void deleteProduct(Long productId) {
    try {
      if(productId != null) {
        repo.deleteById(productId);
      }
    } catch (EmptyResultDataAccessException ex) {
      log.info("Attempt to delete Product %d that doesn't exist ".formatted(productId));
    }
  }

  @Override
  public List<Product> findProductByName(String productName) {
      return repo.findByName(productName);
  }

  @Override
  public List<Product> productsForGroups(List<ProductGroup> groups) {
    return repo.listByGroup(groups);
  }

}
