package com.off3dstudio.off3dbackend.repository;

import com.off3dstudio.off3dbackend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByCpfCnpj(String cpfCnpj);

    List<Cliente> findByTelefoneContaining(String telefone);

    @Query("SELECT DISTINCT c FROM Cliente c WHERE SIZE(c.pedidos) > 0")
    List<Cliente> findClientesComPedidos();

    @Query("SELECT c FROM Cliente c WHERE SIZE(c.pedidos) = 0")
    List<Cliente> findClientesSemPedidos();

    Long countAllBy();

    List<Cliente> findAllByOrderByNomeAsc();

    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.pedidos p WHERE p.status = :status")
    List<Cliente> findClientesComPedidosStatus(@Param("status") String status);

    @Query("SELECT c, COUNT(p) as totalPedidos FROM Cliente c LEFT JOIN c.pedidos p GROUP BY c ORDER BY totalPedidos DESC")
    List<Object[]> findTopClientesByPedidosCount();

    @Query("""
            SELECT c.nome, COUNT(p), COALESCE(SUM(p.valor), 0)
            FROM Cliente c
            LEFT JOIN c.pedidos p
            GROUP BY c.nome
            ORDER BY COUNT(p) DESC
            """)
    List<Object[]> findTopClientesByPedidosCountAndValor();

}
