package com.example.studdata.dao;

import com.example.studdata.model.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    void deleteById(Long id);
    List<Student> findStudentsByFirstNameOrLastNameOrMiddleName(String firstName, String lastName, String middleName);

    List<Student> findAll(Specification<Student> spec, Sort sort);
    List<Student> findAll(Specification<Student> spec);
    List<Student> findStudentsByIssuanceEndDateAfter(String currentDate);

}
