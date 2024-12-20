package org.example.idealstore.repository;

import org.example.idealstore.entity.Vaga;
import org.example.idealstore.enums.StatusVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga,Long> {

    Optional<Vaga> findByCodigo(String codigo);

    Optional<Vaga> findFirstByStatus(StatusVaga statusVaga);

    List<Vaga> findAllByStatus(StatusVaga statusVaga);
}
