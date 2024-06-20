package coding.spring.emailnotifier.service;

import coding.spring.emailnotifier.entity.User;
import coding.spring.emailnotifier.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private UserRepo userRepo;


    public String buyTicket(UUID userId){
        Optional<User> user = Optional.ofNullable(this.userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));;
        Long ticketId = this.mailSenderService.generateRandomKey();

        this.mailSenderService.send(user.get(),ticketId);
        return "Ticket purchased successfully! A confirmation email has been sent to " + user.get().getEmail();

    }
}
