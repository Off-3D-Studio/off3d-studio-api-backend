package com.off3dstudio.off3dbackend.repository;

import com.off3dstudio.off3dbackend.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    // Buscar projetos por status
    List<Projeto> findByStatus(Projeto.StatusProjeto status);

    // Contar projetos por status
    Long countByStatus(Projeto.StatusProjeto status);

    // Buscar projetos por pedido associado
    List<Projeto> findByPedidoId(Long pedidoId);

    // Buscar projetos com data de previsão de término dentro de um período
    List<Projeto> findByDataPrevisaoTerminoBetween(LocalDate startDate, LocalDate endDate);

    // Buscar projetos atrasados (data previsão < hoje e status EM_ANDAMENTO)
    @Query("SELECT p FROM Projeto p WHERE p.dataPrevisaoTermino < CURRENT_DATE AND p.status = 'EM_ANDAMENTO'")
    List<Projeto> findProjetosAtrasados();

    // NOVO MÉTODO: Buscar projetos que estão próximos do vencimento (próximos 7 dias)
    default List<Projeto> findProjetosProximosVencimento() {
        LocalDate hoje = LocalDate.now();
        LocalDate dataLimite = hoje.plusDays(7);
        return findByDataPrevisaoTerminoBetweenAndStatus(hoje, dataLimite, Projeto.StatusProjeto.EM_ANDAMENTO);
    }

    // NOVO MÉTODO: Necessário para o método acima
    List<Projeto> findByDataPrevisaoTerminoBetweenAndStatus(LocalDate startDate, LocalDate endDate, Projeto.StatusProjeto status);

    // Buscar projetos por nome (case insensitive)
    List<Projeto> findByNomeContainingIgnoreCase(String nome);

    // Buscar projetos concluídos em um período específico
    @Query("SELECT p FROM Projeto p WHERE p.status = 'CONCLUIDO' AND p.dataTermino BETWEEN :startDate AND :endDate")
    List<Projeto> findProjetosConcluidosNoPeriodo(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);

    // Buscar projeto pelo nome exato
    Optional<Projeto> findByNome(String nome);

    // Buscar projetos sem pedido associado
    @Query("SELECT p FROM Projeto p WHERE p.pedido IS NULL")
    List<Projeto> findProjetosSemPedido();

    // Estatísticas de projetos por status
    @Query("SELECT p.status, COUNT(p) FROM Projeto p GROUP BY p.status")
    List<Object[]> countProjetosByStatus();
}