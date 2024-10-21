package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.Cliente;
import org.example.idealstore.entity.ClienteVaga;
import org.example.idealstore.entity.Vaga;
import org.example.idealstore.enums.StatusVaga;
import org.example.idealstore.utils.EstacionamentoUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final ClienteService clienteService;
    private final ClienteVagaService clienteVagaService;
    private final VagaService vagaService;

    @Transactional
    public ClienteVaga checkIn(ClienteVaga clienteVaga){
        Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCpfCliente());
        clienteVaga.setCliente(cliente);

        Vaga vaga = vagaService.buscarPorVagaLivre();
        vaga.setStatus(StatusVaga.OCUPADA);
        clienteVaga.setVaga(vaga);

        clienteVaga.setDataEntrada(LocalDateTime.now());
        clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

        return clienteVagaService.cadastrar(clienteVaga);
    }

    @Transactional
    public ClienteVaga checkout(String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);

        LocalDateTime dataSaida = LocalDateTime.now();

        BigDecimal valor= EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(), dataSaida);
        clienteVaga.setValor(valor);

        long totalVezesQueEstacionouConsecutivamente = clienteVagaService
                .getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCpfCliente());

        BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor,totalVezesQueEstacionouConsecutivamente);
        clienteVaga.setDesconto(desconto);

        clienteVaga.setDataSaida(dataSaida);
        clienteVaga.getVaga().setStatus(StatusVaga.LIVRE);

       return clienteVagaService.cadastrar(clienteVaga);

    }




}
