package com.off3dstudio.off3dbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "projetos")
@Data
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusProjeto status;

    private LocalDate dataInicio;
    private LocalDate dataPrevisaoTermino;
    private LocalDate dataTermino;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public enum StatusProjeto {
        RASCUNHO,
        EM_ANDAMENTO,
        PAUSADO,
        CONCLUIDO,
        CANCELADO
    }

}
