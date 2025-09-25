package com.oxesocial.ms_users.jwt;

import com.oxesocial.ms_users.core.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class JwtUserDetails extends User {

    private Usuario usuario;

    public JwtUserDetails(Usuario usuario) {
        super(usuario.getEmail(), usuario.getSenha(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
    }

    public Long getID(){
        return this.usuario.getId();
    }

    public String getRole(){
        return this.usuario.getRole().name();
    }
}
