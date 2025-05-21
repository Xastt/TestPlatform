package ru.xast.TestPlatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "question")
public class Question {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "content")
    @NotEmpty(message = "Enter the question!")
    @Size(min = 2, max = 1000, message = "Your question should be between 2 and 1000!")
    private String content;

    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL)
    private List<Options> questionOptions = new ArrayList<>();

    public Question() {}

    public Question(String content) {
        this.content = content;
    }

}
