package com.oxesocial.ms_users.api.dto.mapper;

import com.oxesocial.ms_users.api.dto.UsuarioCreateDto;
import com.oxesocial.ms_users.api.dto.UsuarioResponseDto;
import com.oxesocial.ms_users.core.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto dto){
        return new ModelMapper().map(dto, Usuario.class);
    }

    public static UsuarioResponseDto toDto(Usuario usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }
}
