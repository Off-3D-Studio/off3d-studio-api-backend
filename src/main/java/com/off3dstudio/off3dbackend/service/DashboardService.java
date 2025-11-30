package com.off3dstudio.off3dbackend.service;

import com.off3dstudio.off3dbackend.dto.DashboardDTO;
import com.off3dstudio.off3dbackend.dto.DashboardUltimosPedidosDTO;
import com.off3dstudio.off3dbackend.model.Projeto;
import com.off3dstudio.off3dbackend.repository.ClienteRepository;
import com.off3dstudio.off3dbackend.repository.DashboardRepository;
import com.off3dstudio.off3dbackend.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;
    private final ProjetoRepository projetoRepository;
    private final ClienteRepository clienteRepository;

    public DashboardDTO getDashboardData() {
        BigDecimal totalReceber = dashboardRepository.sumTotalReceber();
        BigDecimal totalPagar = dashboardRepository.sumTotalPagar();
        Long pedidosPendentes = dashboardRepository.countPedidosPendentes();
        Long projetosAndamento = dashboardRepository.countProjetosAndamento();
        Long totalClientes = clienteRepository.count();

        List<DashboardUltimosPedidosDTO> ultimosPedidos = dashboardRepository.findUltimosPedidos(PageRequest.of(0, 5));
        List<Projeto> projetosProximosVencimento = projetoRepository.findProjetosProximosVencimento();

        return new DashboardDTO(
                totalReceber,
                totalPagar,
                totalReceber.subtract(totalPagar),
                pedidosPendentes,
                projetosAndamento,
                totalClientes,
                ultimosPedidos,
                projetosProximosVencimento
        );
    }

    public List<DashboardUltimosPedidosDTO> getUltimosPedidos() {
        Pageable topFive = PageRequest.of(0, 5);
        return dashboardRepository.findUltimosPedidos(topFive);
    }
}
