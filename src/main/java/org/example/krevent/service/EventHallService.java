package org.example.krevent.service;

import lombok.RequiredArgsConstructor;
import org.example.krevent.mapper.EventHallMapper;
import org.example.krevent.mapper.HallSeatMapper;
import org.example.krevent.models.EventHall;
import org.example.krevent.models.HallSeat;
import org.example.krevent.payload.dto.EventHallDto;
import org.example.krevent.payload.dto.HallSeatDto;
import org.example.krevent.payload.request.EventHallCreationRequest;
import org.example.krevent.repository.EventHallRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.krevent.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class EventHallService {
    private final EventHallRepository eventHallRepository;
    private final EventHallMapper eventHallMapper;
    private final HallSeatMapper hallSeatMapper;

    @Transactional
    public EventHallDto createEventHall(EventHallCreationRequest data) {
        EventHall eventHall = EventHall.builder()
                .name(data.getName())
                .description(data.getDescription())
                .capacity(data.getCapacity())
                .build();

        eventHall = eventHallRepository.save(eventHall);

        return eventHallMapper.toDto(eventHall);
    }

    public EventHallDto getEventHall(Long id) {
        EventHall eventHall = eventHallRepository.findById(id).orElseThrow();
        return eventHallMapper.toDto(eventHall);
    }

    public List<EventHallDto> getAllEventHalls() {
        return eventHallMapper.toDto(eventHallRepository.findAll());
    }

    public List<HallSeatDto> getHallSeats(Long id) {
        EventHall eventHall = findById(eventHallRepository, id);

        return hallSeatMapper.toDto(eventHall.getHallSeats())
                .stream()
                .sorted(Comparator.comparingLong(HallSeatDto::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteEventHall(Long id) {
        eventHallRepository.deleteById(id);
    }

}
