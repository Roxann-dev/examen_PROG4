package school.hei.examen.conf;

import org.springframework.test.context.DynamicPropertyRegistry;
import school.hei.examen.PojaGenerated;

@PojaGenerated
public class EmailConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.ses.source", () -> "dummy-ses-source");
  }
}
