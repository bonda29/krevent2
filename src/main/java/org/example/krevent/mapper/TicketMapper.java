package org.example.krevent.mapper;

import org.example.krevent.models.Ticket;
import org.example.krevent.payload.dto.TicketDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
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

    public List<TicketDto> toDto(Collection<Ticket> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
