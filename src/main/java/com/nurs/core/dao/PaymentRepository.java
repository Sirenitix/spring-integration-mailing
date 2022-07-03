package com.nurs.core.dao;

import com.nurs.core.entity.Order;
import com.nurs.core.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrder(Order order);
}

