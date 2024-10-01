package org.example.idealstore.controller;

import lombok.RequiredArgsConstructor;
import org.example.idealstore.dto.request.UsuarioRequest;
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
    public ResponseEntity<List<Usuario>> listarTodosUsuario(){
        List<Usuario> user = usuarioService.listarTodosUsuarios();

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> encontrarUsuarioId(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarSenhaUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario user = usuarioService.atualizarSenha(id, usuario.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
