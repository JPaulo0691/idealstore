package org.example.idealstore.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioRequest {

    private String username;
    private String password;
}
