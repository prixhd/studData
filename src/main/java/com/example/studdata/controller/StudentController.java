package com.example.studdata.controller;

import com.example.studdata.dao.StudentRepository;
import com.example.studdata.model.Student;
import com.example.studdata.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService service;
    private final StudentRepository repository;

    @GetMapping("/show")
    public String findAllStudents(Model model) {
        List<Student> students = service.findAllStudent();
        model.addAttribute("students", students);
        model.addAttribute("title", "Список студентов в БД");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link");
        model.addAttribute("pageActiveShow", "nav-link active");
        return "students-show";
    }

    @GetMapping("/add")
    public String saveStudentForm(Model model) {
        model.addAttribute("title", "Добавление студента");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link active");
        model.addAttribute("pageActiveShow", "nav-link");

        return "students-add";
    }

    @PostMapping("/add")
    public String saveStudent(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String middleName,
                              @RequestParam int course,
                              @RequestParam String faculty,
                              @RequestParam String studyForm,
                              @RequestParam String scholarship,
                              @RequestParam int orderNumber,
                              @RequestParam String orderDate,
                              @RequestParam String issuanceEndDate,
                              @RequestParam String foundationEndDate,
                              @RequestParam String foundationReason, Model model) {

        Student student = new Student(firstName, lastName, middleName,
                course, faculty, studyForm,
                scholarship, orderNumber, orderDate,
                issuanceEndDate, foundationEndDate, foundationReason);

        model.addAttribute("title", "Добавление студента");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link active");
        model.addAttribute("pageActiveShow", "nav-link");

        service.saveStudent(student);


        return "students-add";
    }

    @GetMapping("/edit/{id}")
    public String updateStudent(@PathVariable(value = "id") long id, Model model) {
        if(!service.existsStudentsById(id)) {
            return "redirect:/students/show";
        }

        Optional<Student> student = service.findStudentById(id);
        ArrayList<Student> res = new ArrayList<>();
        student.ifPresent(res::add);
        model.addAttribute("students", res);
        model.addAttribute("title", "Редактирование студента");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link active");
        model.addAttribute("pageActiveShow", "nav-link");

        return "/students-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateEditStudent(@PathVariable(value = "id") long id,
                                    @RequestParam String firstName,
                                    @RequestParam String lastName,
                                    @RequestParam String middleName,
                                    @RequestParam int course,
                                    @RequestParam String faculty,
                                    @RequestParam String studyForm,
                                    @RequestParam String scholarship,
                                    @RequestParam int orderNumber,
                                    @RequestParam String orderDate,
                                    @RequestParam String issuanceEndDate,
                                    @RequestParam String foundationEndDate,
                                    @RequestParam String foundationReason, Model model) {

        Student student = new Student(id, firstName, lastName, middleName,
                course, faculty, studyForm, scholarship, orderNumber,
                orderDate, issuanceEndDate, foundationEndDate, foundationReason);

        model.addAttribute("title", "Редактирование студента");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link active");
        model.addAttribute("pageActiveShow", "nav-link");

        service.updateStudent(student);

        return "redirect:/students/show";
    }



    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id, Model model) {
        Student student = service.findStudentById(id).orElseThrow();
        service.deleteStudent(student);

        return "redirect:/students/show";
    }

    @GetMapping("show/search/{name}")
    public String searchStudent(@PathVariable(value = "name") String name, Model model) {

        List<Student> students = service.findStudentsByFirstNameOrLastNameOrMiddleName(name, name, name);

        model.addAttribute("students", students);
        model.addAttribute("title", "Список студентов в БД");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link");
        model.addAttribute("pageActiveShow", "nav-link active");

        return "/students-show";
    }

    @GetMapping("show/filter")
    public String filterStudents(@RequestParam(value = "faculty", required = false) String faculty,
                                     @RequestParam(value = "scholarship", required = false) String scholarship,
                                     @RequestParam(value = "studyForm", required = false) String studyForm,
                                     @RequestParam(value = "foundationReason", required = false) String foundationReason,
                                     @RequestParam(value = "sortColumn", required = false) String sortColumn,
                                     @RequestParam(value = "sortDirection", required = false) String sortDirection, Model model) {
        Specification<Student> spec = Specification.where(null);

        if (faculty != null && !faculty.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("faculty"), faculty));
        }

        if (scholarship != null && !scholarship.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("scholarship"), scholarship));
        }

        if (studyForm != null && !studyForm.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("studyForm"), studyForm));
        }

        if (foundationReason != null && !foundationReason.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("foundationReason"), foundationReason));
        }

        Sort sort = null;
        if (sortColumn != null && sortDirection != null && !sortColumn.isEmpty() && !sortDirection.isEmpty()) {
            sort = Sort.by(Sort.Direction.fromString(sortDirection), sortColumn);
        }

        List<Student> students;
        if (sort != null) {
            students = service.findAll(spec, sort);
        } else {
            students = service.findAll(spec);
        }
        model.addAttribute("students", students);
        model.addAttribute("title", "Список студентов в БД");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link");
        model.addAttribute("pageActiveShow", "nav-link active");

        return "students-show";
    }

    @GetMapping("show/check")
    public String checkDateOfStudents(Model model) {
        LocalDate currentDate = LocalDate.now();
        String currentDateString = currentDate.toString();
        List<Student> students = service.findStudentsByIssuanceEndDateAfter(currentDateString);

        model.addAttribute("students", students);
        model.addAttribute("title", "Список студентов в БД");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link");
        model.addAttribute("pageActiveShow", "nav-link active");

        return "students-show";
    }


}
