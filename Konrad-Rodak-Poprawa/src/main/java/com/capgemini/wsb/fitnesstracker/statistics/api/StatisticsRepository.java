package com.capgemini.wsb.fitnesstracker.statistics.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    List<Statistics> findByTotalCaloriesBurnedGreaterThan(int calories);

}
