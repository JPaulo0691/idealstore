package org.example.idealstore.dto.response.Usuario;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioResponse {

    private Long id;
    private String username;
    private String role;
}
