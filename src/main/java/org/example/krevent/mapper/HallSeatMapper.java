package org.example.krevent.mapper;

import org.example.krevent.models.HallSeat;
import org.example.krevent.payload.dto.HallSeatDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class HallSeatMapper {
    private final ModelMapper modelMapper;

    public HallSeatMapper() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<HallSeat, HallSeatDto>() {
            @Override
            protected void configure() {
                map().setTicketId(source.getTicket().getId());
                map().setEventHallId(source.getEventHall().getId());
            }
        });
    }

    public HallSeatDto toDto(HallSeat entity) {
        return modelMapper.map(entity, HallSeatDto.class);
    }
}
