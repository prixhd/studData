package com.example.studdata.model;

import jakarta.persistence.*;

import lombok.*;


@Data
@Entity
@Getter
@Setter
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final int course;
    private final String faculty;
    private final String studyForm;
    private final String scholarship;
    private final int orderNumber;

    @Temporal(TemporalType.DATE)
    private final String orderDate;

    @Temporal(TemporalType.DATE)
    private final String issuanceEndDate;

    @Temporal(TemporalType.DATE)
    private final String foundationEndDate;

    private final String foundationReason;
}
