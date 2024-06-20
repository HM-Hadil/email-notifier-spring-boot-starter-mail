package coding.spring.emailnotifier.service;

import coding.spring.emailnotifier.entity.User;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;
    @Async
    public void send(User user, Long ticketId, byte[] qrImage) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(user.getEmail());
        helper.setSubject("Your Ticket Purchase Confirmation");
        helper.setText("Dear " + user.getUsername() + ",\n\nThank you for purchasing a ticket. Your ticket ID is: " + ticketId + "\n\nBest regards,\nHadil Hammami");

        // Add QR code as an attachment
        DataSource dataSource = new ByteArrayDataSource(qrImage, "image/png");
        helper.addAttachment("ticket_qr.png", dataSource);

        javaMailSender.send(message);
    }

    public Long generateRandomKey() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }
}
