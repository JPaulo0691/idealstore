package org.example.idealstore.repository;

import org.example.idealstore.entity.Cliente;
import org.example.idealstore.repository.projection.ClienteProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("Select c from Cliente c")
    Page<ClienteProjection> findAllPageable(Pageable pageable);

}
