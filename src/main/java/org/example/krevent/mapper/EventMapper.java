package org.example.krevent.mapper;

import org.example.krevent.models.Event;
import org.example.krevent.payload.dto.EventDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class EventMapper {
    private final ModelMapper modelMapper;

    public EventMapper() {
        modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Event, EventDto>() {
            @Override
            protected void configure() {
                map().setEventHallId(source.getEventHall().getId());
            }
        });
    }

    public EventDto toDto(Event entity) {
        return modelMapper.map(entity, EventDto.class);
    }

}
