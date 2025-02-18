package com.school.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String redirect(){
        return "redirect:/biblioteca";
    }

    @GetMapping("/biblioteca")
    public String index(Model model) {
        model.addAttribute("message", "Biblioteca online");
        return  "index";
    }

    @GetMapping("/biblioteca/carte/editare/{id}")
    public String updateBookForm(Model model) {
        model.addAttribute("message", "Editează detaliile cărții:");
        return "update_book";
    }

    @GetMapping("/biblioteca/carte/adaugare")
    public String addBookForm(Model model) {
        model.addAttribute("message", "Introdu detaliile cărții:");
        return "new_book";
    }
}

