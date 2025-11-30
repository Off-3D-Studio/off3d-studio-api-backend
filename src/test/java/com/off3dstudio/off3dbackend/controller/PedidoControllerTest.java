package com.off3dstudio.off3dbackend.controller;

import com.off3dstudio.off3dbackend.model.Pedido;
import com.off3dstudio.off3dbackend.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class PedidoControllerTest {

    private PedidoService pedidoService;
    private PedidoController controller;

    @BeforeEach
    void setup() {
        pedidoService = Mockito.mock(PedidoService.class);
        controller = new PedidoController(pedidoService);
    }

    private Pedido samplePedido() {
        Pedido p = new Pedido();
        p.setCliente("Cliente X");
        p.setDescricao("Desc");
        p.setValor(new BigDecimal("1200.00"));
        p.setStatus(Pedido.StatusPedido.PENDENTE);
        return p;
    }

    @Test
    void basic_crud_paths_delegate_to_service() {
        Pedido p = samplePedido();

        when(pedidoService.listarTodos()).thenReturn(List.of(p));
        when(pedidoService.buscarPorId(1L)).thenReturn(p);
        when(pedidoService.salvar(p)).thenReturn(p);

        assertThat(controller.listarTodos()).containsExactly(p);

        ResponseEntity<Pedido> res = controller.buscarPorId(1L);
        assertThat(res.getBody()).isEqualTo(p);

        assertThat(controller.criar(p)).isEqualTo(p);
        assertThat(controller.atualizar(1L, p)).isEqualTo(p);

        // deletion returns no content; the service is mocked so just ensure no exception
        controller.deletar(1L);

        when(pedidoService.buscarPorStatus(Pedido.StatusPedido.PENDENTE)).thenReturn(List.of(p));
        assertThat(controller.buscarPorStatus(Pedido.StatusPedido.PENDENTE)).containsExactly(p);
    }
}
