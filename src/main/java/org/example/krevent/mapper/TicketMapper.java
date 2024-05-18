package org.example.krevent.mapper;

import org.example.krevent.models.Ticket;
import org.example.krevent.payload.dto.TicketDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class TicketMapper {
    private final ModelMapper modelMapper;

    public TicketMapper() {
        modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Ticket, TicketDto>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUser().getId());
                map().setHallSeatId(source.getHallSeat().getId());
            }
        });
    }

    public TicketDto toDto(Ticket entity) {
        return modelMapper.map(entity, TicketDto.class);
    }

}
