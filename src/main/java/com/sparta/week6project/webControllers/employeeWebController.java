package com.sparta.week6project.webControllers;

import com.sparta.week6project.DAO.impl.EmployeeDAO;
import com.sparta.week6project.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web/employees")
public class employeeWebController {
    @Autowired
    EmployeeDAO employeeDAO;

    @GetMapping("/{pageNum}")
    public String getAllEmployees(Model model,@PathVariable Integer pageNum){
        Page<Employee> employees = employeeDAO.findAllEmployees(pageNum);
        model.addAttribute("employees", employees);
        model.addAttribute("pageNum", pageNum);
        return "employee/displayAllEmployees";
    }
}
