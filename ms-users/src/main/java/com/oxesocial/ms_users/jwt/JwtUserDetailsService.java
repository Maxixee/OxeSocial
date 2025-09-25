package com.oxesocial.ms_users.jwt;

import com.oxesocial.ms_users.core.entity.Roles;
import com.oxesocial.ms_users.core.entity.Usuario;
import com.oxesocial.ms_users.core.service.UsuariosService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuariosService usuariosService;

    public JwtUserDetailsService(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    //pra pegar o contexto
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuariosService.getByEmail(username);
        return new JwtUserDetails(usuario);
    }

    //pra por a role no contexto
    public JwtToken getTokenAuthenticated(String email){
        Roles role = usuariosService.getRoleByEmail(email);
        return JwtUtils.createToken(email, role.name().substring("ROLE_".length()));
    }
}
