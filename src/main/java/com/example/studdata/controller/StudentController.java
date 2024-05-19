package com.example.studdata.controller;

import com.example.studdata.dao.FacultyRepository;
import com.example.studdata.dao.FoundationRepository;
import com.example.studdata.dao.ScholarshipRepository;
import com.example.studdata.dao.StudyFormRepository;
import com.example.studdata.model.*;
import com.example.studdata.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final FacultyRepository facultyRepository;
    private final ScholarshipRepository scholarshipRepository;
    private final FoundationRepository foundationRepository;
    private final StudyFormRepository studyFormRepository;

    @GetMapping("/show")
    public String findAllStudents(Model model) {
        List<Student> students = studentService.findAllStudent();
        model.addAttribute("students", students);
        model.addAttribute("title", "Список студентов в БД");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link");
        model.addAttribute("pageActiveShow", "nav-link active");
        return "show/students-show";
    }

    @GetMapping("/add")
    public String saveStudentForm(Model model) {
        model.addAttribute("title", "Добавление студента");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link active");
        model.addAttribute("pageActiveShow", "nav-link");

        return "add/students-add";
    }

    @PostMapping("/add")
    public String saveStudent(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String middleName,
                              @RequestParam int course,
                              @RequestParam Long facultyId,
                              @RequestParam Long studyFormId,
                              @RequestParam Long scholarshipId,
                              @RequestParam Long foundationId,
                              @RequestParam Long orderNumber,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate orderDate,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate issuanceEndDate,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate foundationEndDate,
                              Model model) {

        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(); // Получаем объект Faculty
        StudyForm studyForm = studyFormRepository.findById(studyFormId).orElseThrow(); // Получаем объект StudyForm
        Scholarship scholarship = scholarshipRepository.findById(scholarshipId).orElseThrow(); // Получаем объект Scholarship
        Foundation foundation = foundationRepository.findById(foundationId).orElseThrow(); // Получаем объект Foundation

        Student student = new Student(
                firstName,
                lastName,
                middleName,
                course,
                faculty,
                studyForm,
                scholarship,
                foundation,
                orderNumber,
                orderDate,
                issuanceEndDate,
                foundationEndDate
        );

        model.addAttribute("title", "Добавление студента");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link active");
        model.addAttribute("pageActiveShow", "nav-link");

        studentService.saveStudent(student);

        return "add/students-add";
    }

    @GetMapping("/edit/{id}")
    public String updateStudent(@PathVariable(value = "id") long id, Model model) {
        if(!studentService.existsStudentsById(id)) {
            return "redirect:/students/show";
        }

        Optional<Student> student = studentService.findStudentById(id);
        ArrayList<Student> res = new ArrayList<>();
        student.ifPresent(res::add);
        model.addAttribute("students", res);
        model.addAttribute("title", "Редактирование студента");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link active");
        model.addAttribute("pageActiveShow", "nav-link");

        return "add/students-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateEditStudent(@PathVariable(value = "id") long id,
                                    @RequestParam String firstName,
                                    @RequestParam String lastName,
                                    @RequestParam String middleName,
                                    @RequestParam int course,
                                    @RequestParam Long facultyId,
                                    @RequestParam Long studyFormId,
                                    @RequestParam Long scholarshipId,
                                    @RequestParam Long foundationId,
                                    @RequestParam Long orderNumber,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate orderDate,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate issuanceEndDate,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate foundationEndDate,
                                    Model model) {

        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(); // Получаем объект Faculty
        StudyForm studyForm = studyFormRepository.findById(studyFormId).orElseThrow(); // Получаем объект StudyForm
        Scholarship scholarship = scholarshipRepository.findById(scholarshipId).orElseThrow(); // Получаем объект Scholarship
        Foundation foundation = foundationRepository.findById(foundationId).orElseThrow(); // Получаем объект Foundation

        Student student = new Student(
                id,
                firstName,
                lastName,
                middleName,
                course,
                faculty,
                studyForm,
                scholarship,
                foundation,
                orderNumber,
                orderDate,
                issuanceEndDate,
                foundationEndDate
        );

        model.addAttribute("title", "Редактирование студента");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link active");
        model.addAttribute("pageActiveShow", "nav-link");

        studentService.saveStudent(student);

        return "redirect:/students/show";
    }



    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id, Model model) {
        Student student = studentService.findStudentById(id).orElseThrow();
        studentService.deleteStudent(student);

        return "redirect:/students/show";
    }

    @GetMapping("show/search/{name}")
    public String searchStudent(@PathVariable(value = "name") String name, Model model) {

        List<Student> students = studentService.findStudentsByFirstNameOrLastNameOrMiddleName(name, name, name);

        model.addAttribute("students", students);
        model.addAttribute("title", "Список студентов в БД");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link");
        model.addAttribute("pageActiveShow", "nav-link active");

        return "show/students-show";
    }
    @GetMapping("show/filter")
    public String filterStudents(@RequestParam(value = "faculty", required = false) Optional<Long> facultyId,
                                 @RequestParam(value = "scholarship", required = false) Optional<Long> scholarshipId,
                                 @RequestParam(value = "studyForm", required = false) Optional<Long> studyFormId,
                                 @RequestParam(value = "foundation", required = false) Optional<Long> foundationId,
                                 @RequestParam(value = "sortColumn", required = false) String sortColumn,
                                 @RequestParam(value = "sortDirection", required = false) String sortDirection, Model model) {
        Specification<Student> spec = Specification.where(null);

        Faculty faculty = facultyId.map(id -> facultyRepository.findById(id).orElse(null)).orElse(null);
        StudyForm studyForm = studyFormId.map(id -> studyFormRepository.findById(id).orElse(null)).orElse(null);
        Scholarship scholarship = scholarshipId.map(id -> scholarshipRepository.findById(id).orElse(null)).orElse(null);
        Foundation foundation = foundationId.map(id -> foundationRepository.findById(id).orElse(null)).orElse(null);

        if (faculty != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("faculty"), faculty),
                            criteriaBuilder.isNull(root.get("faculty"))
                    ));
        }

        if (scholarship != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("scholarship"), scholarship),
                            criteriaBuilder.isNull(root.get("scholarship"))
                    ));
        }

        if (studyForm != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("studyForm"), studyForm),
                            criteriaBuilder.isNull(root.get("studyForm"))
                    ));
        }

        if (foundation != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("foundation"), foundation),
                            criteriaBuilder.isNull(root.get("foundation"))
                    ));
        }

        Sort sort = null;
        if (sortColumn != null && sortDirection != null && !sortColumn.isEmpty() && !sortDirection.isEmpty()) {
            sort = Sort.by(Sort.Direction.fromString(sortDirection), sortColumn);
        }

        List<Student> students;
        if (sort != null) {
            students = studentService.findAll(spec, sort);
        } else {
            students = studentService.findAll(spec);
        }
        model.addAttribute("students", students);
        model.addAttribute("title", "Список студентов в БД");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link");
        model.addAttribute("pageActiveShow", "nav-link active");

        return "show/students-show";
    }

    @GetMapping("show/check")
    public String checkDateOfStudents(Model model) {
        LocalDate currentDate = LocalDate.now();
        List<Student> students = studentService.findStudentsByIssuanceEndDateAfter(currentDate);

        model.addAttribute("students", students);
        model.addAttribute("title", "Список студентов в БД");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link");
        model.addAttribute("pageActiveShow", "nav-link active");

        return "show/students-show";
    }

    @PostMapping("show/deleteStudents")
    public String deleteCheckedStudents(@RequestParam("selectedStudents") List<Long> selectedStudentIds, Model model) {
        for (Long studentId : selectedStudentIds) {
            studentService.deleteStudentById(studentId);
        }

        return "redirect:/students/show";
    }


}
