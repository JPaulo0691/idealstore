package org.example.idealstore.dto.response;

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
