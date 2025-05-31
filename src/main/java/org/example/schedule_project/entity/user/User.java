package org.example.schedule_project.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.schedule_project.entity.BaseEntity;

@Getter
@Entity
@Table(name="User")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    public User() {

    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void update(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.username = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
