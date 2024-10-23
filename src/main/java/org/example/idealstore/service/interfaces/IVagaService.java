package org.example.idealstore.service.interfaces;

import org.example.idealstore.entity.Vaga;

public interface IVagaService {

    Vaga salvar(Vaga vaga);
    Vaga buscarPorCodigo(String codigo);
    Vaga buscarPorVagaLivre();

}
