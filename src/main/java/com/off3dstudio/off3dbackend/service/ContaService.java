package com.off3dstudio.off3dbackend.service;

import com.off3dstudio.off3dbackend.model.Conta;
import com.off3dstudio.off3dbackend.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;

    public List<Conta> listarTodos() {
        return contaRepository.findAll();
    }

    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    public Conta salvar(Conta conta) {
        return contaRepository.save(conta);
    }

    public void deletar(Long id) {
        contaRepository.deleteById(id);
    }

    public List<Conta> buscarPorTipo(Conta.TipoConta tipo) {
        return contaRepository.findByTipo(tipo);
    }

    public List<Conta> buscarPorStatus(Conta.StatusConta status) {
        return contaRepository.findByStatus(status);
    }

    public List<Conta> buscarContasVencidas() {
        return contaRepository.findContasVencidas();
    }

    public List<Conta> buscarContasAVencer() {
        LocalDate hoje = LocalDate.now();
        LocalDate semanaProxima = hoje.plusDays(7);
        return contaRepository.findContasAVencer(hoje, semanaProxima);
    }

    public BigDecimal getTotalContasReceber() {
        return contaRepository.sumContasReceberPendentes();
    }

    public BigDecimal getTotalContasPagar() {
        return contaRepository.sumContasPagarPendentes();
    }

    public BigDecimal getSaldo() {
        BigDecimal totalReceber = getTotalContasReceber();
        BigDecimal totalPagar = getTotalContasPagar();
        return totalReceber.subtract(totalPagar);
    }

    public BigDecimal getTotalPagoNoPeriodo(LocalDate inicio, LocalDate fim) {
        return contaRepository.sumContasPagasNoPeriodo(inicio, fim);
    }

    public List<Conta> buscarContasPorPeriodo(LocalDate inicio, LocalDate fim) {
        return contaRepository.findByDataVencimentoBetween(inicio, fim);
    }

    public void marcarComoPaga(Long id, LocalDate dataPagamento) {
        Conta conta = buscarPorId(id);
        conta.setStatus(Conta.StatusConta.PAGO);
        conta.setDataPagamento(dataPagamento);
        salvar(conta);
    }

}
