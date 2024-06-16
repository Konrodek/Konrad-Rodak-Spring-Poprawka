package com.capgemini.wsb.fitnesstracker.statistics.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsProvider {

    private final StatisticsRepository statisticsRepository;

    public Statistics createStatistics(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }

    public Optional<Statistics> updateStatistics(Long id, Statistics newStatistics) {
        return statisticsRepository.findById(id)
                .map(statistics -> {
                    statistics.setTotalTrainings(newStatistics.getTotalTrainings());
                    statistics.setTotalDistance(newStatistics.getTotalDistance());
                    statistics.setTotalCaloriesBurned(newStatistics.getTotalCaloriesBurned());
                    return statisticsRepository.save(statistics);
                });
    }

    @Override
    public Optional<Statistics> getStatistics(Long statisticsId) {
        return statisticsRepository.findById(statisticsId);
    }

    public void deleteStatistics(Long id) {
        statisticsRepository.deleteById(id);
    }

    public List<Statistics> findStatisticsByCaloriesGreaterThan(int calories) {
        return statisticsRepository.findByTotalCaloriesBurnedGreaterThan(calories);
    }
}
