package com.sparta.week6project.webControllers;

import com.sparta.week6project.DAO.impl.EmployeeDAO;
import com.sparta.week6project.DTO.DepartmentDTO;
import com.sparta.week6project.DTO.EmployeeDTO;
import com.sparta.week6project.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/employees")
public class employeeWebController {
    @Autowired
    EmployeeDAO employeeDAO;

    @GetMapping("/all/{pageNum}")
    public String getAllEmployees(Model model,@PathVariable int pageNum){
        Page<Employee> employees = employeeDAO.findAllEmployees(pageNum);
        model.addAttribute("employees", employees);
        model.addAttribute("pageNum", pageNum);
        return "employee/displayAllEmployees";
    }

    @GetMapping("/{id}")
    public String getEmployeeById(Model model, @PathVariable int id){
        EmployeeDTO employeeDTO = employeeDAO.findById(id).orElse(null);
        model.addAttribute("employee",employeeDTO);
        return "employee/displayEmployee";
    }

    @GetMapping("/createEmployee")
    public String createEmployee(Model model){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        model.addAttribute("employee",employeeDTO);
        return "employee/createEmployeePage";
    }

    @PostMapping("/createSuccess")
    public String createDepartmentSuccess(@ModelAttribute("employee") EmployeeDTO employeeDTO, Model model){
        employeeDAO.save(employeeDTO);
        return "employee/createSuccessPage";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(Model model, @PathVariable int id){
        EmployeeDTO employeeDTO = employeeDAO.findById(id).orElse(null);
        model.addAttribute("employee",employeeDTO);
        return "employee/updateEmployeePage";
    }

    @PostMapping("/updateEmployee/updateSuccess")
    public String updateEmployeeSuccess(@ModelAttribute("employee") EmployeeDTO employeeDTO, Model model){
        employeeDAO.save(employeeDTO);
        return "employee/updateSuccessPage";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id, Model model){
        EmployeeDTO employeeDTO = employeeDAO.findById(id).orElse(null);
        if (employeeDTO != null){
            employeeDAO.deleteById(id);
        }
        model.addAttribute("employee",employeeDTO);
        return "employee/deleteSuccessPage";

    }
}
