package com.matrice.backend.controller;

import com.matrice.backend.DTO.StatisticsDTO;
import com.matrice.backend.service.StatisticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "http://localhost:4200")
public class StatisticsController {

    private final StatisticsService statisticsService;


    public StatisticsController(
            StatisticsService statisticsService) {

        this.statisticsService =
                statisticsService;
    }


    // ==========================================
    // MANAGER STATISTICS
    // ==========================================

    @GetMapping("/manager/{managerId}")
    public StatisticsDTO getManagerStatistics(
            @PathVariable Long managerId) {

        return statisticsService
                .getStatistics(managerId);
    }
}