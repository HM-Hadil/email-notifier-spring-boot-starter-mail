package coding.spring.emailnotifier.service;

import coding.spring.emailnotifier.entity.User;
import coding.spring.emailnotifier.repo.UserRepo;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private QRCodeGeneratorService qrCodeGenerator;




    public String buyTicket(UUID userId) throws IOException, WriterException, MessagingException {
        Optional<User> user = Optional.ofNullable(userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));

        Long ticketId = mailSenderService.generateRandomKey();

        // Generate QR code image
        String s = "Hello "+user.get().getUsername() + " your Ticket ID is : " + ticketId;
        String qrText = s + "\n we hope you are satisfied with our services";
        byte[] qrImage = qrCodeGenerator.generateQRCodeImage(qrText, 350, 350);

        mailSenderService.send(user.get(), ticketId, qrImage);
        return "Ticket purchased successfully! A confirmation email has been sent to " + user.get().getEmail();
    }
}
