package com.oxesocial.ms_users.api.controller;

import com.oxesocial.ms_users.api.dto.UsuarioLoginDto;
import com.oxesocial.ms_users.api.exception.ErrorMessage;
import com.oxesocial.ms_users.jwt.JwtToken;
import com.oxesocial.ms_users.jwt.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("oxe/v1")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtUserDetailsService detailsService, AuthenticationManager authenticationManager) {
        this.detailsService = detailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioLoginDto loginDto, HttpServletRequest request){
        log.info("Iniciando login do usuario {}", loginDto.getEmail());
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(loginDto.getEmail());

            return ResponseEntity.ok(token);
        } catch (AuthenticationException e){
            log.error("BAD REQUEST FROM EMAIL: {}", loginDto.getEmail());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais invalidas"));
    }
}
