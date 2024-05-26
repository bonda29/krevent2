package org.example.krevent.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.krevent.payload.request.FreeTicketCreationRequest;
import org.example.krevent.payload.request.TicketPurchaseRequest;
import org.example.krevent.service.ticket.CreateFreeTicket;
import org.example.krevent.service.ticket.GetTicketService;
import org.example.krevent.service.ticket.PurchaseTicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final PurchaseTicketService purchaseTicketService;
    private final GetTicketService getTicketService;
    private final CreateFreeTicket createFreeTicket;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody TicketPurchaseRequest data) {
        return ResponseEntity.ok(purchaseTicketService.purchaseTicketForGuest(data));
    }

    @PostMapping("/free")
    public void createFreeTicket(@RequestBody FreeTicketCreationRequest data, HttpServletResponse response) {
        var image = createFreeTicket.createFreeTicket(data);

        try {
            // Set the response type to image/png
            response.setContentType("image/png");

            // Write the image to the response output stream
            ImageIO.write(image, "PNG", response.getOutputStream());

            // Flush the output stream to send the data to the client
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/success")
    public void getTickets(@RequestParam String sessionId, HttpServletResponse response) {
        var image = getTicketService.getTickets(sessionId);

        try {
            // Set the response type to image/png
            response.setContentType("image/png");

            // Write the image to the response output stream
            ImageIO.write(image, "PNG", response.getOutputStream());

            // Flush the output stream to send the data to the client
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
