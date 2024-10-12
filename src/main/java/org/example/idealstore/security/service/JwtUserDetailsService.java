package org.example.idealstore.security.service;

import lombok.RequiredArgsConstructor;
import org.example.idealstore.entity.Usuario;
import org.example.idealstore.enums.Role;
import org.example.idealstore.security.token.JwtToken;
import org.example.idealstore.security.userdetails.JwtUserDetails;
import org.example.idealstore.security.utils.JwtUtils;
import org.example.idealstore.service.UsuarioService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioService.buscarPorUsername(username);

        return new JwtUserDetails(usuario);
    }

    public JwtToken getTokenAuthenticated(String username){
        Role role= usuarioService.buscarRolePorUsername(username);

        return JwtUtils.createToken(username, role.name());
    }
}
