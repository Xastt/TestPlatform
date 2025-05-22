package ru.xast.TestPlatform.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "test_results")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Persons person;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private Integer score;
    private Integer maxPossibleScore;
    private LocalDateTime completedAt;

}
