package school.hei.examen.service;

import java.util.Base64;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import school.hei.examen.DTO.ImageSubmissionResponse;
import school.hei.examen.endpoint.event.EventProducer;
import school.hei.examen.endpoint.event.model.ImageGrayscaleRequested;
import school.hei.examen.entity.ImageSubmission;
import school.hei.examen.repository.ImageSubmissionRepository;

@Service
@AllArgsConstructor
public class ImageSubmissionService {

  private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/png");

  private final ImageSubmissionRepository imageSubmissionRepository;
  private final EventProducer<ImageGrayscaleRequested> eventProducer;

  @SneakyThrows
  public ImageSubmissionResponse submit(MultipartFile file, String email) {
    validate(file, email);

    ImageSubmission saved =
        imageSubmissionRepository.save(
            ImageSubmission.builder().fileName(file.getOriginalFilename()).email(email).build());

    eventProducer.accept(
        List.of(
            ImageGrayscaleRequested.builder()
                .submissionId(saved.getId())
                .fileName(saved.getFileName())
                .email(email)
                .contentType(file.getContentType())
                .imageBase64(Base64.getEncoder().encodeToString(file.getBytes()))
                .build()));

    return toResponse(saved);
  }

  private void validate(MultipartFile file, String email) {
    if (file == null || file.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le fichier image est requis");
    }
    String contentType = file.getContentType();
    if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Seuls les formats JPEG et PNG sont acceptés");
    }
    if (email == null || email.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'email est requis");
    }
  }

  private ImageSubmissionResponse toResponse(ImageSubmission entity) {
    return ImageSubmissionResponse.builder()
        .id(entity.getId())
        .fileName(entity.getFileName())
        .email(entity.getEmail())
        .submittedAt(entity.getSubmittedAt())
        .build();
  }

  public List<ImageSubmissionResponse> findAll() {
    return imageSubmissionRepository.findAll().stream().map(this::toResponse).toList();
  }
}
