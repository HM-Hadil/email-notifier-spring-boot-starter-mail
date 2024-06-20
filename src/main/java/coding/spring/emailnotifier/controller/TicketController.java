package coding.spring.emailnotifier.controller;

import coding.spring.emailnotifier.service.TicketService;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/buy")
    public String buyTicket(@RequestParam UUID userId) throws IOException, WriterException, MessagingException {
        return ticketService.buyTicket(userId);
    }
}
