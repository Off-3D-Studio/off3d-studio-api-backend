package com.off3dstudio.off3dbackend.repository;

import com.off3dstudio.off3dbackend.model.Conta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ContaRepositoryTest {

    @Autowired
    private ContaRepository contaRepository;

    @Test
    void sums_and_fluxo_caixa_query() {
        Conta c1 = new Conta();
        c1.setDescricao("C1"); c1.setValor(new BigDecimal("500")); c1.setDataVencimento(LocalDate.of(2025,11,10));
        c1.setTipo(Conta.TipoConta.RECEBER); c1.setStatus(Conta.StatusConta.PENDENTE);

        Conta c2 = new Conta();
        c2.setDescricao("C2"); c2.setValor(new BigDecimal("200")); c2.setDataVencimento(LocalDate.of(2025,11,10));
        c2.setTipo(Conta.TipoConta.PAGAR); c2.setStatus(Conta.StatusConta.PENDENTE);

        contaRepository.saveAll(List.of(c1, c2));

        BigDecimal totalReceber = contaRepository.sumContasReceberPendentes();
        BigDecimal totalPagar = contaRepository.sumContasPagarPendentes();

        assertThat(totalReceber).isEqualByComparingTo(new BigDecimal("500"));
        assertThat(totalPagar).isEqualByComparingTo(new BigDecimal("200"));

        // fluxo caixa for month range
        LocalDate ini = LocalDate.of(2025,11,1);
        LocalDate fim = LocalDate.of(2025,11,30);

        var result = contaRepository.findFluxoCaixaMensal(ini, fim);
        assertThat(result).isNotEmpty();
    }
}
