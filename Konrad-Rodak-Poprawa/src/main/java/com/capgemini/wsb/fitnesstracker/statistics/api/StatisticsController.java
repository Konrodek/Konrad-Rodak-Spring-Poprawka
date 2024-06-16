package com.capgemini.wsb.fitnesstracker.statistics.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsServiceImpl statisticsService;

    @PostMapping
    public ResponseEntity<Statistics> createStatistics(@RequestBody Statistics statistics) {
        return ResponseEntity.ok(statisticsService.createStatistics(statistics));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Statistics> updateStatistics(@PathVariable Long id, @RequestBody Statistics statistics) {
        return statisticsService.updateStatistics(id, statistics)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Statistics> getStatistics(@PathVariable Long id) {
        return statisticsService.getStatistics(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatistics(@PathVariable Long id) {
        statisticsService.deleteStatistics(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/calories/{calories}")
    public ResponseEntity<List<Statistics>> findStatisticsByCaloriesGreaterThan(@PathVariable int calories) {
        return ResponseEntity.ok(statisticsService.findStatisticsByCaloriesGreaterThan(calories));
    }
}
