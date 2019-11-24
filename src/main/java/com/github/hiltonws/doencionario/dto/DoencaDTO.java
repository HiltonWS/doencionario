package com.github.hiltonws.doencionario.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoencaDTO {
    private Long id;
    private String codigo;
    private String descricao;
}
