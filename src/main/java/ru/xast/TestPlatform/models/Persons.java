package ru.xast.TestPlatform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "persons")
public class Persons {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "surname")
    @NotEmpty(message = "Enter your surname!")
    @Size(min = 2, max = 60, message = "Your surname should be between 2 and 60!")
    private String surname;

    @Column(name = "name")
    @NotEmpty(message = "Enter your name!")
    @Size(min = 2, max = 60, message = "Your name should be between 2 and 60!")
    private String name;

    @Column(name = "patronymic")
    @NotEmpty(message = "Enter your patronymic!")
    @Size(min = 2, max = 60, message = "Your patronymic should be between 2 and 60!")
    private String patronymic;

    @Column(name = "phonenumber")
    @NotEmpty(message = "Enter your phone number!")
    @Pattern(regexp = "^(\\+7 $\\d{3}$ \\d{3}-\\d{2}-\\d{2}|\\+7\\d{10})$",
            message = "Phone number must be in the format +7 (XXX) XXX-XX-XX or +7XXXXXXXXXX!")
    private String phoneNumber;

    @Column(name = "email")
    @NotEmpty(message = "Enter your email!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Please, enter correct email")
    private String email;

    public Persons() {}

    public Persons(String surname, String name, String patronymic, String phoneNumber, String email) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

}