package org.example.idealstore.service.interfaces;

import org.example.idealstore.entity.ClienteVaga;

public interface IClienteVagaService {

    ClienteVaga cadastrar(ClienteVaga clienteVaga);
    ClienteVaga buscarPorRecibo(String recibo);
    long getTotalDeVezesEstacionamentoCompleto(String cpfCliente);
}
