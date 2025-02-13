package com.school.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/admin/home")
    public String handleAdminHome(){
        return "home_admin";
    }
}

