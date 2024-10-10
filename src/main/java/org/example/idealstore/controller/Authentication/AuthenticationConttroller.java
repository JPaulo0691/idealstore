package org.example.idealstore.controller.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.idealstore.dto.request.UsuarioLoginRequest;
import org.example.idealstore.exception.web.ErrorMessage;
import org.example.idealstore.jwt.token.JwtToken;
import org.example.idealstore.jwt.service.JwtUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationConttroller {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginRequest loginRequest, HttpServletRequest servletRequest){

        log.info("Processo de autenticação pelo login '{}'", loginRequest.getUsername());
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()); // busca no BD um usuário e senha que existe

            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthenticated(loginRequest.getUsername());

            return ResponseEntity.ok(token);

        }catch (AuthenticationException ex){
            log.warn("Bad Credentials from username '{}'", loginRequest.getUsername());
        }

        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(servletRequest, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }
}
