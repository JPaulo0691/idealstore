package org.example.idealstore.service.interfaces;

import org.example.idealstore.entity.ClienteVaga;

public interface IEstacionamentoService {

    ClienteVaga checkIn(ClienteVaga clienteVaga);
    ClienteVaga checkout(String recibo);

}
