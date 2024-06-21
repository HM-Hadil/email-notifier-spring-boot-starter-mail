package coding.spring.emailnotifier.service;

import coding.spring.emailnotifier.entity.User;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;
    @Async
    public void send(User user, Long ticketId, byte[] qrImage, String imageUrl) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getEmail());
            helper.setSubject("Your Ticket Purchase Confirmation");
            helper.setText("Dear " + user.getUsername() + ",\n\nThank you for purchasing a ticket. Your ticket ID is: " + ticketId + "\n\nPlease find your QR code and an image attached.\n\nBest regards,\nHadil Hammami");

            // Attach QR code image
            ByteArrayResource qrImageResource = new ByteArrayResource(qrImage);
            helper.addAttachment("ticket-qr.png", qrImageResource);

            // Attach image from URL
            UrlResource urlResource = new UrlResource(new URL(imageUrl));
            helper.addAttachment("external-image.png", urlResource);

            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            // Handle exception
            e.printStackTrace();
        }
    }
    public Long generateRandomKey() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }
}
