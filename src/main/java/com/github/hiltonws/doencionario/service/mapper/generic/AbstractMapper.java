package com.github.hiltonws.doencionario.service.mapper.generic;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsavel por mapear os dtos e as entidades
 * @param <E> Uma entidade
 * @param <DTO> Um DTO
 */
public abstract class AbstractMapper<E, DTO> {

    /**
     * Mapeia uma entidade para DTO
     * @param entity A entidade
     * @return Um DTO mapeado
     */
    public abstract DTO toDTO(E entity);

    /**
     * Mapeia um DTO para uma Entidade
     * @param dto Um DTO
     * @return Uma entidade mapeada
     */
    public abstract E toObject(DTO dto);

    /**
     * Mapeia uma lista de entidades para uma Lista e DTos
     * @param entities uUma  lista de entidades
     * @return Uma lista de DTO's  mepeados
     */
    public List<DTO> toDTOS(final List<E> entities) {
        List<DTO> dtos = Collections.emptyList();

        if ((entities != null) && !entities.isEmpty()) {
            dtos = entities.stream().map(this::toDTO).collect(Collectors.toList());
        }

        return dtos;

    }

    /**
     * Mapeia uma list de DTO's para uma lista de entidades
     * @param dtos Uma lista de dtos
     * @return Uma lista de entidades
     */
    public List<E> toObjects(final List<DTO> dtos) {
        List<E> obj = Collections.emptyList();

        if ((dtos != null) && !dtos.isEmpty()) {
            obj = dtos.stream().map(this::toObject).collect(Collectors.toList());
        }

        return obj;

    }


}
