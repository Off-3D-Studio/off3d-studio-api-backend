package com.off3dstudio.off3dbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Objects;

@Data
@AllArgsConstructor
public class RelatorioVendasDTO {

    private LocalDate data;
        // JPA constructor expressions that use DATE(p.dataCriacao) return java.sql.Date in many dialects;
        // provide an additional constructor that accepts java.sql.Date so JPQL 'new' instantiation can match.
        public RelatorioVendasDTO(Date data, Long quantidadePedidos, BigDecimal valorTotal) {
            this.data = data == null ? null : data.toLocalDate();
            this.quantidadePedidos = quantidadePedidos;
            this.valorTotal = valorTotal;
        }

        public RelatorioVendasDTO(Timestamp ts, Long quantidadePedidos, BigDecimal valorTotal) {
            this.data = ts == null ? null : ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            this.quantidadePedidos = quantidadePedidos;
            this.valorTotal = valorTotal;
        }

        public RelatorioVendasDTO(java.util.Date date, Long quantidadePedidos, BigDecimal valorTotal) {
            if (Objects.isNull(date)) this.data = null;
            else this.data = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            this.quantidadePedidos = quantidadePedidos;
            this.valorTotal = valorTotal;
        }
    private Long quantidadePedidos;
    private BigDecimal valorTotal;

}
