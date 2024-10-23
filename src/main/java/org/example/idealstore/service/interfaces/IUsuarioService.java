package org.example.idealstore.service.interfaces;

import org.example.idealstore.entity.Usuario;
import org.example.idealstore.enums.Role;

import java.util.List;

public interface IUsuarioService {

    Usuario salvar(Usuario usuario);
    List<Usuario> listarTodosUsuarios();
    Usuario buscarPorId(Long usuarioId);
    Usuario buscarPorUsername(String username);
    Usuario atualizarSenha(Long idUsuario, String password, String novaSenha, String confirmaSenha);
    Role buscarRolePorUsername(String username);
}
