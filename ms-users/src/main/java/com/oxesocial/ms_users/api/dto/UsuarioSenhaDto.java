package com.oxesocial.ms_users.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioSenhaDto {

    public UsuarioSenhaDto() {
    }

    public UsuarioSenhaDto(String senhaAtual, String novaSenha, String confirmaSenha) {
        this.senhaAtual = senhaAtual;
        this.novaSenha = novaSenha;
        this.confirmaSenha = confirmaSenha;
    }

    @NotBlank
    @Size(message = "Sua senha deve ter de 6 a 50 caracteres",min = 6, max = 50)
    private String senhaAtual;
    @NotBlank
    @Size(message = "Sua senha deve ter de 6 a 50 caracteres",min = 6, max = 50)
    private String novaSenha;
    @NotBlank
    @Size(message = "Sua senha deve ter de 6 a 50 caracteres",min = 6, max = 50)
    private String confirmaSenha;

    @Override
    public String toString() {
        return "UsuarioSenhaDto{" +
                "senhaAtual='" + senhaAtual + '\'' +
                ", novaSenha='" + novaSenha + '\'' +
                ", confirmaSenha='" + confirmaSenha + '\'' +
                '}';
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }
}
