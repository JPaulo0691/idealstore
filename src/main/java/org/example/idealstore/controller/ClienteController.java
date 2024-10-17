package org.example.idealstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.idealstore.dto.request.Cliente.ClienteRequest;
import org.example.idealstore.dto.response.Cliente.ClientePageable;
import org.example.idealstore.dto.response.Cliente.ClienteResponse;
import org.example.idealstore.dto.response.Cliente.ClienteResponseList;
import org.example.idealstore.entity.Cliente;
import org.example.idealstore.mapper.ConvertObjects;
import org.example.idealstore.repository.projection.ClienteProjection;
import org.example.idealstore.repository.projection.DetalheProjection;
import org.example.idealstore.security.userdetails.JwtUserDetails;
import org.example.idealstore.service.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClienteResponse> encontrarCliente(@PathVariable Long id){

        Cliente cliente = clienteService.buscarClientePorId(id);
        ClienteResponse response = convert.convertObjects(cliente,ClienteResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClientePageable> listarTodosClientes(Pageable pageable){

        Page<ClienteProjection> listarTodos = clienteService.listarTodos(pageable);
        ClientePageable responsePageable = convert.convertObjects(listarTodos, ClientePageable.class);

        return ResponseEntity.status(HttpStatus.OK).body(responsePageable);
    }

    @GetMapping("/detalhe")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<ClienteResponse> buscarDetalhesDoCliente(@AuthenticationPrincipal JwtUserDetails userDetails){

        DetalheProjection cliente = clienteService.buscarPorUsuario(userDetails.getId());
        ClienteResponse clienteResponse = convert.convertObjects(cliente, ClienteResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(clienteResponse);
    }
}
