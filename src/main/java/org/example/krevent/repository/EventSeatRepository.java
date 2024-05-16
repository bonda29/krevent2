package org.example.krevent.repository;

import org.example.krevent.models.EventSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventSeatRepository extends JpaRepository<EventSeat, Long> {
}