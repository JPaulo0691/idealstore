package org.example.idealstore.repository;

import org.example.idealstore.dto.response.Cliente.ClienteResponseList;
import org.example.idealstore.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT C.ID," +
            "              C.NOME," +
            "              C.CPF," +
            "              C.CRIADO_POR," +
            "              C.DATA_CRIACAO," +
            "              C.MODIFICADO_POR," +
            "              C.DATA_MODIFICACAO," +
            "              U.ROLE" +
            "         FROM Clientes C" +
            "         JOIN USUARIOS U" +
            "           ON U.ID = C.ID_USUARIO", nativeQuery = true)
    List<ClienteResponseList> listarTodosClientes();
}
