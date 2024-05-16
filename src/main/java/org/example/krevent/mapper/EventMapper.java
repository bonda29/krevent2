package org.example.krevent.mapper;

import org.example.krevent.models.Event;
import org.example.krevent.payload.dto.EventDto;
import org.example.krevent.models.EventSeat;
import org.example.krevent.payload.dto.EventSeatDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import static java.util.stream.Collectors.toSet;

public class EventMapper {
    private final ModelMapper modelMapper;

    public EventMapper() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Event, EventDto>() {
            @Override
            protected void configure() {
                map().setEventSeatIds(source.getEventSeats().stream()
                        .map(EventSeat::getId)
                        .collect(toSet()));
            }
        });
    }

    public EventSeatDto toDto(Event entity) {
        return modelMapper.map(entity, EventSeatDto.class);
    }

}
