package school.hei.examen.endpoint.rest.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school.hei.examen.DTO.ImageSubmissionRequest;
import school.hei.examen.DTO.ImageSubmissionResponse;
import school.hei.examen.service.ImageSubmissionService;

@RestController
@AllArgsConstructor
public class ImageSubmissionController {
  private final ImageSubmissionService imageSubmissionService;

  @PostMapping("/images")
  public ResponseEntity<ImageSubmissionResponse> submit(
      @Valid @RequestBody ImageSubmissionRequest request) {
    ImageSubmissionResponse response = imageSubmissionService.submit(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/images")
  public ResponseEntity<List<ImageSubmissionResponse>> findAll() {
    return ResponseEntity.ok(imageSubmissionService.findAll());
  }
}
