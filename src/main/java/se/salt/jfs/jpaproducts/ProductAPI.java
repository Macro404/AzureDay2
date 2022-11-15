package se.salt.jfs.jpaproducts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"se.salt.jfs.jpaproducts.product", "se.salt.jfs.jpaproducts.productgroup"})
@EnableMongoRepositories(basePackages = "se.salt.jfs.jpaproducts.review")
public class ProductAPI {

	private static final Logger log = LoggerFactory.getLogger(ProductAPI.class);
	public static void main(String[] args) {
		SpringApplication.run(ProductAPI.class, args);
	}

}
