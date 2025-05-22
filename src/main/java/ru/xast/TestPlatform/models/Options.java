package ru.xast.TestPlatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "options")
public class Options {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question quest;

    @Column(name = "option_content")
    @NotEmpty(message = "Enter the option content of the product!")
    @Size(min = 2, max = 1000, message = "Your option content should be between 2 and 1000!")
    private String option_content;

    @Column(name = "is_correct")
    private Boolean isCorrect = false;

    public Options() {}

    public Options(String option_content) {
        this.option_content = option_content;
    }

}
