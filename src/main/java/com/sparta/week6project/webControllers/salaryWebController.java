package com.sparta.week6project.webControllers;

import com.sparta.week6project.DAO.impl.DepartmentDAO;
import com.sparta.week6project.DAO.impl.SalaryDAO;
import com.sparta.week6project.DTO.DepartmentDTO;
import com.sparta.week6project.DTO.SalaryDTO;
import com.sparta.week6project.entities.Department;
import com.sparta.week6project.entities.Salary;
import com.sparta.week6project.entities.SalaryId;
import com.sparta.week6project.mappers.DepartmentMapper;
import com.sparta.week6project.repositories.DepartmentRepository;
import com.sparta.week6project.repositories.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web/salaries")
public class salaryWebController {
    @Autowired
    SalaryDAO salaryDAO;

    @Autowired
    SalaryRepository salaryRepository;

    @GetMapping("/all")
    public String getAllSalaries(Model model){
        Page<Salary> salaries = salaryDAO.findAllSalaries();
        model.addAttribute("salaries", salaries);
        return "salary/displayAllSalaries";
    }

    @GetMapping("/salary")
    public String getSalaryById(Integer empNo, LocalDate fromDate, Model model){
        SalaryId salaryId = new SalaryId();
        salaryId.setEmpNo(empNo);
        salaryId.setFromDate(fromDate);
        Optional<SalaryDTO> salaryDTOOptional = salaryDAO.findById(salaryId);
        SalaryDTO salary = null;
        if(salaryDTOOptional.isPresent()){
            salary = salaryDTOOptional.get();
        }
        model.addAttribute("salary",salary);
        return "salary/displaySalary";
    }

    @GetMapping("/createSalary")
    public String createSalary(Model model){
        SalaryDTO salary = new SalaryDTO();
        model.addAttribute("salary",salary);
        return "salary/createSalaryPage";
    }

    @PostMapping("/createSuccess")
    public String createDepartmentSuccess(@ModelAttribute("salary")SalaryDTO salary){
        salary.setEmpNo(salary.getId().getEmpNo());
        salaryDAO.save(salary);
        return "salary/createSuccessPage";
    }

//    @GetMapping("/updateDepartment")
//    public String updateDepartment(Model model, String id){
//        Optional<DepartmentDTO> departmentDTOOptional= departmentDAO.findByDept_No(id);
//        DepartmentDTO departmentDTO = null;
//        if(departmentDTOOptional.isPresent()){
//            departmentDTO = departmentDTOOptional.get();
//        }
//        model.addAttribute("department",departmentDTO);
//        return "department/updateDepartmentPage";
//    }
//
//    @PostMapping("/updateSuccess")
//    public String updateDepartmentSuccess(@ModelAttribute("department")DepartmentDTO department, Model model){
//        departmentDAO.save(department);
//
//        return "department/updateSuccessPage";
//    }
//
//    @GetMapping("/deleteDepartment")
//    public String getDeleteActor(Model model, String id){
//        if (departmentDAO.findByDept_No(id).isPresent()){
//            model.addAttribute("department",departmentDAO.findByDept_No(id).orElse(null));
//            departmentDAO.deleteById(id);
//        }
//
//        return "department/deleteSuccessPage";
//    }

}
