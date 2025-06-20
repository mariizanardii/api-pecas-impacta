package com.impacta.pecas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.impacta.pecas.model.Componente;

public interface ComponenteRepository extends JpaRepository<Componente, Long> {

    List<Componente> findAllByPeca_Codigo(String codigo);

}
