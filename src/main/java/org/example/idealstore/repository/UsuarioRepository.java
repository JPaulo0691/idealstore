package org.example.idealstore.repository;

import org.example.idealstore.entity.Usuario;
import org.example.idealstore.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u.role FROM Usuario u WHERE u.username like :username")
    Role findRoleByUsername(String username);
}
