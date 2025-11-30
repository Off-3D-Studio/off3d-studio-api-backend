package com.off3dstudio.off3dbackend.repository;

import com.off3dstudio.off3dbackend.dto.DashboardUltimosPedidosDTO;
import com.off3dstudio.off3dbackend.model.Pedido;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.status = 'PENDENTE'")
    Long countPedidosPendentes();

    @Query("SELECT COUNT(pj) FROM Projeto pj WHERE pj.status = 'EM_ANDAMENTO'")
    Long countProjetosAndamento();

    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c WHERE c.tipo = 'RECEBER' AND c.status = 'PENDENTE'")
    BigDecimal sumTotalReceber();

    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c WHERE c.tipo = 'PAGAR' AND c.status = 'PENDENTE'")
    BigDecimal sumTotalPagar();

    @Query("SELECT new com.off3dstudio.off3dbackend.dto.DashboardUltimosPedidosDTO(" +
            "p.id, p.descricao, p.valor, p.status, p.dataCriacao, p.cliente) " +
            "FROM Pedido p " +
            "ORDER BY p.dataCriacao DESC")
    List<DashboardUltimosPedidosDTO> findUltimosPedidos(Pageable pageable);

}
