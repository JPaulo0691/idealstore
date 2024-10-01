package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.Usuario;
import org.example.idealstore.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Usuario atualizarSenha(Long idUsuario, String password, String novaSenha, String confirmaSenha) {

        if(!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com a confirmação de senha....");
        }

         if(novaSenha.equals("")) {
             throw new RuntimeException("Você não pode ter uma nova senha em branco");
         }

         if(!novaSenha.matches(".{6,}")){
             throw new RuntimeException("Você não pode ter uma nova senha menor que 6 dígitos.");
         }
        Usuario senhaUsuario = buscarPorId(idUsuario);

        if(!senhaUsuario.getPassword().equals(password)){
            throw new RuntimeException("Sua senha não confere");
        }

        if (senhaUsuario.getPassword().equals(novaSenha)){
            throw new RuntimeException("Senha Atual não pode ser igual a mesma senha");
        }

        senhaUsuario.setPassword(novaSenha);
        return  senhaUsuario;

    }
}
