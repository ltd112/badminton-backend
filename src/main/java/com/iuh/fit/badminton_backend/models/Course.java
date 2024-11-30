package com.iuh.fit.badminton_backend.models;

import com.iuh.fit.badminton_backend.models.enums.CourseStatus;
import com.iuh.fit.badminton_backend.models.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level; // BEGINNER, INTERMEDIATE, ADVANCED

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseStatus status; // UPCOMING, ONGOING, COMPLETED, CANCELLED

    @Column(nullable = false)
    private int maxParticipants;

    @Column(nullable = false)
    private Double fee;

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private User coach;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;
}
