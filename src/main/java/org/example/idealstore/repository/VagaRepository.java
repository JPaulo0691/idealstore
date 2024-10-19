package org.example.idealstore.repository;

import org.example.idealstore.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga,Long> {

    Optional<Vaga> findByCodigo(String codigo);
}
