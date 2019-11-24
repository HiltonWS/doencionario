package com.github.hiltonws.doencionario.controller;

import com.github.hiltonws.doencionario.dto.BeneFiciarioDoencaDTO;
import com.github.hiltonws.doencionario.dto.BeneficiarioDTO;
import com.github.hiltonws.doencionario.dto.DoencaDTO;
import com.github.hiltonws.doencionario.service.BeneficiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/v1/beneficiario")
@Tag(name = "beneficiario")
public class BeneficiarioController {
    @Autowired
    private BeneficiarioService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Salva um beneficiário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna o ID do Beneficiario"),
    })
    public ResponseEntity<Long> post(@RequestBody BeneficiarioDTO dto) {
        Long id = service.save(dto);
        if (id != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        }

        return ResponseEntity.noContent().build();

    }

    @GetMapping(value = "/{idBeneficiario}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retorna um beneficiario pela ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "O beneficiário buscado"),
            @ApiResponse(responseCode = "204", description = "Não há beneficiário com o id indicado"),
    })
    public ResponseEntity<BeneficiarioDTO> get(@PathVariable Long idBeneficiario) {
        BeneficiarioDTO dto = service.get(idBeneficiario);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/doenca", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Vincula uma doença ao beneficiario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Beneficiário {nome do beneficiário} vinculado à doença {descricao da doença} com sucesso."),
            @ApiResponse(responseCode = "204", description = "Não há beneficiário com o id indicado, ou doença como id indicado"),
    })
    public ResponseEntity<String> postDoenca(@RequestBody BeneFiciarioDoencaDTO beneFiciarioDoencaDTO){
        String msg = service.saveDoenca(beneFiciarioDoencaDTO);
        if(msg != null){
            return ResponseEntity.ok(msg);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{idBeneficiario}/doencas", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retorna as doencas do profissional")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Um array com as doenças do profissional"),
    })
    public ResponseEntity<List<DoencaDTO>> getDoencas(@PathVariable Long idBeneficiario){
        List<DoencaDTO> doencas = service.getDoencas(idBeneficiario);
        if(doencas != null){
            return ResponseEntity.ok(doencas);
        }
        return ResponseEntity.noContent().build();
    }

}
