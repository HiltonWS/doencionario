package com.github.hiltonws.doencionario.service;

import com.github.hiltonws.doencionario.dto.BeneFiciarioDoencaDTO;
import com.github.hiltonws.doencionario.dto.BeneficiarioDTO;
import com.github.hiltonws.doencionario.dto.DoencaDTO;
import com.github.hiltonws.doencionario.model.Doenca;
import com.github.hiltonws.doencionario.repository.DoencaRepository;
import com.github.hiltonws.doencionario.service.mapper.BenefeciarioMapper;
import com.github.hiltonws.doencionario.model.Beneficiario;
import com.github.hiltonws.doencionario.repository.BeneficiarioRepository;
import com.github.hiltonws.doencionario.service.mapper.DoencaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BeneficiarioService {

    @Autowired
    private BeneficiarioRepository repository;

    @Autowired
    private DoencaRepository doencaRepository;

    @Autowired
    private BenefeciarioMapper mapper;

    @Autowired
    private DoencaMapper doencaMapper;

    public Long save(BeneficiarioDTO dto){
        Beneficiario save = repository.save(mapper.toObject(dto));
        return save.getId();
    }

    public BeneficiarioDTO get(Long id){
        return mapper.toDTO(repository.findById(id).orElse(null));
    }

    public String saveDoenca(BeneFiciarioDoencaDTO beneFiciarioDoencaDTO) {

        Beneficiario beneficiario = repository.findById(beneFiciarioDoencaDTO.getIdBeneficiario()).orElse(null);
        if(beneficiario == null){
            return null;
        }
        Doenca doenca = doencaRepository.findById(beneFiciarioDoencaDTO.getIdDoenca()).orElse(null);

        if(doenca == null){
            return null;
        }

        List<Doenca> doencas = beneficiario.getDoencas();
        if(doencas == null){
            doencas = new ArrayList<>();
        }

        if(doencas.contains(doenca)){
            return String.format("O Beneficiário %s já possui a doença %s vinculada.", beneficiario.getNome(), doenca.getDescricao());
        }

        doencas.add(doenca);
        repository.save(beneficiario);
        return String.format("Beneficiário %s vinculado à doença %s com sucesso.", beneficiario.getNome(), doenca.getDescricao());
    }

    public List<DoencaDTO> getDoencas( Long idBeneficiario){
        return doencaMapper.toDTOS(repository.getDoencas(idBeneficiario));
    }
}
