package com.example.spring_bot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity(name = "userProfile")
public class UserProfile {
    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "age")
    private int age;

    @Column(name = "registered")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp registered;
}
