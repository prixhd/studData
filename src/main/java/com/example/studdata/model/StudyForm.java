package com.example.studdata.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@Table(name = "study_forms")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class StudyForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String name;
}
