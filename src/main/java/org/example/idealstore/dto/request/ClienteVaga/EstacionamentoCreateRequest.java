package org.example.idealstore.dto.request.ClienteVaga;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstacionamentoCreateRequest {

    @NotBlank
    @Size
    @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "A placa do veículo deve seguir o padrão: XXX-0000")
    private String recibo;

    @NotBlank
    private String placa;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotBlank
    private String cor;

    @NotBlank
    @Size(min = 11, max = 11)
    @CPF
    private String clienteCpf;

}
