package com.github.hiltonws.doencionario.controller;

import com.github.hiltonws.doencionario.dto.DoencaDTO;
import com.github.hiltonws.doencionario.service.DoencaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/doenca")
@Tag(name = "doenca")
public class DoencaController {
    @Autowired
    private DoencaService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Salva uma Doença")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna o ID da doença"),
    })
    public ResponseEntity<Long> post(@RequestBody DoencaDTO dto) {
        Long id = service.save(dto);
        if (id != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        }

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{idDoenca}")
    @Operation(description = "Retorna uma doença")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna a doença consultada"),
            @ApiResponse(responseCode = "204", description = "Não há doença com o id indicado")
    })
    public ResponseEntity<DoencaDTO> get(@PathVariable Long idDoenca) {
        DoencaDTO dto = service.get(idDoenca);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.noContent().build();
    }

}
