package org.example.schedule_project.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.schedule_project.entity.BaseEntity;
import org.example.schedule_project.entity.user.User;

@Getter
@Entity
@Table(name="schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String todo;

    @JoinColumn(name="userId")
    private Long userId;

    @Column(nullable = false)
    private String password;

    public Schedule() {

    }

    public Schedule(String todo, Long userId, String password) {
        this.todo = todo;
        this.userId = userId;
        this.password = password;
    }

    public void update(String todo, Long userId){
        this.todo = todo;
        this.userId = userId;
    }

}
