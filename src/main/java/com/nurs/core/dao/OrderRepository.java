package com.nurs.core.dao;

import com.nurs.core.dto.UpdateOrderRequest;
import com.nurs.core.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    @Transactional
    @Modifying
    @Query(value = "UPDATE orders set amount =:amount , date =:date, paid =:paid, email=:email where id = :id",
            nativeQuery = true)
    void updateById(Long id, BigDecimal amount, String date,  Boolean paid, String email);
}
