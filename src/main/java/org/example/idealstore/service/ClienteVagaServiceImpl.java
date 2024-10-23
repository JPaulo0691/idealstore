package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.ClienteVaga;
import org.example.idealstore.exception.custom.EntityNotFoundException;
import org.example.idealstore.repository.ClienteVagaRepository;
import org.example.idealstore.service.interfaces.IClienteVagaService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteVagaServiceImpl implements IClienteVagaService {

    private final ClienteVagaRepository vagaRepository;

    @Transactional
    public ClienteVaga cadastrar(ClienteVaga clienteVaga){
        return vagaRepository.save(clienteVaga);
    }

    public ClienteVaga buscarPorRecibo(String recibo){
        return vagaRepository.findByReciboAndDataSaidaIsNull(recibo)
                .orElseThrow(
                        () -> new EntityNotFoundException("Recibo %s não encontrado no sistema ou check-ou já realizado.")
                );
    }

    public long getTotalDeVezesEstacionamentoCompleto(String cpfCliente) {
        return vagaRepository.countByClienteCpfAndDataSaidaIsNotNull(cpfCliente);
    }
}
