package org.jackson.quarkussocial.rest.dto;

import org.jackson.quarkussocial.domain.model.Posts;

import java.time.LocalDateTime;

public class PostResponse {
    private String texto;
    private LocalDateTime dateTime;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static  PostResponse FromEntity(Posts post){
        var response = new  PostResponse();
        response.setTexto(post.getTexto());
        response.setDateTime(post.getDateTime());
        return response;
    }
}
