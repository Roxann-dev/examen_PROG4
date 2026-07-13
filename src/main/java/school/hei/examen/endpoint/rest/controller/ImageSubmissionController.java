package school.hei.examen.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import school.hei.examen.DTO.ImageSubmissionResponse;
import school.hei.examen.service.ImageSubmissionService;

@RestController
@AllArgsConstructor
public class ImageSubmissionController {
  private final ImageSubmissionService imageSubmissionService;

  @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ImageSubmissionResponse> submit(
      @RequestParam("file") MultipartFile file, @RequestParam("email") String email) {
    ImageSubmissionResponse response = imageSubmissionService.submit(file, email);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
