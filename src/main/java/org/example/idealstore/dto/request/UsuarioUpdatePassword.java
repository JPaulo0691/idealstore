package org.example.idealstore.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioUpdatePassword {

    private String senhaAntiga;
    private String novaSenha;
    private String confirmaSenha;
}
