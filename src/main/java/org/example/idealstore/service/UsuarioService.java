package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.Usuario;
import org.example.idealstore.exception.custom.EntityNotFoundException;
import org.example.idealstore.exception.custom.UsernameUniqueViolationException;
import org.example.idealstore.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try{
            return usuarioRepository.save(usuario);
        }catch (DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado", usuario.getUsername()));
        }

    }

    public List<Usuario> listarTodosUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long usuarioId){
        return usuarioRepository.findById(usuarioId)
                                .orElseThrow(() ->new EntityNotFoundException(String.format("Usuário id nr.%d não encontrado",usuarioId)));

    }

    @Transactional
    public Usuario atualizarSenha(Long idUsuario, String password, String novaSenha, String confirmaSenha) {

        if(!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com a confirmação de senha....");
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
