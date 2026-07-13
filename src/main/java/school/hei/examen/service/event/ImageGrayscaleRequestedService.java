package school.hei.examen.service.event;

import jakarta.mail.internet.InternetAddress;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import school.hei.examen.endpoint.event.model.ImageGrayscaleRequested;
import school.hei.examen.file.bucket.BucketComponent;
import school.hei.examen.mail.Email;
import school.hei.examen.mail.Mailer;

@Service
@AllArgsConstructor
public class ImageGrayscaleRequestedService implements Consumer<ImageGrayscaleRequested> {

  private static final String IMAGES_PREFIX = "images/grayscale/";

  private final Mailer mailer;
  private final BucketComponent bucketComponent;

  @SneakyThrows
  @Override
  public void accept(ImageGrayscaleRequested requested) {
    byte[] originalBytes = Base64.getDecoder().decode(requested.getImageBase64());
    String formatName = "image/png".equals(requested.getContentType()) ? "png" : "jpg";

    BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(originalBytes));
    BufferedImage grayscaleImage = toGrayscale(originalImage);

    File tempFile = File.createTempFile(requested.getSubmissionId().toString(), "." + formatName);
    ImageIO.write(grayscaleImage, formatName, tempFile);

    String bucketKey = IMAGES_PREFIX + requested.getSubmissionId() + "." + formatName;
    bucketComponent.upload(tempFile, bucketKey);
    String presignedUrl = bucketComponent.presign(bucketKey, Duration.ofHours(24)).toString();

    tempFile.delete();

    InternetAddress recipientAddress = new InternetAddress(requested.getEmail());
    String subject = "Votre image en noir et blanc";
    String body =
        "Bonjour,\n\n"
            + "Votre image \""
            + requested.getFileName()
            + "\" a été convertie en noir et blanc.\n\n"
            + "Vous pouvez la consulter ici :\n"
            + presignedUrl
            + "\n\n"
            + "Ce lien est valable 24 heures.\n\n"
            + "À bientôt !";

    mailer.accept(new Email(recipientAddress, List.of(), List.of(), subject, body, List.of()));
  }

  private BufferedImage toGrayscale(BufferedImage original) {
    BufferedImage grayImage =
        new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
    Graphics g = grayImage.getGraphics();
    g.drawImage(original, 0, 0, null);
    g.dispose();
    return grayImage;
  }
}
