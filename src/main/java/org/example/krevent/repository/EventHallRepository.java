package org.example.krevent.repository;

import org.example.krevent.models.EventHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventHallRepository extends JpaRepository<EventHall, Long> {
}