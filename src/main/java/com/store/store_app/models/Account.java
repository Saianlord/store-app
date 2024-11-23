package com.store.store_app.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private float balance;
    @Column(name= "last_updated")
    @CreationTimestamp
    private LocalDate lastUpdated;
}
