package com.github.hiltonws.doencionario.service.mapper;

import com.github.hiltonws.doencionario.dto.DoencaDTO;
import com.github.hiltonws.doencionario.model.Doenca;
import com.github.hiltonws.doencionario.service.mapper.generic.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class DoencaMapper extends AbstractMapper<Doenca, DoencaDTO> {
    @Override
    public DoencaDTO toDTO(Doenca entity) {
        if(entity == null){
            return null;
        }
        DoencaDTO dto = new DoencaDTO();
        dto.setCodigo(entity.getCodigo());
        dto.setDescricao(entity.getDescricao());
        return dto;
    }

    @Override
    public Doenca toObject(DoencaDTO dto) {
        if(dto == null){
            return null;
        }
        Doenca entity = new Doenca();
        entity.setCodigo(dto.getCodigo());
        entity.setDescricao(dto.getDescricao());
        entity.setId(dto.getId());
        return entity;
    }
}
