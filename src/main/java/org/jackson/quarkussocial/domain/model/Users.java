package org.jackson.quarkussocial.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
public class Users {

//    para dizer que é campo primary key
    @Id
// Para informar que é tipo auto incremento, mas no banco oracle não está criado assim
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    para informar o nome da coluna no banco
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id) && Objects.equals(name, users.name) && Objects.equals(age, users.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
