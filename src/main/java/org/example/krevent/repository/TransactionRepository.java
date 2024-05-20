package org.example.krevent.repository;

import org.example.krevent.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findBySessionId(String sessionId);
}