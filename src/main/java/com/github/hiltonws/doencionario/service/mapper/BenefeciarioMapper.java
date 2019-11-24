package com.github.hiltonws.doencionario.service.mapper;

import com.github.hiltonws.doencionario.dto.BeneficiarioDTO;
import com.github.hiltonws.doencionario.service.mapper.generic.AbstractMapper;
import com.github.hiltonws.doencionario.model.Beneficiario;
import org.springframework.stereotype.Component;

@Component
public class BenefeciarioMapper extends AbstractMapper<Beneficiario, BeneficiarioDTO> {
    @Override
    public BeneficiarioDTO toDTO(Beneficiario entity) {
        if(entity == null){
            return null;
        }

        BeneficiarioDTO dto = new BeneficiarioDTO();
        dto.setNome(entity.getNome());
        dto.setDtNascimento(entity.getDtNascimento());
        return dto;
    }

    @Override
    public Beneficiario toObject(BeneficiarioDTO dto) {
        if(dto == null){
            return null;
        }

        Beneficiario entity = new Beneficiario();
        entity.setDtNascimento(dto.getDtNascimento());
        entity.setNome(dto.getNome());
        return entity;
    }
}
