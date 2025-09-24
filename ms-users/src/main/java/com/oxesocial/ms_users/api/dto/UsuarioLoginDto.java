package com.oxesocial.ms_users.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioLoginDto {

    public UsuarioLoginDto() {
    }

    public UsuarioLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @NotBlank
    @Email(message = "formato do e-mail está invalido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;
    @NotBlank
    @Size(message = "Sua senha deve ter de 6 a 50 caracteres",min = 6, max = 50)
    private String password;

    @Override
    public String toString() {
        return "UsuarioLoginDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
