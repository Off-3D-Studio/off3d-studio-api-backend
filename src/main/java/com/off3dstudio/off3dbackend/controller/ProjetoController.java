package com.off3dstudio.off3dbackend.controller;

import com.off3dstudio.off3dbackend.model.Projeto;
import com.off3dstudio.off3dbackend.service.ProjetoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService projetoService;

    @GetMapping
    public List<Projeto> listarTodos() {
        return projetoService.listarTodos();
    }

    @GetMapping("/atrasados")
    public List<Projeto> listarAtrasados() {
        return projetoService.buscarProjetosAtrasados();
    }

    @GetMapping("/estatisticas")
    public Map<String, Long> getEstatisticas() {
        return projetoService.getEstatisticasProjetos();
    }

    @GetMapping("/proximos-vencimento")
    public List<Projeto> listarProximosVencimento() {
        return projetoService.buscarProjetosProximosVencimento();
    }

}
