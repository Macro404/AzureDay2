package se.salt.jfs.jpaproducts.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.salt.jfs.jpaproducts.productgroup.ProductGroup;
import se.salt.jfs.jpaproducts.productgroup.ProductGroupRepository;

import java.util.List;

@Service
public class ProductService {

  @Autowired
  IProductRepository productRepo;


  public ProductService() {
  }

  public ProductService(IProductRepository productRepo) {
    this.productRepo = productRepo;
  }

  List<Product> getAllProducts() {
    return productRepo.listAllProducts();
  }

  public Product findById(Long id) {
    return productRepo.getById(id);
  }

  Product saveProduct(Product newProduct) {
    return productRepo.saveProduct(newProduct);
  }

  List<Product> findByName(String name){
    return productRepo.findProductByName(name);
  }

  public Product updateProductData(Product newProductData) {
    Product storedProduct = findById(newProductData.getId());
    if(storedProduct == null) {
      return null;
    }
    if(newProductData.getName() != null) {
      storedProduct.setName(newProductData.getName());
    }
    if(newProductData.getPrice() != null && newProductData.getPrice() > 0 ) {
      storedProduct.setPrice(newProductData.getPrice());
    }
    if(newProductData.getDescription() != null) {
      storedProduct.setDescription(newProductData.getDescription());
    }
    if(newProductData.getProductGroup() != null) {
      storedProduct.setProductGroup(newProductData.getProductGroup());
    }

    return  productRepo.saveProduct(storedProduct);
  }

  public void removeProduct(Product product){
    productRepo.deleteProduct(product);
  }

  public List<Product> findByGroup(List<ProductGroup> groups) {
    return productRepo.productsForGroups(groups);

  }
  
  public void deleteProduct(Long productId) {
    productRepo.deleteProduct(productId);
  }

}