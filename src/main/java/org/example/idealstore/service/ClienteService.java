package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.Cliente;
import org.example.idealstore.entity.Usuario;
import org.example.idealstore.exception.custom.CpfUniqueViolationException;
import org.example.idealstore.exception.custom.EntityNotFoundException;
import org.example.idealstore.repository.ClienteRepository;
import org.example.idealstore.repository.projection.ClienteProjection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public Cliente cadastrar(Cliente cliente, Long id){
        try{
            Usuario usuario = usuarioService.buscarPorId(id);
            cliente.setUsuario(usuario);

            return clienteRepository.save(cliente);
        }catch (DataIntegrityViolationException ex){
            throw new CpfUniqueViolationException(
                    String.format("Cpf %s já está cadastrado no sistema", cliente.getCpf())
            );
        }
    }

    public Cliente buscarClientePorId(Long id){

        return clienteRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("O cliente id:%s não foi encontrado", id)));
    }

    public Page<ClienteProjection> listarTodos(Pageable pageable){
        return clienteRepository.findAllPageable(pageable);
    }
}
