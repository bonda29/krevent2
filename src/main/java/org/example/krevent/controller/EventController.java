package org.example.krevent.controller;

import lombok.RequiredArgsConstructor;
import org.example.krevent.payload.dto.EventDto;
import org.example.krevent.payload.request.EventCreationRequest;
import org.example.krevent.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.krevent.payload.response.MessageResponse.from;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    @PostMapping("/")
    public ResponseEntity<EventDto> createEvent(EventCreationRequest data) {
        return ResponseEntity.ok(eventService.createEvent(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @PatchMapping("/{id}/add-images")
    public ResponseEntity<EventDto> addImages(@PathVariable Long id, @RequestBody List<String> imageUrls) {
        return ResponseEntity.ok(eventService.addImages(id, imageUrls));
    }

    @PatchMapping("/{id}/remove-image")
    public ResponseEntity<EventDto> removeImage(@PathVariable Long id, @RequestBody String imageUrl) {
        return ResponseEntity.ok(eventService.removeImage(id, imageUrl));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(from("Event deleted successfully"));
    }
}
