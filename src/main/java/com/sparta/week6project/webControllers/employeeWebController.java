package com.sparta.week6project.webControllers;

import com.sparta.week6project.DAO.impl.DepartmentDAO;
import com.sparta.week6project.DAO.impl.EmployeeDAO;
import com.sparta.week6project.DAO.impl.SalaryDAO;
import com.sparta.week6project.DAO.impl.TitleDAO;
import com.sparta.week6project.DTO.DeptEmpDTO;
import com.sparta.week6project.DTO.EmployeeDTO;
import com.sparta.week6project.DTO.FullEmpInfoDTO;
import com.sparta.week6project.DTO.SalaryDTO;
import com.sparta.week6project.entities.*;
import com.sparta.week6project.repositories.DepartmentRepository;
import com.sparta.week6project.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/web/employees")
public class employeeWebController {
    @Autowired
    EmployeeDAO employeeDAO;
    @Autowired
    DepartmentDAO departmentDAO;

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    TitleDAO titleDAO;
    @Autowired
    TitleRepository titleRepository;
    @Autowired
    SalaryDAO salaryDAO;

    @GetMapping("/basic/all/{pageNum}")
    public String getAllEmployees(Model model, @PathVariable int pageNum) {
        Page<Employee> employees = employeeDAO.findAllEmployees(pageNum);
        model.addAttribute("employees", employees);
        model.addAttribute("pageNum", pageNum);
        return "employee/displayAllEmployees";
    }

    @GetMapping("/basic/{id}")
    public String getEmployeeById(Model model, @PathVariable int id) {
        EmployeeDTO employeeDTO = employeeDAO.findById(id).orElse(null);
        model.addAttribute("employee", employeeDTO);
        return "employee/displayEmployee";
    }

    @GetMapping("/basic")
    public String getEmployeeByLastName(Model model, @RequestParam String lastName) {
        List<Employee> employeeList = employeeDAO.findEmployeeByLastName(lastName);
        model.addAttribute("employees", employeeList);
        return "employee/displayAllEmployeesLastName";
    }

    @GetMapping("/update/createEmployee")
    public String createEmployee(Model model) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        model.addAttribute("employee", employeeDTO);
        return "employee/createEmployeePage";
    }

    @PostMapping("/update/createSuccess")
    public String createEmployeeSuccess(@ModelAttribute("employee") EmployeeDTO employeeDTO, Model model) {
        employeeDAO.save(employeeDTO);
        return "employee/createSuccessPage";
    }

    @GetMapping("/update/createEmployeeFull")
    public String createEmployeeFull(Model model) {
        FullEmpInfoDTO fullEmpInfoDTO = new FullEmpInfoDTO();
        List<Department> departments = departmentRepository.findAll();
        List<String> titles = titleRepository.findAllDistinctTitle();
        model.addAttribute("fullEmployee", fullEmpInfoDTO);
        model.addAttribute("departments",departments);
        model.addAttribute("titles",titles);
        return "employee/createEmployeeFullPage";
    }

    @PostMapping("/update/createFullSuccess")
    public String createEmployeeFullSuccess(@ModelAttribute("employeeFull") FullEmpInfoDTO fullEmpInfoDTO, Model model) {
        //Set department info
        DeptEmpId deptEmpId = new DeptEmpId();
        deptEmpId.setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        deptEmpId.setDeptNo(fullEmpInfoDTO.getDepartment().getDeptNo());
        fullEmpInfoDTO.getDepartment().setId(deptEmpId);
        fullEmpInfoDTO.getDepartment().setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        fullEmpInfoDTO.getDepartment().setToDate(LocalDate.of(9999,1,1));
        fullEmpInfoDTO.getDepartment().setFromDate(fullEmpInfoDTO.getEmployee().getHireDate());

        //Set salary info
        SalaryId salaryId = new SalaryId();
        salaryId.setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        salaryId.setFromDate(fullEmpInfoDTO.getEmployee().getHireDate());
        fullEmpInfoDTO.getSalary().setId(salaryId);
        fullEmpInfoDTO.getSalary().setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        fullEmpInfoDTO.getSalary().setToDate(LocalDate.of(9999,1,1));

        //Set title info
        fullEmpInfoDTO.getTitle().getId().setFromDate(fullEmpInfoDTO.getEmployee().getHireDate());
        fullEmpInfoDTO.getTitle().getId().setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        fullEmpInfoDTO.getTitle().setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        fullEmpInfoDTO.getTitle().setToDate(LocalDate.of(9999,1,1));

        model.addAttribute("populatedEmp", fullEmpInfoDTO);
        model.addAttribute("departmentName",departmentRepository.findDeptNameByDeptNo(fullEmpInfoDTO.getDepartment().getDeptNo()));
        return "employee/createFullConfirmPage";
    }

    @PostMapping("/update/createEmployee/createFullConfirm")
    public String createEmployeeConfirm(@ModelAttribute("populatedEmp") FullEmpInfoDTO fullEmpInfoDTO) {

        return "employee/createSuccessPage";
    }

    @GetMapping("/update/updateEmployee/{id}")
    public String updateEmployee(Model model, @PathVariable int id) {
        EmployeeDTO employeeDTO = employeeDAO.findById(id).orElse(null);
        model.addAttribute("employee", employeeDTO);
        return "employee/updateEmployeePage";
    }

    @PostMapping("/update/updateEmployee/updateSuccess")
    public String updateEmployeeSuccess(@ModelAttribute("employee") EmployeeDTO employeeDTO, Model model) {
        employeeDAO.save(employeeDTO);
        return "employee/updateSuccessPage";
    }

    @GetMapping("/admin/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id, Model model) {
        EmployeeDTO employeeDTO = employeeDAO.findById(id).orElse(null);
        if (employeeDTO != null) {
            employeeDAO.deleteById(id);
        }
        model.addAttribute("employee", employeeDTO);
        return "employee/deleteSuccessPage";
    }
}
