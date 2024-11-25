package org.example.idealstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.dto.request.Vaga.VagaRequest;
import org.example.idealstore.dto.response.Vaga.VagaResponse;
import org.example.idealstore.entity.Vaga;
import org.example.idealstore.mapper.ConvertObjects;
import org.example.idealstore.service.VagaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

    private final VagaServiceImpl vagaService;
    private final ConvertObjects convertObjects;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid VagaRequest vagaRequest){
        Vaga vaga = vagaService.salvar(convertObjects.convertObjects(vagaRequest, Vaga.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(vaga.getCodigo())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<VagaResponse> buscarCodigo(@PathVariable String codigo){

        Vaga vaga = convertObjects.convertObjects(vagaService.buscarPorCodigo(codigo), Vaga.class);
        VagaResponse vagaResponse = convertObjects.convertObjects(vaga, VagaResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(vagaResponse);
    }

    @GetMapping("/listar-todas-vagas")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<VagaResponse>> listarVagas(){
        List<Vaga> vagas = convertObjects.convertListObjects(vagaService.listarVagas(), Vaga.class);
        List<VagaResponse> vagasResponse = convertObjects.convertListObjects(vagas, VagaResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(vagasResponse);
    }

    @GetMapping("/listar-vagas-disponiveis")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<VagaResponse>> listarVagasDisponiveis(){
        List<Vaga> vagas = convertObjects.convertListObjects(vagaService.listarVagasDisponiveis(), Vaga.class);
        List<VagaResponse> vagasResponse = convertObjects.convertListObjects(vagas, VagaResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(vagasResponse);
    }
}
