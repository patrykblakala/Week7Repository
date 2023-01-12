package com.sparta.week6project.webControllers;

import com.sparta.week6project.entities.Department;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web")
public class homeWebController {
    @GetMapping("/home")
    public String getHomePage(Model model){
        System.out.println("Loading home page");
        return "home/home";
    }
}
