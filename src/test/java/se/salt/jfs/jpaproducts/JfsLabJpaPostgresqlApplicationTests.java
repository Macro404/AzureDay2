package se.salt.jfs.jpaproducts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles({"test"})
public abstract class JfsLabJpaPostgresqlApplicationTests {

  @Value("${server.port}")
  protected int port;
  @Autowired
  protected RestTemplate restTemplate;


}
