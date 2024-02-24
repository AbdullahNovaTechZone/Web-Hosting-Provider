package com.novatechzone.web.domain.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByCategoryIdAndPriceAndDuration(Long categoryId, Double price, Double duration);
}