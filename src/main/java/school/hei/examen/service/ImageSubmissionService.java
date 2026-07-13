package school.hei.examen.service;

import java.util.Base64;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.hei.examen.DTO.ImageSubmissionRequest;
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

  public ImageSubmissionResponse submit(ImageSubmissionRequest request) {
    validate(request);

    ImageSubmission saved =
        imageSubmissionRepository.save(
            ImageSubmission.builder()
                .fileName(request.getFileName())
                .email(request.getEmail())
                .build());

    eventProducer.accept(
        List.of(
            ImageGrayscaleRequested.builder()
                .submissionId(saved.getId())
                .fileName(saved.getFileName())
                .email(request.getEmail())
                .contentType(request.getContentType())
                .imageBase64(request.getFileBase64())
                .build()));

    return toResponse(saved);
  }

  public List<ImageSubmissionResponse> findAll() {
    return imageSubmissionRepository.findAll().stream().map(this::toResponse).toList();
  }

  private void validate(ImageSubmissionRequest request) {
    if (request.getFileBase64() == null || request.getFileBase64().isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le fichier image est requis");
    }
    if (!ALLOWED_CONTENT_TYPES.contains(request.getContentType())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Seuls les formats JPEG et PNG sont acceptés");
    }
    try {
      Base64.getDecoder().decode(request.getFileBase64());
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le contenu Base64 est invalide");
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
}
