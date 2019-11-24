package com.github.hiltonws.doencionario.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Beneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;

    @Temporal(TemporalType.DATE)
    private Date dtNascimento;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Doenca> doencas;
}
