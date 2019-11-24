package com.github.hiltonws.doencionario.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BeneFiciarioDoencaDTO {
    @NotNull(message = "O id do beneficiario deve ser preenchido")
    private Long idBeneficiario;
    @NotNull(message = "O id da doenca deve ser preenchido")
    private Long  idDoenca;
}
