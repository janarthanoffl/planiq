package com.project.planiq.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="goals")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Goal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;


    @NotBlank
    @Column(nullable = false)
    private String title;


    // Progress from 0 to 100
    @Column(nullable = false)
    @Builder.Default
    private Integer progress = 0;

    @Column(nullable = false)
    @Builder.Default
    private Boolean completed = false;

    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
