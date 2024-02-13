package com.example.studdata.service.impl;

import com.example.studdata.dao.StudentRepository;
import com.example.studdata.model.Student;
import com.example.studdata.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository repository;
    @Override
    public List<Student> findAllStudent() {return repository.findAll();}

    @Override
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public void deleteStudent(Student student) {repository.delete(student);}

    @Override
    public void deleteStudentById(Long id) {repository.deleteById(id);}

    @Override
    public boolean existsStudentsById(Long id) {return repository.existsById(id);}

    @Override
    public Optional<Student> findStudentById(Long id) {return repository.findById(id);}

    @Override
    public List<Student> findStudentsByFirstNameOrLastNameOrMiddleName(String firstName, String lastName, String middleName) {return repository.findStudentsByFirstNameOrLastNameOrMiddleName(firstName, lastName, middleName);}

    @Override
    public List<Student> findAll(Specification<Student> spec, Sort sort) {return repository.findAll(spec, sort);}

    @Override
    public List<Student> findAll(Specification<Student> spec) {return repository.findAll(spec);}

    @Override
    public List<Student> findStudentsByIssuanceEndDateAfter(String currentDate) {return repository.findStudentsByIssuanceEndDateAfter(currentDate);};
}
