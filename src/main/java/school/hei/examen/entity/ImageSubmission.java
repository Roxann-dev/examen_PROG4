package school.hei.examen.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "image_submission")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class ImageSubmission {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, length = 255)
  private String fileName;

  @Column(nullable = false, length = 100)
  private String email;

  @CreationTimestamp
  @Column(nullable = false)
  private Instant submittedAt;
}
