package com.off3dstudio.off3dbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Cliente é obrigatório!")
    private String cliente;

    @NotBlank(message = "Descrição é obrigatório!")
    private String descricao;

    @NotNull(message = "Valor é obrigatório!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public enum StatusPedido {
        PENDENTE,
        EM_ANDAMENTO,
        CONCLUIDO,
        CANCELADO
    }
}
