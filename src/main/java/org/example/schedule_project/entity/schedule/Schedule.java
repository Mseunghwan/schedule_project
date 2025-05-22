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

    @ManyToOne(fetch = FetchType.LAZY) // 여러 일정이 하나의 유저에 매핑됨
    @JoinColumn(name = "user_id", nullable = false) // 실제 FK 컬럼명
    private User user;

    @Column(nullable = false)
    private String password;

    public Schedule() {

    }

    public Schedule(String todo, User user, String password) {
        this.todo = todo;
        this.user = user;
        this.password = password;
    }

    public void update(String todo, User user){
        this.todo = todo;
        this.user = user;
    }

}
