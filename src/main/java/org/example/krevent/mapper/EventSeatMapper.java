package org.example.krevent.mapper;

import org.example.krevent.models.EventSeat;
import org.example.krevent.payload.dto.EventSeatDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class EventSeatMapper {
    private final ModelMapper modelMapper;

    public EventSeatMapper() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<EventSeat, EventSeatDto>() {
            @Override
            protected void configure() {
                map().setEventId(source.getEvent().getId());
                map().setUserId(source.getUser().getId());
            }
        });
    }

    public EventSeatDto toDto(EventSeat entity) {
        return modelMapper.map(entity, EventSeatDto.class);
    }
}
