package org.example.idealstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.dto.request.ClienteVaga.EstacionamentoRequest;
import org.example.idealstore.dto.response.ClienteVaga.EstacionamentoResponse;
import org.example.idealstore.entity.ClienteVaga;
import org.example.idealstore.mapper.ConvertObjects;
import org.example.idealstore.service.interfaces.IClienteVagaService;
import org.example.idealstore.service.interfaces.IEstacionamentoService;
import org.example.idealstore.service.interfaces.IVagaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/estacionamentos")
@RequiredArgsConstructor
public class EstacionamentoController {

    private final IEstacionamentoService estacionamentoService;
    private final IVagaService vagaService;
    private final ConvertObjects convertObjects;
    private final IClienteVagaService clienteVagaService;

    @PostMapping("/check-in")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EstacionamentoResponse> checkInPark(@RequestBody @Valid EstacionamentoRequest request){
        ClienteVaga clienteVaga = estacionamentoService.checkIn(convertObjects.convertObjects(request, ClienteVaga.class));
        EstacionamentoResponse response = convertObjects.convertObjects(clienteVaga, EstacionamentoResponse.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{recibo}")
                .buildAndExpand(clienteVaga.getRecibo())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("check-in/{recibo}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<EstacionamentoResponse> getByRecibo(@PathVariable String recibo){
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
        EstacionamentoResponse response = convertObjects.convertObjects(clienteVaga, EstacionamentoResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("check-out/{recibo}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EstacionamentoResponse> checkOut(@PathVariable String recibo){
        ClienteVaga clienteVaga = estacionamentoService.checkout(recibo);
        EstacionamentoResponse response = convertObjects.convertObjects(clienteVaga, EstacionamentoResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
