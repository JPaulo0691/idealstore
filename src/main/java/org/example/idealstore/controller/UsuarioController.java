package org.example.idealstore.controller;

import lombok.RequiredArgsConstructor;
import org.example.idealstore.dto.request.UsuarioRequest;
import org.example.idealstore.dto.request.UsuarioUpdatePassword;
import org.example.idealstore.dto.response.UsuarioResponse;
import org.example.idealstore.entity.Usuario;
import org.example.idealstore.mapper.ConvertDTO;
import org.example.idealstore.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ConvertDTO convertDTO;

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrarUsuario(@RequestBody UsuarioRequest usuarioDto){
        Usuario user = usuarioService.salvar(convertDTO.convertObjects(usuarioDto, Usuario.class));

        return ResponseEntity.status(HttpStatus.CREATED).body((UsuarioResponse) convertDTO.convertObjects(user, UsuarioResponse.class));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodosUsuario(){
        List<Usuario> user = convertDTO.convertListObjects(usuarioService.listarTodosUsuarios(), Usuario.class);
        List<UsuarioResponse> userListResponse = convertDTO.convertListObjects(user, UsuarioResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(userListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> encontrarUsuarioId(@PathVariable Long id){
        Usuario user = convertDTO.convertObjects(usuarioService.buscarPorId(id), Usuario.class);

        return ResponseEntity.status(HttpStatus.OK).body(convertDTO.convertObjects(user, UsuarioResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarSenhaUsuario(@PathVariable Long id, @RequestBody UsuarioUpdatePassword usuario){
        Usuario user = convertDTO.convertObjects(usuarioService.atualizarSenha(id
                                                                              , usuario.getSenhaAntiga()
                                                                              , usuario.getNovaSenha()
                                                                              , usuario.getConfirmaSenha())
                                                                              , Usuario.class);

        return ResponseEntity.noContent().build();
    }
}
