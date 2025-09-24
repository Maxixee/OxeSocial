package com.oxesocial.ms_users.core.repository;

import com.oxesocial.ms_users.core.entity.Roles;
import com.oxesocial.ms_users.core.entity.Usuario;
import com.oxesocial.ms_users.core.repository.projections.UsuarioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("select c from Usuario c")
    Page<UsuarioProjection> findAllPageable(Pageable pageable);

    @Query("select u.role from Usuario u where u.email like :email")
    Roles findRoleByEmail(String email);
}
