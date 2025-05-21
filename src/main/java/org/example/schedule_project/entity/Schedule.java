package org.example.schedule_project.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String todo;

    @Column(nullable = false)
    private String writeUser;

    @Column(nullable = false)
    private String password;

    public Schedule() {

    }

    public Schedule(String todo, String writeUser, String password) {
        this.todo = todo;
        this.writeUser = writeUser;
        this.password = password;
    }

    public void update(String todo, String writeUser){
        this.todo = todo;
        this.writeUser = writeUser;
    }

}
