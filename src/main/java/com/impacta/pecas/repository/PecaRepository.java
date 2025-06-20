package com.impacta.pecas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.impacta.pecas.model.Peca;

public interface PecaRepository extends JpaRepository<Peca, Long> {

    boolean existsByCodigo(String codigo);

    Optional<Peca> findByCodigo(String codigo);
}
