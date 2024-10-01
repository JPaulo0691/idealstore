package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.Usuario;
import org.example.idealstore.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodosUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long usuarioId){
        return usuarioRepository.findById(usuarioId)
                                .orElseThrow(() ->new RuntimeException("Usuário não encontrado"));

    }

    @Transactional
    public Usuario atualizarSenha(Long idUsuario, String password) {

        Usuario senhaUsuario = buscarPorId(idUsuario);

        senhaUsuario.setPassword(password);

        return  senhaUsuario;

    }
}
