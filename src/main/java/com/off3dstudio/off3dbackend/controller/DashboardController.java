package com.off3dstudio.off3dbackend.controller;

import com.off3dstudio.off3dbackend.dto.DashboardDTO;
import com.off3dstudio.off3dbackend.dto.DashboardUltimosPedidosDTO;
import com.off3dstudio.off3dbackend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardDTO getDashboard() {
        return dashboardService.getDashboardData();
    }

    @GetMapping("/ultimos-pedidos")
    public ResponseEntity<List<DashboardUltimosPedidosDTO>> getUltimosPedidos() {
        List<DashboardUltimosPedidosDTO> ultimosPedidos = dashboardService.getUltimosPedidos();
        return ResponseEntity.ok(ultimosPedidos);
    }
}
