package com.matrice.backend.controller;
import com.matrice.backend.DTO.DashboardDTO;
import com.matrice.backend.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "http://localhost:4200")

public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {

        this.dashboardService = dashboardService;

    }

    @GetMapping
    public DashboardDTO getDashboard() {

        return dashboardService.getDashboard();

    }
}
