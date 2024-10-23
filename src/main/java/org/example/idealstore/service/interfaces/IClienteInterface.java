package org.example.idealstore.service.interfaces;

import org.example.idealstore.entity.Cliente;
import org.example.idealstore.repository.projection.ClienteProjection;
import org.example.idealstore.repository.projection.DetalheProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteInterface {

    Cliente cadastrar(Cliente cliente, Long id);
    Cliente buscarClientePorId(Long id);
    Page<ClienteProjection> listarTodos(Pageable pageable);
    DetalheProjection buscarPorUsuario(Long id);
    Cliente buscarPorCpf(String cpf);
}
