package org.example.krevent.mapper;

import org.example.krevent.models.EventSeat;
import org.example.krevent.models.User;
import org.example.krevent.payload.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.stream.Collectors;

public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                map().setEventSeatIds(source.getEventSeats().stream()
                        .map(EventSeat::getId)
                        .collect(Collectors.toSet()));
            }
        });
    }

    public UserDto toDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }
}