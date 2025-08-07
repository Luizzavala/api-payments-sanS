package me.quadradev.application.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    @Column(nullable = true)
    private String middleName;

    private String lastName;

    @Column(nullable = true)
    private String secondLastName;

    private String phoneNumber;

    private String address;

    @OneToOne(mappedBy = "person")
    private User user;
}
