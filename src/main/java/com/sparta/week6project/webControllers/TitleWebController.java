package com.sparta.week6project.webControllers;

import com.sparta.week6project.DAO.impl.TitleDAO;
import com.sparta.week6project.DTO.DepartmentDTO;
import com.sparta.week6project.DTO.SalaryDTO;
import com.sparta.week6project.DTO.TitleDTO;
import com.sparta.week6project.entities.Employee;
import com.sparta.week6project.entities.Title;
import com.sparta.week6project.entities.TitleId;
import com.sparta.week6project.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web/titles")
public class TitleWebController {

    @Autowired
    private TitleDAO titleDAO;

    @Autowired
    private TitleRepository titleRepo;

    @GetMapping("/all/{pageNum}")
    public String getAllTitles(Model model,@PathVariable int pageNum){
        Page<Title> titles = titleDAO.findAllTitles(pageNum);
        model.addAttribute("titles", titles);
        model.addAttribute("pageNum", pageNum);
        return "title/displayAllTitles";
    }

    @GetMapping("/title")
    public String getTitleById(Integer empNo, String title,LocalDate fromDate, Model model) {

        TitleId titleId = new TitleId();
        titleId.setTitle(title);
        titleId.setEmpNo(empNo);
        titleId.setFromDate(fromDate);

        Optional<TitleDTO> titleOptional = titleDAO.findById(titleId);
        TitleDTO result = null;
        if (titleOptional.isPresent()) {
            result = titleOptional.get();
        }
        model.addAttribute("title", result);
        return "/title/displayTitle";

    }
    @GetMapping("/createTitle")
    public String createTitle(Model model){
        TitleDTO title = new TitleDTO();
        model.addAttribute("title",title);
        return "title/createTitlePage";
    }
    @PostMapping("/createSuccess")
    public String createTitleSuccess(@ModelAttribute("title")TitleDTO title){
        title.setEmpNo(title.getId().getEmpNo());
        titleDAO.save(title);
        return "title/createSuccessPage";
    }

    @GetMapping("/updateTitle")
    public String updateTitle(Integer empNo, String title,LocalDate fromDate, Model model){

        TitleId titleId = new TitleId();
        titleId.setTitle(title);
        titleId.setEmpNo(empNo);
        titleId.setFromDate(fromDate);

        Optional<TitleDTO> titleOptional = titleDAO.findById(titleId);
        TitleDTO result = null;
        if (titleOptional.isPresent()) {
            result = titleOptional.get();
        }
        model.addAttribute("title", result);
        return "title/updateTitlePage";
    }
    @PostMapping("/updateSuccess")
    public String updateTitleSuccess(@ModelAttribute("title") TitleDTO title, Model model){
        titleDAO.save(title);

        return "title/updateSuccessPage";
    }
    @GetMapping("/deleteTitle")
    public String getDeleteTitle(Integer empNo, String title,LocalDate fromDate, Model model){
        TitleId titleId = new TitleId();
        titleId.setTitle(title);
        titleId.setEmpNo(empNo);
        titleId.setFromDate(fromDate);
        Optional<TitleDTO> titleDTOOptional = titleDAO.findById(titleId);
        TitleDTO result = null;
        if(titleDTOOptional.isPresent()) {
            result = titleDTOOptional.get();
        }
            model.addAttribute("empNo", result.getEmpNo());
            titleDAO.deleteById(titleId);



        return "title/deleteSuccessPage";

    }

}
