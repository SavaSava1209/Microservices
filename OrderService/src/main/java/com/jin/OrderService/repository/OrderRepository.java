package com.jin.OrderService.repository;

import com.jin.OrderService.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
