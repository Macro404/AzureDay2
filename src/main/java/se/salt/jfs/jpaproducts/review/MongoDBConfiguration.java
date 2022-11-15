package se.salt.jfs.jpaproducts.review;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;


@Configuration
@Profile("!test")
public class MongoDBConfiguration extends AbstractMongoClientConfiguration {

  Logger logger = LoggerFactory.getLogger(MongoDBConfiguration.class.getName());

  @Value("${spring.data.mongodb.uri}")
  String mongoURI;

  @Value("${spring.data.mongodb.database}")
  String dbName;

  @Override
  protected String getDatabaseName() {
    return dbName;
  }

  @Override
  public MongoClient mongoClient() {
    ConnectionString connectionString = new ConnectionString(mongoURI);
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
      .applyConnectionString(connectionString)
      .uuidRepresentation(UuidRepresentation.STANDARD)
      .build();

    return MongoClients.create(mongoClientSettings);
  }
}
