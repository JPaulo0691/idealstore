package org.example.idealstore.repository;

import org.example.idealstore.entity.Cliente;
import org.example.idealstore.repository.projection.ClienteProjection;
import org.example.idealstore.repository.projection.DetalheProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("Select c from Cliente c")
    Page<ClienteProjection> findAllPageable(Pageable pageable);

    @Query(value = "SELECT C.ID," +
            "              C.NOME," +
            "              C.CPF," +
            "              U.ROLE" +
            "         FROM CLIENTES C" +
            "         JOIN USUARIOS U" +
            "           ON U.ID = C.ID_USUARIO" +
            "        WHERE C.ID_USUARIO = :id", nativeQuery = true)
    DetalheProjection findByUsuarioId(@Param("id") Long id);
}
