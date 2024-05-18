package org.example.krevent.mapper;

import org.example.krevent.models.Event;
import org.example.krevent.models.EventHall;
import org.example.krevent.models.HallSeat;
import org.example.krevent.payload.dto.EventHallDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import static java.util.stream.Collectors.toSet;

public class EventHallMapper {
    private final ModelMapper modelMapper;

    public EventHallMapper() {
        modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<EventHall, EventHallDto>() {
            @Override
            protected void configure() {
                map().setEventIds(source.getEvents().stream()
                        .map(Event::getId)
                        .collect(toSet()));

                map().setHallSeatIds(source.getHallSeats().stream()
                        .map(HallSeat::getId)
                        .collect(toSet()));
            }
        });
    }

    public EventHallDto toDto(EventHall entity) {
        return modelMapper.map(entity, EventHallDto.class);
    }

}
