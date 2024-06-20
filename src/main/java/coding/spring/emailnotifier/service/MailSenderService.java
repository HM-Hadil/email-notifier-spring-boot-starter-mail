package coding.spring.emailnotifier.service;

import coding.spring.emailnotifier.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;
    @Async
    public void send(User user,  Long ticketId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Your Ticket Purchase Confirmation");
        message.setText("Dear " + user.getUsername() + ",\n\nThank you for purchasing a ticket. Your ticket ID is: " + ticketId + "\n\nBest regards,\n ,Hadil Hammami");

        javaMailSender.send(message);
    }

    public Long generateRandomKey() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }
}
