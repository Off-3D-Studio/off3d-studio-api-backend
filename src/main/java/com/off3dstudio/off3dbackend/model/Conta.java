package com.off3dstudio.off3dbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contas")
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    private TipoConta tipo;

    @Enumerated(EnumType.STRING)
    private StatusConta status;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public enum TipoConta {PAGAR, RECEBER}

    public enum StatusConta {PENDENTE, PAGO, ATRASADO}

}
