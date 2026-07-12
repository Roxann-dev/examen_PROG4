package school.hei.examen.conf;

import org.springframework.test.context.DynamicPropertyRegistry;
import school.hei.examen.PojaGenerated;

@PojaGenerated
public class BucketConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.s3.bucket", () -> "dummy-bucket");
  }
}
