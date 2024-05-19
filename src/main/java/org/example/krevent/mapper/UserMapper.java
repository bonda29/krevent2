package org.example.krevent.mapper;

import org.example.krevent.models.User;
import org.example.krevent.models.abstracts.BaseEntity;
import org.example.krevent.payload.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                if (source.getTickets() != null)
                    map().setTicketIds(source.getTickets().stream()
                            .map(BaseEntity::getId)
                            .collect(Collectors.toSet()));
            }
        });
    }

    public UserDto toDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }
}