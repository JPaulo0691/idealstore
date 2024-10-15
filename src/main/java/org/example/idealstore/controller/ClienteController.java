package org.example.idealstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.idealstore.dto.request.Cliente.ClienteRequest;
import org.example.idealstore.dto.response.Cliente.ClienteResponse;
import org.example.idealstore.entity.Cliente;
import org.example.idealstore.mapper.ConvertObjects;
import org.example.idealstore.security.userdetails.JwtUserDetails;
import org.example.idealstore.service.ClienteService;
import org.example.idealstore.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clientes")
@Slf4j
public class ClienteController {

    private final ConvertObjects convert;
    private final ClienteService clienteService;

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody @Valid ClienteRequest request,
                                                            @AuthenticationPrincipal JwtUserDetails userDetails){

        Cliente cliente = convert.convertObjects(request, Cliente.class);
        clienteService.cadastrar(cliente, userDetails.getId());

        ClienteResponse clienteResponse = convert.convertObjects(cliente, ClienteResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);

    }
}
