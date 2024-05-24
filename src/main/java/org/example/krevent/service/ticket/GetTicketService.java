package org.example.krevent.service.ticket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.example.krevent.mapper.TicketMapper;
import org.example.krevent.models.HallSeat;
import org.example.krevent.models.Ticket;
import org.example.krevent.models.User;
import org.example.krevent.payload.dto.EmailDto;
import org.example.krevent.payload.dto.TicketDto;
import org.example.krevent.repository.HallSeatRepository;
import org.example.krevent.repository.TicketRepository;
import org.example.krevent.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import static org.example.krevent.models.enums.TransactionStatus.FINISHED;

@Service
@RequiredArgsConstructor
public class GetTicketService {
    private final TransactionRepository transactionRepository;
    private final TicketImageGenerator ticketImageGenerator;
    private final HallSeatRepository hallSeatRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;
    private final EmailService emailService;
    private final TicketMapper ticketMapper;


    @Transactional
    public List<TicketDto> getTickets(String sessionId) {
        var transaction = transactionRepository.findBySessionId(sessionId);
        List<Ticket> tickets = new ArrayList<>();
        List<HallSeat> updatedHallSeats = new ArrayList<>();
        List<BufferedImage> ticketImages = new ArrayList<>();

        for (HallSeat hallSeat : transaction.getHallSeats()) {
            var ticket = createTicket(transaction.getUser(), hallSeat);

            hallSeat.setBooked(true);
            hallSeat.setTicket(ticket);
            updatedHallSeats.add(hallSeat);

            tickets.add(ticket);
            ticketImages.add(generateTicketImage(transaction.getUser(), ticket));
        }

        hallSeatRepository.saveAll(updatedHallSeats);
        transaction.setStatus(FINISHED);

        sendEmailToUser(transaction.getUser(), tickets, ticketImages);
        return ticketMapper.toDto(tickets);
    }


    private Ticket createTicket(User user, HallSeat hallSeat) {
        Map<String, Object> ticketData = new LinkedHashMap<>();
        ticketData.put("name", user.getFirstName() + " " + user.getLastName());
        ticketData.put("email", user.getEmail());
        ticketData.put("type", hallSeat.getType());
        ticketData.put("seat", "row " + hallSeat.getRow() + " seat " + hallSeat.getSeat());
        ticketData.put("price", hallSeat.getPrice());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(ticketData);
        String qrCodeUrl;
        try {
            qrCodeUrl = qrCodeService.createQrCode(UUID.randomUUID().toString(), json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Ticket ticket = Ticket.builder()
                .user(user)
                .hallSeat(hallSeat)
                .price(hallSeat.getPrice())
                .qrCodeImage(qrCodeUrl)
                .build();

        return ticketRepository.save(ticket);
    }

    private void sendEmailToUser(User user, List<Ticket> tickets, List<BufferedImage> ticketImages) {
        // Prepare email data
        EmailDto emailData = new EmailDto();
        emailData.setTo(user.getEmail());
        emailData.setSubject("Потвърждение за онлайн покупка на билет");
        emailData.setName(user.getFirstName() + " " + user.getLastName());
        emailData.setNumberOfTickets(tickets.size());
        emailData.setPrice(tickets.stream().mapToDouble(Ticket::getPrice).sum());
        emailData.setTickets(ticketImages);

        // Send email
        emailService.sendEmail(emailData);
    }

    private BufferedImage generateTicketImage(User user, Ticket ticket) {
        HallSeat hallSeat = ticket.getHallSeat();

        String name = user.getFirstName() + " " + user.getLastName();
        String type;
        if (hallSeat.getType().toString().contains("BALCONY")) {
            type = "BALCONY";
        } else {
            type = "REGULAR";
        }
        String seat = "row " + hallSeat.getRow() + " seat " + hallSeat.getSeat();

        return ticketImageGenerator.generateTicketImage(
                name,
                type,
                ticket.getPrice(),
                seat,
                ticket.getQrCodeImage());
    }

}
