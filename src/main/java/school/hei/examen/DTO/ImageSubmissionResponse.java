package school.hei.examen.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class ImageSubmissionResponse {
  @NotNull private UUID id;
  private String fileName;
  private String email;
  @NotNull private Instant submittedAt;
}
