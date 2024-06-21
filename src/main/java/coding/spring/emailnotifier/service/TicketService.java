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
        String imageUrl="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwKuX6a97h0QHCTy0K0wMzZ9Ze8gOmuMCW580ltSrLfF7ASRfxqoEIOpXj3lcWA24RYLA&usqp=CAU";

        // Generate QR code image
        String s = "Ticket ID : " + ticketId;
        String qrText = s + "\n Thank you for purchasing a ticket. Please keep this QR code handy as it will be required for entry.\n";
        byte[] qrImage = qrCodeGenerator.generateQRCodeImage(qrText, 350, 350);

        mailSenderService.send(user.get(), ticketId, qrImage,imageUrl);
        return "Ticket purchased successfully! A confirmation email has been sent to " + user.get().getEmail();
    }
}
