package com.sparta.week6project.webControllers;

import com.sparta.week6project.DAO.impl.DepartmentDAO;
import com.sparta.week6project.DTO.DepartmentDTO;
import com.sparta.week6project.entities.Department;
import com.sparta.week6project.mappers.DepartmentMapper;
import com.sparta.week6project.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web/departments")
public class departmentWebController {

    @Autowired
    DepartmentDAO departmentDAO;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentMapper departmentMapper;

    @GetMapping("/basic/")
    public String getAllDepartments(Model model){
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "department/displayAllDepartments";
    }

    @GetMapping("/basic/{id}")
    public String getDepartmentById(@PathVariable String id, Model model){
        Optional<DepartmentDTO> departmentOptional = departmentDAO.findByDept_No(id);
        DepartmentDTO department = null;
        if(departmentOptional.isPresent()){
            department = departmentOptional.get();
        }
        model.addAttribute("department",department);
        return "department/displayDepartment";
    }

    @GetMapping("/update/createDepartment")
    public String createDepartment(Model model){
        DepartmentDTO department = new DepartmentDTO();
        model.addAttribute("department",department);
        return "department/createDepartmentPage";
    }

    @PostMapping("/update/createSuccess")
    public String createDepartmentSuccess(@ModelAttribute("department")DepartmentDTO department, Model model){
        departmentDAO.save(department);

        return "department/createSuccessPage";
    }

    @GetMapping("/update/updateDepartment/{id}")
    public String updateDepartment(Model model, @PathVariable String id){
        Optional<DepartmentDTO> departmentDTOOptional= departmentDAO.findByDept_No(id);
        DepartmentDTO departmentDTO = null;
        if(departmentDTOOptional.isPresent()){
            departmentDTO = departmentDTOOptional.get();
        }
        model.addAttribute("department",departmentDTO);
        return "department/updateDepartmentPage";
    }

    @PostMapping("/update/updateSuccess")
    public String updateDepartmentSuccess(@ModelAttribute("department")DepartmentDTO department, Model model){
        departmentDAO.save(department);

        return "department/updateSuccessPage";
    }

    @GetMapping("/admin/deleteDepartment")
    public String getDeleteDepartment(Model model, String id){
        if (departmentDAO.findByDept_No(id).isPresent()){
            model.addAttribute("department",departmentDAO.findByDept_No(id).orElse(null));
            departmentDAO.deleteById(id);
        }

        return "department/deleteSuccessPage";
    }
}
