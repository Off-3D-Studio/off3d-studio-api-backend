package com.off3dstudio.off3dbackend.repository;

import com.off3dstudio.off3dbackend.dto.RelatorioVendasDTO;
import com.off3dstudio.off3dbackend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByStatus(Pedido.StatusPedido status);
    List<Pedido> findByClienteContainingIgnoreCase(String cliente);

    Long countByStatus(Pedido.StatusPedido status);

    List<Pedido> findTop5ByOrderByDataCriacaoDesc();

    @Query("SELECT p FROM Pedido p WHERE p.dataCriacao BETWEEN :inicio AND :fim")
    List<Pedido> findByPeriodo(@Param("inicio") LocalDateTime inicio,
                               @Param("fim") LocalDateTime fim);

            @Query("""
                                SELECT function('date', p.dataCriacao), COUNT(p), SUM(p.valor)
                    FROM Pedido p
                    WHERE p.dataCriacao BETWEEN :inicio AND :fim
                    GROUP BY function('date', p.dataCriacao)
                    ORDER BY function('date', p.dataCriacao)
                    """)
            List<Object[]> findVendasPorPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    @Query("""
            SELECT COALESCE(SUM(p.valor), 0)
            FROM Pedido p
            WHERE YEAR(p.dataCriacao) = YEAR(CURRENT_DATE)
              AND MONTH(p.dataCriacao) = MONTH(CURRENT_DATE)
            """)
    BigDecimal findTotalVendasMesAtual();

    @Query("""
                SELECT COALESCE(SUM(p.valor), 0)
                FROM Pedido p
                WHERE YEAR(p.dataCriacao) = :year AND MONTH(p.dataCriacao) = :month
            """)
    BigDecimal sumVendasByMonth(@Param("year") int year, @Param("month") int month);

    @Query("""
                SELECT COUNT(DISTINCT p.cliente)
                FROM Pedido p
                WHERE YEAR(p.dataCriacao) = :year AND MONTH(p.dataCriacao) = :month
            """)
    Long countNovosClientesByMonth(@Param("year") int year, @Param("month") int month);

}
