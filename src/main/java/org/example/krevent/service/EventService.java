package org.example.krevent.service;


import lombok.RequiredArgsConstructor;
import org.example.krevent.mapper.EventMapper;
import org.example.krevent.models.Event;
import org.example.krevent.models.EventHall;
import org.example.krevent.payload.dto.EventDto;
import org.example.krevent.payload.request.EventCreationRequest;
import org.example.krevent.repository.EventHallRepository;
import org.example.krevent.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.krevent.util.DateUtil.parse;
import static org.example.krevent.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventHallRepository eventHallRepository;
    private final EventMapper eventMapper;

    @Transactional
    public EventDto createEvent(EventCreationRequest data) {
        EventHall eventHall = findById(eventHallRepository, data.getEventHallId());
        LocalDateTime date = parse(data.getDate());

        Event event = Event.builder()
                .name(data.getName())
                .description(data.getDescription())
                .date(date)
                .eventHall(eventHall)
                .build();

        event = eventRepository.save(event);

        return eventMapper.toDto(event);
    }

    public EventDto getEvent(Long id) {
        Event event = findById(eventRepository, id);
        return eventMapper.toDto(event);
    }

    @Transactional
    public EventDto addImages(Long id, List<String> imageUrls) {
        Event event = findById(eventRepository, id);
        event.getImageUrls().addAll(imageUrls);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Transactional
    public EventDto removeImage(Long id, String imageUrl) {
        Event event = findById(eventRepository, id);
        event.getImageUrls().remove(imageUrl);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Transactional
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
