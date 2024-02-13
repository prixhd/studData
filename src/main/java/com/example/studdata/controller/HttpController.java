package com.example.studdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;

@Controller
public class HttpController {

    @GetMapping("/")
    public String homeStudentPage(Model model) {
        model.addAttribute("title", "Студенческая база данных");
        model.addAttribute("pageActiveHome", "nav-link active");
        model.addAttribute("pageActiveAdd", "nav-link");
        model.addAttribute("pageActiveShow", "nav-link");
        return "index";
    }

    @GetMapping("/add")
    public String addStudentPage(Model model) {
        model.addAttribute("title", "Добавление студента в бд");
        model.addAttribute("pageActiveHome", "nav-link");
        model.addAttribute("pageActiveAdd", "nav-link active");
        model.addAttribute("pageActiveShow", "nav-link");
        return "add";
    }
}
