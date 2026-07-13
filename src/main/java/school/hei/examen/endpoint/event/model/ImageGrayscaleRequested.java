package school.hei.examen.endpoint.event.model;

import java.time.Duration;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "imageBase64")
public class ImageGrayscaleRequested extends PojaEvent {
  private UUID submissionId;
  private String fileName;
  private String email;
  private String contentType;
  private String imageBase64;

  @Override
  public Duration maxConsumerDuration() {
    return Duration.ofSeconds(120);
  }

  @Override
  public Duration maxConsumerBackoffBetweenRetries() {
    return Duration.ofSeconds(30);
  }
}
