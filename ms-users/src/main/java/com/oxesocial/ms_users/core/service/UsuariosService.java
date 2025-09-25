package com.oxesocial.ms_users.core.service;

import com.oxesocial.ms_users.core.entity.Roles;
import com.oxesocial.ms_users.core.entity.Usuario;
import com.oxesocial.ms_users.core.exception.EmailUniqueViolationException;
import com.oxesocial.ms_users.core.exception.EntityNotFoundException;
import com.oxesocial.ms_users.core.exception.PasswordInvalidException;
import com.oxesocial.ms_users.core.repository.UsuariosRepository;
import com.oxesocial.ms_users.core.repository.projections.UsuarioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuariosService {

    private final UsuariosRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuariosService(UsuariosRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario salvar(Usuario usuario){
        try {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            if(!usuario.getEmail().equals("hiego@gmail.com")){
                usuario.setRole(Roles.ROLE_USER);
            }else {
                usuario.setRole(Roles.ROLE_ADMIN);
            }
            return repository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException e){
            throw new EmailUniqueViolationException(String.format("O email %s já está em uso", usuario.getEmail()));
        }
    }

    @Transactional
    public Usuario trocarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha){
        if(!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidException("A nova senha e a confirmação da senha são diferentes");
        }

        if(senhaAtual.equals(novaSenha)){
            throw new PasswordInvalidException("A nova senha tem que ser diferente da atual");
        }

        Usuario user = getById(id);
        if (!passwordEncoder.matches(user.getSenha(), senhaAtual)){
            throw new PasswordInvalidException("Sua senha atual está incorreta ");
        }

        user.setSenha(passwordEncoder.encode(novaSenha));
        return user;

    }

    @Transactional(readOnly = true)
    public Usuario getById(Long id){
        return repository.findById(id).orElseThrow(
                () ->  new EntityNotFoundException(String.format("Usuario com id = %s não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public Usuario getByEmail(String email){
        return repository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com email = %s não encontrado", email))
        );
    }

    @Transactional(readOnly = true)
    public Page<UsuarioProjection> getAllUsuario(Pageable pageable){
        return repository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Roles getRoleByEmail(String email){
        return repository.findRoleByEmail(email);
    }

}
