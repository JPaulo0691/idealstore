package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.dto.response.Vaga.VagaResponse;
import org.example.idealstore.entity.Vaga;
import org.example.idealstore.exception.custom.CodigoUniqueViolationException;
import org.example.idealstore.exception.custom.EntityNotFoundException;
import org.example.idealstore.repository.VagaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga salvar(Vaga vaga){
        try {
            return vagaRepository.save(vaga);
        }catch (DataIntegrityViolationException ex){
            throw new CodigoUniqueViolationException(String.format("Vaga com código %s já cadastrada", vaga.getCodigo()));
        }
    }

    public Vaga buscarPorCodigo(String codigo){
        return vagaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Vaga com código %s não foi encontrada", codigo)));
    }
}
