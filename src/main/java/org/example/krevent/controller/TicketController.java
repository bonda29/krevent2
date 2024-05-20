package org.example.krevent.controller;

import lombok.RequiredArgsConstructor;
import org.example.krevent.payload.request.TicketPurchaseRequest;
import org.example.krevent.service.ticket.GetTicketService;
import org.example.krevent.service.ticket.PurchaseTicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final PurchaseTicketService purchaseTicketService;
    private final GetTicketService getTicketService;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody TicketPurchaseRequest data) {
        return ResponseEntity.ok(purchaseTicketService.purchaseTicket(data));
    }

    @GetMapping("/success")
    public ResponseEntity<?> getTickets(@RequestParam String sessionId) {
        return ResponseEntity.ok(getTicketService.getTickets(sessionId));
    }
}
