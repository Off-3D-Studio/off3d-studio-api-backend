package com.off3dstudio.off3dbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório!")
    private String nome;

    private String cpfCnpj;
    private String email;
    private String telefone;
    private String endereco;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
}
