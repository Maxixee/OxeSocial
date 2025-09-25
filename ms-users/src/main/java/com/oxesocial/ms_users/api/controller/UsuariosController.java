package com.oxesocial.ms_users.api.controller;

import com.oxesocial.ms_users.api.dto.PageableDto;
import com.oxesocial.ms_users.api.dto.UsuarioCreateDto;
import com.oxesocial.ms_users.api.dto.UsuarioResponseDto;
import com.oxesocial.ms_users.api.dto.UsuarioSenhaDto;
import com.oxesocial.ms_users.api.dto.mapper.PageableMapper;
import com.oxesocial.ms_users.api.dto.mapper.UsuarioMapper;
import com.oxesocial.ms_users.api.exception.ErrorMessage;
import com.oxesocial.ms_users.core.entity.Usuario;
import com.oxesocial.ms_users.core.repository.projections.UsuarioProjection;
import com.oxesocial.ms_users.core.service.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("oxe/v1/usuarios")
public class UsuariosController {

    private final UsuariosService service;

    public UsuariosController(UsuariosService service) {
        this.service = service;
    }

    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> salvar(@Valid @RequestBody UsuarioCreateDto createDto){
        Usuario usuario = service.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(usuario));
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
        Usuario usuario = service.getById(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(usuario));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDto> getByEmail(@PathVariable String email){
        Usuario usuario = service.getByEmail(email);
        return ResponseEntity.ok(UsuarioMapper.toDto(usuario));
    }

    @GetMapping("/all-users")
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true)
                                                  @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
        Page<UsuarioProjection> usuarios = service.getAllUsuario(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(usuarios));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> trocarSenha(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto){
        service.trocarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.ok("Senha atualizada com sucesso!!!");
    }

}
