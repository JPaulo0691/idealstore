package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.ClienteVaga;
import org.example.idealstore.repository.ClienteVagaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteVagaService {

    private final ClienteVagaRepository vagaRepository;

    @Transactional
    public ClienteVaga cadastrar(ClienteVaga clienteVaga){
        return vagaRepository.save(clienteVaga);
    }
}
