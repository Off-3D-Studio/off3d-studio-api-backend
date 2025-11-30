package com.off3dstudio.off3dbackend.controller;

import com.off3dstudio.off3dbackend.dto.DashboardTotaisResponse;
import com.off3dstudio.off3dbackend.model.Conta;
import com.off3dstudio.off3dbackend.service.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @GetMapping
    public List<Conta> listarTodos() {
        return contaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.buscarPorId(id));
    }

    @PostMapping
    public Conta criar(@Valid @RequestBody Conta conta) {
        return contaService.salvar(conta);
    }

    @PutMapping("/{id}")
    public Conta atualizar(@PathVariable Long id, @Valid @RequestBody Conta conta) {
        return contaService.salvar(conta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipo}")
    public List<Conta> buscarPorTipo(@PathVariable Conta.TipoConta tipo) {
        return contaService.buscarPorTipo(tipo);
    }

    @GetMapping("/vencidas")
    public List<Conta> buscarContasVencidas() {
        return contaService.buscarContasVencidas();
    }

    @GetMapping("/a-vencer")
    public List<Conta> buscarContasAVencer() {
        return contaService.buscarContasAVencer();
    }

    @GetMapping("/dashboard/totais")
    public ResponseEntity<DashboardTotaisResponse> getTotaisDashboard() {
        BigDecimal totalReceber = contaService.getTotalContasReceber();
        BigDecimal totalPagar = contaService.getTotalContasPagar();
        BigDecimal saldo = contaService.getSaldo();

        return ResponseEntity.ok(new DashboardTotaisResponse(totalReceber, totalPagar, saldo));
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<Conta> marcarComoPaga(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPagamento) {
        contaService.marcarComoPaga(id, dataPagamento);
        return ResponseEntity.ok(contaService.buscarPorId(id));
    }

}
