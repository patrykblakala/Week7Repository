package com.sparta.week6project.webControllers;

import com.sparta.week6project.DAO.impl.DepartmentManagerDAO;
import com.sparta.week6project.DTO.DeptManagerDTO;
import com.sparta.week6project.DTO.SalaryDTO;
import com.sparta.week6project.Week6ProjectApplication;
import com.sparta.week6project.entities.*;
import com.sparta.week6project.repositories.DeptManagerRepository;
import com.sparta.week6project.repositories.SalaryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequestMapping("/web/departmentManager")

public class departmentManagerWebController {
    @Autowired
    DepartmentManagerDAO departmentManagerDAO;

    @Autowired
    DeptManagerRepository deptManagerRepository;


    @GetMapping("/all")
    public String getAllSalaries(Model model){
        List<DeptManager> departmentManagers = departmentManagerDAO.findAll();
        model.addAttribute("departmentManagers", departmentManagers);
        return "departmentManager/all";
    }

    @GetMapping("/show")
    public String getSalaryById(Integer empNo, String deptNo, Model model){

        DeptManagerId deptManagerId = new DeptManagerId();
        deptManagerId.setEmpNo(empNo);
        deptManagerId.setDeptNo(deptNo);
        
        Optional<DeptManagerDTO> deptManagerDTOOptional = departmentManagerDAO.findById(deptManagerId);
        DeptManagerDTO departmentManager = null;
        if(deptManagerDTOOptional.isPresent()){
            departmentManager = deptManagerDTOOptional.get();
        }
        model.addAttribute("departmentManager",departmentManager);
        return "departmentManager/display";
    }

    @GetMapping("/update")
    public String updateSalaryById(Integer empNo, String deptNo, Model model){

        DeptManagerId deptManagerId = new DeptManagerId();
        deptManagerId.setEmpNo(empNo);
        deptManagerId.setDeptNo(deptNo);

        Optional<DeptManagerDTO> deptManagerDTOOptional = departmentManagerDAO.findById(deptManagerId);
        DeptManagerDTO departmentManager = null;
        if(deptManagerDTOOptional.isPresent()){
            departmentManager = deptManagerDTOOptional.get();
        }
        model.addAttribute("departmentManager",departmentManager);
        return "departmentManager/update";
    }

    @GetMapping("/create")
    public String createSalary(Model model){
        DeptManagerDTO departmentManager = new DeptManagerDTO();
        model.addAttribute("departmentManager",departmentManager);
        return "departmentManager/create";
    }

    @PostMapping("/success")
    public String createDepartmentSuccess(@ModelAttribute("departmentManager")DeptManagerDTO departmentManager){

        deptManagerRepository.saveDeptManager(departmentManager.getEmpNo(), departmentManager.getDeptNo(), departmentManager.getFromDate(), departmentManager.getToDate());

        return "departmentManager/success";
    }

    @PostMapping("/updateSuccess")
    public String updateDepartmentSuccess(@ModelAttribute("departmentManager") DeptManagerDTO departmentManager){

        DeptManagerId deptManagerId = new DeptManagerId();
        deptManagerId.setDeptNo(departmentManager.getDeptNo());
        deptManagerId.setEmpNo(departmentManager.getEmpNo());
        DeptManagerDTO original = null;
        Optional<DeptManagerDTO> originalOptional = departmentManagerDAO.findById(deptManagerId);
        try{
            original = originalOptional.get();
            if (departmentManager.getFromDate() != null){
                original.setFromDate(departmentManager.getFromDate());
            }
            if (departmentManager.getToDate() != null) {
                original.setToDate(departmentManager.getToDate());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        departmentManagerDAO.update(original);
        return "departmentManager/success";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("departmentManager") DeptManagerDTO departmentManager){

        DeptManagerId deptManagerId = new DeptManagerId();
        deptManagerId.setDeptNo(departmentManager.getDeptNo());
        deptManagerId.setEmpNo(departmentManager.getEmpNo());

        departmentManagerDAO.deleteById(deptManagerId);
        return "departmentManager/success";

    }

}
