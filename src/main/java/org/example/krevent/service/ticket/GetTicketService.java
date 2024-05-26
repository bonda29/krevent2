package org.example.krevent.service.ticket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.example.krevent.models.Guest;
import org.example.krevent.models.HallSeat;
import org.example.krevent.models.Ticket;
import org.example.krevent.payload.dto.EmailDto;
import org.example.krevent.repository.HallSeatRepository;
import org.example.krevent.repository.TicketRepository;
import org.example.krevent.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
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

    @Transactional
    public BufferedImage getTickets(String sessionId) {
        var transaction = transactionRepository.findBySessionId(sessionId);

        if (transaction.getStatus() == FINISHED) {
            File inputFile = new File("src/main/resources/static/tickets/" + sessionId + ".png");
            BufferedImage image;
            try {
                image = ImageIO.read(inputFile);
            } catch (IOException e) {
                throw new RuntimeException("Error while reading image", e);
            }
            return image;
        }

        List<Ticket> tickets = new ArrayList<>();
        List<HallSeat> updatedHallSeats = new ArrayList<>();
        List<BufferedImage> ticketImages = new ArrayList<>();

        for (HallSeat hallSeat : transaction.getHallSeats()) {
            var ticket = createTicket(transaction.getGuest(), hallSeat);

            hallSeat.setBooked(true);
            hallSeat.setTicket(ticket);
            updatedHallSeats.add(hallSeat);

            tickets.add(ticket);
            ticketImages.add(generateTicketImage(transaction.getGuest(), ticket));
        }

        hallSeatRepository.saveAll(updatedHallSeats);
        transaction.setStatus(FINISHED);

        sendEmailToUser(transaction.getGuest(), tickets, ticketImages);

        BufferedImage image = combineImages(ticketImages, 10);

        // Save the image to a file
        File outputFile = new File("src/main/resources/static/tickets/" + sessionId + ".png");
        try {
            ImageIO.write(image, "PNG", outputFile);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving image", e);
        }

        return image;
    }


    private Ticket createTicket(Guest guest, HallSeat hallSeat) {
        Map<String, Object> ticketData = new LinkedHashMap<>();
        ticketData.put("name", guest.getFirstName() + " " + guest.getLastName());
        ticketData.put("email", guest.getEmail());
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
                .guest(guest)
                .hallSeat(hallSeat)
                .price(hallSeat.getPrice())
                .qrCodeImage(qrCodeUrl)
                .build();

        return ticketRepository.save(ticket);
    }

    private void sendEmailToUser(Guest guest, List<Ticket> tickets, List<BufferedImage> ticketImages) {
        // Prepare email data
        EmailDto emailData = new EmailDto();
        emailData.setTo(guest.getEmail());
        emailData.setSubject("Потвърждение за онлайн покупка на билет");
        emailData.setName(guest.getFirstName() + " " + guest.getLastName());
        emailData.setNumberOfTickets(tickets.size());
        emailData.setPrice(tickets.stream().mapToDouble(Ticket::getPrice).sum());
        emailData.setTickets(ticketImages);

        // Send email
        emailService.sendEmail(emailData);
    }

    private BufferedImage generateTicketImage(Guest guest, Ticket ticket) {
        HallSeat hallSeat = ticket.getHallSeat();

        String name = guest.getFirstName() + " " + guest.getLastName();

        String seat = "Р" + hallSeat.getRow() + " М" + hallSeat.getSeat();

        return ticketImageGenerator.generateTicketImageFromTemplate(
                name,
                hallSeat.getType(),
                ticket.getPrice(),
                seat,
                ticket.getQrCodeImage());
    }

    public BufferedImage combineImages(List<BufferedImage> images, int gap) {
        int totalHeight = -gap;
        int maxWidth = 0;
        for (BufferedImage image : images) {
            totalHeight += image.getHeight() + gap;
            maxWidth = Math.max(maxWidth, image.getWidth());
        }

        // Create a new image
        BufferedImage combined = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();

        // Draw each image onto the new image
        int y = 0;
        for (BufferedImage image : images) {
            g.drawImage(image, null, 0, y);
            y += image.getHeight() + gap;
        }

        g.dispose();

        return combined;
    }
}
