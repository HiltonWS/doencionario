package com.github.hiltonws.doencionario.repository;

import com.github.hiltonws.doencionario.model.Doenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoencaRepository extends JpaRepository<Doenca, Long> {
}
