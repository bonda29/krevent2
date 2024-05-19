package org.example.krevent.controller;

import lombok.RequiredArgsConstructor;
import org.example.krevent.models.enums.SeatType;
import org.example.krevent.payload.request.HallSeatCreationRequest;
import org.example.krevent.payload.response.MessageResponse;
import org.example.krevent.service.HallSeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hall-seats")
public class HallSeatController {
    private final HallSeatService hallSeatService;

    @PostMapping("/")
    public ResponseEntity<?> createHallSeat(@RequestBody HallSeatCreationRequest data) {
        return ResponseEntity.ok(hallSeatService.createHallSeat(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHallSeat(@PathVariable Long id) {
        return ResponseEntity.ok(hallSeatService.getHallSeat(id));
    }

    @PatchMapping("/{id}/update-type")
    public ResponseEntity<?> updateType(@PathVariable Long id, @RequestParam SeatType type) {
        return ResponseEntity.ok(hallSeatService.updateType(id, type));
    }

    @PatchMapping("/{id}/update-price")
    public ResponseEntity<?> updatePrice(@PathVariable Long id, @RequestParam Double price) {
        return ResponseEntity.ok(hallSeatService.updatePrice(id, price));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHallSeat(@PathVariable Long id) {
        hallSeatService.deleteHallSeat(id);
        return ResponseEntity.ok(MessageResponse.from("Hall seat deleted successfully"));
    }

}
