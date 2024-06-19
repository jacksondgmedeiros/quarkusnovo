package org.jackson.quarkussocial.rest.dto;


import jakarta.validation.constraints.NotBlank;

public class CreatePostRequest {

    @NotBlank
    private String texto;

    public @NotBlank String getTexto() {
        return texto;
    }

    public void setTexto( String texto) {
        this.texto = texto;
    }
}
