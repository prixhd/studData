package com.example.studdata.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name = "foundations")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Foundation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String name;
}
