package org.example.krevent.service;

import lombok.RequiredArgsConstructor;
import org.example.krevent.exception.HallSeatBookedException;
import org.example.krevent.mapper.HallSeatMapper;
import org.example.krevent.models.EventHall;
import org.example.krevent.models.HallSeat;
import org.example.krevent.models.enums.SeatType;
import org.example.krevent.payload.dto.HallSeatDto;
import org.example.krevent.payload.request.HallSeatCreationRequest;
import org.example.krevent.repository.EventHallRepository;
import org.example.krevent.repository.HallSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.krevent.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class HallSeatService {
    private final EventHallRepository eventHallRepository;
    private final HallSeatRepository hallSeatRepository;
    private final HallSeatMapper hallSeatMapper;

    @Transactional
    public HallSeatDto createHallSeat(HallSeatCreationRequest data) {
        EventHall eventHall = findById(eventHallRepository, data.getEventHallId());

        HallSeat hallSeat = HallSeat.builder()
                .row(data.getRow())
                .seat(data.getSeat())
                .type(data.getType())
                .eventHall(eventHall)
                .build();

        hallSeat = hallSeatRepository.save(hallSeat);

        return hallSeatMapper.toDto(hallSeat);
    }

    public HallSeatDto getHallSeat(Long id) {
        HallSeat hallSeat = findById(hallSeatRepository, id);
        return hallSeatMapper.toDto(hallSeat);
    }

    @Transactional
    public HallSeatDto updateType(Long id, SeatType type) {
        HallSeat hallSeat = findById(hallSeatRepository, id);

        if (hallSeat.isBooked()) {
            throw new HallSeatBookedException("Cannot change the type of already booked seat");
        } else if (hallSeat.getType() == type) {
            return hallSeatMapper.toDto(hallSeat);
        }

        hallSeat.setType(type);
        hallSeat.setPrice(type.getPrice());
        hallSeat = hallSeatRepository.save(hallSeat);
        return hallSeatMapper.toDto(hallSeat);
    }

    @Transactional
    public HallSeatDto updatePrice(Long id, double price) {
        HallSeat hallSeat = findById(hallSeatRepository, id);

        if (hallSeat.isBooked()) {
            throw new HallSeatBookedException("Cannot change the price of already booked seat");
        } else if (hallSeat.getPrice() == price) {
            return hallSeatMapper.toDto(hallSeat);
        }

        hallSeat.setPrice(price);
        hallSeat = hallSeatRepository.save(hallSeat);
        return hallSeatMapper.toDto(hallSeat);

    }

    @Transactional
    public void deleteHallSeat(Long id) {
        hallSeatRepository.deleteById(id);
    }

}
