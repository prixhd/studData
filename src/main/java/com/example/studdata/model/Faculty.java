package com.example.studdata.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@Table(name = "faculties")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String name;
}
