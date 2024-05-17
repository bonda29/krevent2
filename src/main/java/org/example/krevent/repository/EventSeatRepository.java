package org.example.krevent.repository;

import org.example.krevent.models.HallSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventSeatRepository extends JpaRepository<HallSeat, Long> {
}