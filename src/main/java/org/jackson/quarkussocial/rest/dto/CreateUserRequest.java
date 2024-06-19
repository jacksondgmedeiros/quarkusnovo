package org.jackson.quarkussocial.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class CreateUserRequest {
    @NotNull(message = "Id Não pode ser nulo e nem repetido")
    private Long id;

//    verifica se é nula ou vazia
    @NotBlank(message = "Nome não pode ser nulo ou branco")
    private String name;

//    verifica se é nula
    @NotNull(message = "Idade não pode ser nula")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
