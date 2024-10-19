package org.example.idealstore.dto.response.Vaga;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VagaResponse {

    private Long id;
    private String codigo;
    private String status;
}
