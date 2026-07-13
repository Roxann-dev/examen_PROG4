package school.hei.examen.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import school.hei.examen.entity.ImageSubmission;

public interface ImageSubmissionRepository extends JpaRepository<ImageSubmission, UUID> {}
