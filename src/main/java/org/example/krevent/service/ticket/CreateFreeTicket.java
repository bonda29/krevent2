package org.example.krevent.service.ticket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.example.krevent.mapper.TicketMapper;
import org.example.krevent.models.HallSeat;
import org.example.krevent.models.Ticket;
import org.example.krevent.payload.request.FreeTicketCreationRequest;
import org.example.krevent.repository.HallSeatRepository;
import org.example.krevent.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.example.krevent.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class CreateFreeTicket {
    private final TicketImageGenerator ticketImageGenerator;
    private final HallSeatRepository hallSeatRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;
    private final TicketMapper ticketMapper;

    public BufferedImage createFreeTicket(FreeTicketCreationRequest request) {
        var name = request.getName();
        var hallSeat = findById(hallSeatRepository, request.getHallSeatId());

        var qrCodeUrl = generateQRCode(name, hallSeat);

        var ticket = Ticket.builder()
                .user(null)
                .hallSeat(hallSeat)
                .price(0)
                .qrCodeImage(qrCodeUrl)
                .build();

        ticketRepository.save(ticket);

        hallSeat.setBooked(true);
        hallSeat.setTicket(ticket);

        hallSeatRepository.save(hallSeat);

        String type;
        if (hallSeat.getType().toString().contains("BALCONY")) {
            type = "BALCONY";
        } else {
            type = "REGULAR";
        }
        String seat = "row " + hallSeat.getRow() + " seat " + hallSeat.getSeat();

        return ticketImageGenerator.generateTicketImageFromTemplate(name, type, 0.0, seat, qrCodeUrl);
    }

    private String generateQRCode(String name, HallSeat hallSeat) {
        Map<String, Object> ticketData = new LinkedHashMap<>();
        ticketData.put("name", name);
        ticketData.put("type", hallSeat.getType());
        ticketData.put("seat", "row " + hallSeat.getRow() + " seat " + hallSeat.getSeat());
        ticketData.put("price", "free"); // free ticket

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(ticketData);
        String qrCodeUrl;
        try {
            qrCodeUrl = qrCodeService.createQrCode(UUID.randomUUID().toString(), json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return qrCodeUrl;
    }
}
