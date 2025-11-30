package com.off3dstudio.off3dbackend.service;

import com.off3dstudio.off3dbackend.model.Projeto;
import com.off3dstudio.off3dbackend.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public List<Projeto> buscarProjetosAtrasados() {
        return projetoRepository.findProjetosAtrasados();
    }

        public List<Projeto> buscarProjetosProximosVencimento() {
        return projetoRepository.findProjetosProximosVencimento();
    }

    public Long contarProjetosEmAndamento() {
        return projetoRepository.countByStatus(Projeto.StatusProjeto.EM_ANDAMENTO);
    }

    public Map<String, Long> getEstatisticasProjetos() {
        List<Object[]> resultados = projetoRepository.countProjetosByStatus();
        return resultados.stream()
                .collect(Collectors.toMap(
                        obj -> ((Projeto.StatusProjeto) obj[0]).name(),
                        obj -> (Long) obj[1]
                ));
    }

    public List<Projeto> buscarProjetosConcluidosMesAtual() {
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate fimMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return projetoRepository.findProjetosConcluidosNoPeriodo(inicioMes, fimMes);
    }

    public List<Projeto> listarTodos() {
        return projetoRepository.findAll();
    }

}
