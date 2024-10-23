package org.example.idealstore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.Usuario;
import org.example.idealstore.exception.custom.EntityNotFoundException;
import org.example.idealstore.exception.custom.PasswordInvalidException;
import org.example.idealstore.exception.custom.UsernameUniqueViolationException;
import org.example.idealstore.repository.UsuarioRepository;
import org.example.idealstore.service.interfaces.IUsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.idealstore.enums.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try{
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
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

    public Usuario buscarPorUsername(String username){
        return usuarioRepository
                .findByUsername(username)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Usuário com username %s não encontrado", username)));

    }

    @Transactional
    public Usuario atualizarSenha(Long idUsuario, String password, String novaSenha, String confirmaSenha) {

        if(!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidException("Nova senha não confere com a confirmação de senha....");
        }

        Usuario senhaUsuario = buscarPorId(idUsuario);

        if(!passwordEncoder.matches(password, senhaUsuario.getPassword())){
            throw new PasswordInvalidException("Sua senha não confere");
        }

        senhaUsuario.setPassword(passwordEncoder.encode(novaSenha));
        return  senhaUsuario;
    }

    public Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
