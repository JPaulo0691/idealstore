package org.example.idealstore.repository;

import org.example.idealstore.entity.ClienteVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteVagaRepository extends JpaRepository<ClienteVaga, Long> {
}
