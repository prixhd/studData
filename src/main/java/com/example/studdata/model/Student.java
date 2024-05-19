package com.example.studdata.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;


@Data
@Entity
@Getter
@Setter
@Table(name = "students")
@AllArgsConstructor
//@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private int course;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "study_form_id")
    private StudyForm studyForm;

    @ManyToOne
    @JoinColumn(name = "scholarship_id")
    private Scholarship scholarship;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private Foundation foundation;

    private Long orderNumber;

    @Temporal(TemporalType.DATE)
    private LocalDate orderDate;

    @Temporal(TemporalType.DATE)
    private LocalDate issuanceEndDate;

    @Temporal(TemporalType.DATE)
    private LocalDate foundationEndDate;

    public Student(String firstName, String lastName, String middleName,
                   int course, Faculty faculty, StudyForm studyForm,
                   Scholarship scholarship, Foundation foundation,
                   Long orderNumber, LocalDate orderDate, LocalDate issuanceEndDate, LocalDate foundationEndDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.course = course;
        this.faculty = faculty;
        this.studyForm = studyForm;
        this.scholarship = scholarship;
        this.foundation = foundation;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.issuanceEndDate = issuanceEndDate;
        this.foundationEndDate = foundationEndDate;
    }


}
