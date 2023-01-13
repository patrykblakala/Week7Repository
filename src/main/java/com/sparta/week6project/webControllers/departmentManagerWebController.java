package com.sparta.week6project.webControllers;

import com.sparta.week6project.DAO.impl.DepartmentManagerDAO;
import com.sparta.week6project.DTO.DeptManagerDTO;
import com.sparta.week6project.entities.DeptManager;
import com.sparta.week6project.entities.DeptManagerId;
import com.sparta.week6project.repositories.DeptManagerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


    @GetMapping("/basic/all")
    public String getAllSalaries(Model model){
        List<DeptManager> departmentManagers = departmentManagerDAO.findAll();
        model.addAttribute("departmentManagers", departmentManagers);
        return "departmentManager/all";
    }

    @GetMapping("/basic/show")
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

    @GetMapping("/update/update")
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

    @GetMapping("/update/create")
    public String createSalary(Model model){
        DeptManagerDTO departmentManager = new DeptManagerDTO();
        model.addAttribute("departmentManager",departmentManager);
        return "departmentManager/create";
    }

    @PostMapping("/update/success")
    public String createDepartmentSuccess(@ModelAttribute("departmentManager")DeptManagerDTO departmentManager){

        deptManagerRepository.saveDeptManager(departmentManager.getEmpNo(), departmentManager.getDeptNo(), departmentManager.getFromDate(), departmentManager.getToDate());

        return "departmentManager/success";
    }

    @PostMapping("/update/updateSuccess")
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

    @PostMapping("/admin/delete")
    public String delete(@ModelAttribute("departmentManager") DeptManagerDTO departmentManager){

        DeptManagerId deptManagerId = new DeptManagerId();
        deptManagerId.setDeptNo(departmentManager.getDeptNo());
        deptManagerId.setEmpNo(departmentManager.getEmpNo());

        departmentManagerDAO.deleteById(deptManagerId);
        return "departmentManager/success";

    }

}
