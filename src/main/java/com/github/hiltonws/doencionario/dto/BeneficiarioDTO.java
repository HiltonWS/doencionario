package com.github.hiltonws.doencionario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiarioDTO {
    private Long id;
    private String nome;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dtNascimento;
}

