package org.example.idealstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.dto.request.ClienteVaga.EstacionamentoRequest;
import org.example.idealstore.dto.response.ClienteVaga.EstacionamentoResponse;
import org.example.idealstore.entity.ClienteVaga;
import org.example.idealstore.mapper.ConvertObjects;
import org.example.idealstore.service.EstacionamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/estacionamentos")
@RequiredArgsConstructor
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;
    private final ConvertObjects convertObjects;

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
}
