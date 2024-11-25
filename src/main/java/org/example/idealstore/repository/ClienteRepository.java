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

import java.util.List;
import java.util.Optional;

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

    @Query("SELECT c.id, "+
            "c.nome, "+
            "c.cpf, " +
            "u.role "+
            "FROM Cliente c " +
            "JOIN Usuario u " +
            "ON u.id = c.usuario.id " +
            "WHERE c.usuario.id = :id")
    List<Cliente> findAllClienteDetalhe(@Param("id") Long id);

    Optional<Cliente> findByCpf(String cpf);



}
