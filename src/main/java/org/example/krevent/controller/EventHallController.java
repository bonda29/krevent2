package org.example.krevent.controller;

import lombok.RequiredArgsConstructor;
import org.example.krevent.payload.dto.EventHallDto;
import org.example.krevent.payload.request.EventHallCreationRequest;
import org.example.krevent.service.EventHallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.krevent.payload.response.MessageResponse.from;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event-halls")
public class EventHallController {
    private final EventHallService eventHallService;

    @PostMapping("/")
    public ResponseEntity<EventHallDto> createEventHall(@RequestBody EventHallCreationRequest data) {
        return ResponseEntity.ok(eventHallService.createEventHall(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventHallDto> getEventHall(@PathVariable Long id) {
        return ResponseEntity.ok(eventHallService.getEventHall(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<EventHallDto>> getAllEventHalls() {
        return ResponseEntity.ok(eventHallService.getAllEventHalls());
    }

    @GetMapping("/{id}/hall-seats")
    public ResponseEntity<?> getHallSeats(@PathVariable Long id) {
        return ResponseEntity.ok(eventHallService.getHallSeats(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventHall(@PathVariable Long id) {
        eventHallService.deleteEventHall(id);
        return ResponseEntity.ok(from("Event hall deleted successfully"));
    }
}
