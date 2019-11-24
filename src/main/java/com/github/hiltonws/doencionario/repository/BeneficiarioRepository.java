package com.github.hiltonws.doencionario.repository;

import com.github.hiltonws.doencionario.model.Beneficiario;
import com.github.hiltonws.doencionario.model.Doenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {

    @Query("SELECT e1.doencas FROM Beneficiario e1 WHERE e1.id = :idBeneficiario")
    List<Doenca> getDoencas(@Param("idBeneficiario") Long idBeneficiario);
}
