package com.sparta.week6project.webControllers;

import com.sparta.week6project.DAO.impl.UserDAO;
import com.sparta.week6project.DTO.DepartmentDTO;
import com.sparta.week6project.DTO.UserDTO;
import com.sparta.week6project.entities.User;
import com.sparta.week6project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web/users")
public class userWebController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getUserHomePage(Model model) {
        return "user/userHome";
    }

    @GetMapping("/basic/")
    public String getAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/displayAllUsers";
    }
    @GetMapping("/basic/{id}")
    public String getUserById(@PathVariable Integer id, Model model){
        Optional<UserDTO> userOptional = userDAO.findById(id);
        UserDTO user = null;
        if(userOptional.isPresent()){
            user = userOptional.get();
        }
        model.addAttribute("user",user);
        return "user/displayUser";
    }
    @GetMapping("/update/create")
    public String createUser(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "user/createUserPage";
    }
    @PostMapping("/update/createSuccess")
    public String createUserSuccess(@ModelAttribute("user") UserDTO user, Model model) {
        user.setRole("BASIC");
        userDAO.save(user);
        return "user/createSuccessPage";
    }

    @GetMapping("/update/update")
    public String updateUser(Model model, Integer id) {
        Optional<UserDTO> userDTOOptional = userDAO.findById(id);
        UserDTO userDTO = null;
        if (userDTOOptional.isPresent()) {
            userDTO = userDTOOptional.get();
        }
        if (userDTO != null)
        model.addAttribute("user", userDTO);
        return "user/updatePage";
    }

    @PostMapping("/update/updateSuccess")
    public String updateSuccess(@ModelAttribute("user") UserDTO user, Model model) {
        userDAO.save(user);
        return "user/updateSuccessPage";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(Model model, UserDTO user) {
        model.addAttribute("user", user);
        return "user/deletePage";
    }
    @PostMapping("/admin/deleteSuccess")
    public String deleteSuccess(@ModelAttribute("user") UserDTO user, Model model) {
        Optional<UserDTO> userDTOOptional = userDAO.findById(user.getId());
        if (userDTOOptional.isPresent()) {
            userDAO.deleteById(user.getId());
        }
        return "user/deleteSuccessPage";
    }
}
