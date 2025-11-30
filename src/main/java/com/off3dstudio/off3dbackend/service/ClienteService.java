package com.off3dstudio.off3dbackend.service;

import com.off3dstudio.off3dbackend.model.Cliente;
import com.off3dstudio.off3dbackend.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAllByOrderByNomeAsc();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Optional<Cliente> buscarPorCpfCnpj(String cpfCnpj) {
        return clienteRepository.findByCpfCnpj(cpfCnpj);
    }

    public List<Cliente> buscarClientesComPedidos() {
        return clienteRepository.findClientesComPedidos();
    }

    public List<Cliente> buscarClientesSemPedidos() {
        return clienteRepository.findClientesSemPedidos();
    }

    public Long contarTotalClientes() {
        return clienteRepository.countAllBy();
    }

    public boolean existePorEmail(String email) {
        return clienteRepository.findByEmail(email).isPresent();
    }

    public boolean existePorCpfCnpj(String cpfCnpj) {
        return clienteRepository.findByCpfCnpj(cpfCnpj).isPresent();
    }

}
