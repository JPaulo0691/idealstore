package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.Vaga;
import org.example.idealstore.exception.custom.CodigoUniqueViolationException;
import org.example.idealstore.exception.custom.EntityNotFoundException;
import org.example.idealstore.repository.VagaRepository;
import org.example.idealstore.service.interfaces.IVagaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.idealstore.enums.StatusVaga.LIVRE;

@RequiredArgsConstructor
@Service
public class VagaServiceImpl implements IVagaService {

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

    public Vaga buscarPorVagaLivre(){
        return vagaRepository.findFirstByStatus(LIVRE)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma Vaga foi encontrada"));
    }

    public List<Vaga> listarVagas(){
        return vagaRepository.findAll();
    }

    public List<Vaga> listarVagasDisponiveis(){
        return vagaRepository.findAllByStatus(LIVRE);
    }
}
