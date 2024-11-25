package org.example.idealstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.idealstore.dto.request.Usuario.UsuarioRequest;
import org.example.idealstore.dto.request.Usuario.UsuarioUpdatePassword;
import org.example.idealstore.dto.response.Usuario.UsuarioResponse;
import org.example.idealstore.entity.Usuario;
import org.example.idealstore.exception.custom.EntityNotFoundException;
import org.example.idealstore.mapper.ConvertObjects;
import org.example.idealstore.service.UsuarioServiceImpl;
import org.example.idealstore.service.interfaces.IUsuarioService;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
public class UsuarioController {

    private final IUsuarioService usuarioService;
    private final ConvertObjects convertDTO;

    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
               security = @SecurityRequirement(name = "security"),
               responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e email já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Dados inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
               })
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrarUsuario(@Valid @RequestBody UsuarioRequest usuarioDto){
        Usuario user = usuarioService.salvar(convertDTO.convertObjects(usuarioDto, Usuario.class));

        return ResponseEntity.status(HttpStatus.CREATED).body((UsuarioResponse) convertDTO.convertObjects(user, UsuarioResponse.class));
    }

    @Operation(summary = "Listar todos os usuários", description = "Recurso para listar todos os usuários",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso retornado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
           })
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> listarTodosUsuario(){
        List<Usuario> user = convertDTO.convertListObjects(usuarioService.listarTodosUsuarios(), Usuario.class);
        List<UsuarioResponse> userListResponse = convertDTO.convertListObjects(user, UsuarioResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(userListResponse);
    }

    @Operation(summary = "Pesquisar usuários pelo id", description = "Recurso para trazer usuários específicos por id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') OR (hasAuthority('CLIENTE') AND #id == authentication.principal.id)")
    /* Tips: I was using hasRole('ADMIN'), however I was saving in the DATABASE as 'ADMIN', instead of 'ROLE_ADMIN'.
    The Spring Sec use in the backstage the prefix ROLE_. Because of this, it was necessary to change for hasAuthority('ADMIN')*/
    public ResponseEntity<UsuarioResponse> encontrarUsuarioId(@PathVariable Long id){

        Usuario user = convertDTO.convertObjects(usuarioService.buscarPorId(id), Usuario.class);
        System.out.println(user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(convertDTO.convertObjects(user, UsuarioResponse.class));
    }

    @Operation(summary = "Editar senha dos usuários por id", description = "Recurso para editar usuários específicos por id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário editado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Os dados fornecidos não estão corretos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntityNotFoundException.class)))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> atualizarSenhaUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioUpdatePassword usuario){
        Usuario user = convertDTO.convertObjects(usuarioService.atualizarSenha(id
                                                                              , usuario.getSenhaAntiga()
                                                                              , usuario.getNovaSenha()
                                                                              , usuario.getConfirmaSenha())
                                                                              , Usuario.class);
        return ResponseEntity.noContent().build();
    }
}
