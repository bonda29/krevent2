package org.example.krevent.controller;

import lombok.RequiredArgsConstructor;
import org.example.krevent.payload.request.TicketPurchaseRequest;
import org.example.krevent.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody TicketPurchaseRequest data) {
        return ResponseEntity.ok(ticketService.purchaseTicket(data));
    }
}
