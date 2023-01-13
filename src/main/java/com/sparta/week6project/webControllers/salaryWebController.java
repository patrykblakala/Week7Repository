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

    @GetMapping("basic/all")
    public String getAllSalaries(Model model){
        Page<Salary> salaries = salaryDAO.findAllSalaries();
        model.addAttribute("salaries", salaries);
        return "salary/displayAllSalaries";
    }

    @GetMapping("/basic/salary")
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

    @GetMapping("/update/createSalary")
    public String createSalary(Model model){
        SalaryDTO salary = new SalaryDTO();
        model.addAttribute("salary",salary);
        return "salary/createSalaryPage";
    }

    @PostMapping("/update/createSuccess")
    public String createDepartmentSuccess(@ModelAttribute("salary")SalaryDTO salary){
        salary.setEmpNo(salary.getId().getEmpNo());
        salaryDAO.save(salary);
        return "salary/createSuccessPage";
    }

    @GetMapping("/update/updateSalary")
    public String updateSalary(Integer empNo, LocalDate fromDate, Model model){
        SalaryId salaryId = new SalaryId();
        salaryId.setEmpNo(empNo);
        salaryId.setFromDate(fromDate);
        Optional<SalaryDTO> salaryDTOOptional= salaryDAO.findById(salaryId);
        SalaryDTO salary = null;
        if(salaryDTOOptional.isPresent()){
            salary = salaryDTOOptional.get();
            model.addAttribute("salary",salary);
        } else {
            model.addAttribute("salary", salary);
        }
        return "salary/updateSalaryPage";
    }

    @PostMapping("/update/updateSalarySuccess")
    public String updateSalarySuccess(@ModelAttribute("salary")SalaryDTO salary){
        System.out.println(salary);
        salary.setEmpNo(salary.getId().getEmpNo());
        salaryDAO.save(salary);

        return "salary/updateSalarySuccessPage";
    }

    @GetMapping("/admin/deleteSalary")
    public String getDeleteSalary(Integer empNo, LocalDate fromDate, Model model){
        SalaryId salaryId = new SalaryId();
        salaryId.setEmpNo(empNo);
        salaryId.setFromDate(fromDate);
        if (salaryDAO.findById(salaryId).isPresent()){
            model.addAttribute("salary",salaryId);
            salaryDAO.deleteById(salaryId);
        } else {
            model.addAttribute("salary", null);
        }

        return "salary/deleteSalaryPage";
    }

    @GetMapping("/basic/salaryAverage")
    public String getSalaryAverageByDepartmentNumberAndDate(String departmentNumber, LocalDate givenDate, Model model){
        model.addAttribute("average",salaryDAO.averageSalaryForDepartmentAndDate(departmentNumber,givenDate));
        model.addAttribute("department",departmentNumber);
        model.addAttribute("date",givenDate);
        return "salary/displaySalaryAveragePage";
    }

    @GetMapping("/basic/salaryRange")
    public String getSalaryRangeByTitleAndYear(String jobTitle, int givenYear, Model model){
        String salaryRange = salaryDAO.getSalaryRangeByJobTitleAndYear(jobTitle,givenYear);
        model.addAttribute("salaryRange","£"+salaryRange.split(" =")[1]);
        model.addAttribute("maxSalary","£"+salaryRange.split(" -")[0]);
        model.addAttribute("minSalary","£"+salaryRange.split("- ")[1].split(" =")[0]);
        model.addAttribute("year",givenYear);
        model.addAttribute("title",jobTitle);
        return "salary/displaySalaryRangePage";
    }

    @GetMapping("/basic/salaryPayGap")
    public String getGenderPayGapByDepartmentNumberAndYear(String departmentNumber, LocalDate givenYear, Model model){
        System.out.println("starting");
        String result = salaryDAO.getGenderPayGap(departmentNumber,givenYear).split("!")[0];
        System.out.println(result);
        System.out.println(givenYear.toString());
        model.addAttribute("year",givenYear.toString());
        model.addAttribute("payGap",result);

        return "salary/displayGenderPayGapPage";
    }

}
