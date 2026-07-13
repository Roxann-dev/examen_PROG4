package school.hei.examen.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@ToString(exclude = "fileBase64")
public class ImageSubmissionRequest {

  @NotBlank private String fileName;

  @NotBlank private String contentType;

  @NotBlank private String fileBase64;

  @NotBlank @Email private String email;
}
