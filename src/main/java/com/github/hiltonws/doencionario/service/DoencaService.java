package com.github.hiltonws.doencionario.service;

import com.github.hiltonws.doencionario.dto.DoencaDTO;
import com.github.hiltonws.doencionario.model.Doenca;
import com.github.hiltonws.doencionario.repository.DoencaRepository;
import com.github.hiltonws.doencionario.service.mapper.DoencaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoencaService {

    @Autowired
    private DoencaService service;

    @Autowired
    private DoencaRepository repository;

    @Autowired
    private DoencaMapper mapper;


    public Long save(DoencaDTO dto){
        Doenca save = repository.save(mapper.toObject(dto));
        return save.getId();
    }

    public DoencaDTO get(Long id){
        return mapper.toDTO(repository.findById(id).orElse(null));
    }
}
