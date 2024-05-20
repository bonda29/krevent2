package org.example.krevent.service.ticket;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.example.krevent.mapper.TicketMapper;
import org.example.krevent.models.HallSeat;
import org.example.krevent.models.Ticket;
import org.example.krevent.models.User;
import org.example.krevent.payload.dto.TicketDto;
import org.example.krevent.repository.HallSeatRepository;
import org.example.krevent.repository.TicketRepository;
import org.example.krevent.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.example.krevent.util.QrCodeUtil.createQrCode;

@Service
@RequiredArgsConstructor
public class GetTicketService {
    private final TransactionRepository transactionRepository;
    private final HallSeatRepository hallSeatRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;


    @Transactional
    public List<TicketDto> getTickets(String sessionId) {
        var transaction = transactionRepository.findBySessionId(sessionId);
        List<Ticket> tickets = new ArrayList<>();
        List<HallSeat> updatedHallSeats = new ArrayList<>();

        for (HallSeat hallSeat : transaction.getHallSeats()) {
            var ticket = createTicket(transaction.getUser(), hallSeat);

            hallSeat.setBooked(true);
            hallSeat.setTicket(ticket);
            updatedHallSeats.add(hallSeat);

            tickets.add(ticket);
        }

        hallSeatRepository.saveAll(updatedHallSeats);

        return ticketMapper.toDto(tickets);
    }

    private Ticket createTicket(User user, HallSeat hallSeat) {
        Map<String, Object> ticketData = Map.of(
                "Name", user.getFirstName() + " " + user.getLastName(),
                "email", user.getEmail(),
                "seat", "row " + hallSeat.getRow() + " seat " + hallSeat.getSeat(),
                "price", hallSeat.getPrice()
        );

        Gson gson = new Gson();
        String json = gson.toJson(ticketData);
        String qrCodeImage;
        try {
            qrCodeImage = createQrCode(UUID.randomUUID().toString(), json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Ticket ticket = Ticket.builder()
                .user(user)
                .hallSeat(hallSeat)
                .price(hallSeat.getPrice())
                .qrCodeImage(qrCodeImage)
                .build();

        return ticketRepository.save(ticket);
    }
}
