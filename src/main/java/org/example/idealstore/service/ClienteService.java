package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.Cliente;
import org.example.idealstore.entity.Usuario;
import org.example.idealstore.exception.custom.CpfUniqueViolationException;
import org.example.idealstore.repository.ClienteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
}
