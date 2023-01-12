package com.sparta.week6project.webControllers;

import com.sparta.week6project.DAO.impl.UserDAO;
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

    @GetMapping("/")
    public String getAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/displayAllUsers";
    }

    @GetMapping("/{id}")
    public String getUser(Model model, @PathVariable Integer id) {
        Optional<UserDTO> optionalUser = userDAO.findById(id);
        UserDTO user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        model.addAttribute("user", user);
        return "user/displayUser";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "user/createUserPage";
    }

    @PostMapping("/createSuccess")
    public String createUserSuccess(@ModelAttribute("user") UserDTO user, Model model) {
        user.setRole("BASIC");
        userDAO.save(user);
        return "user/createSuccessPage";
    }

    @GetMapping("/update")
    public String updateUser(Model model, Integer id) {
        Optional<UserDTO> userDTOOptional = userDAO.findById(id);
        UserDTO userDTO = null;
        if (userDTOOptional.isPresent()) {
            userDTO = userDTOOptional.get();
        }
        model.addAttribute("user", userDTO);
        return "user/updatePage";
    }

    @PostMapping("/updateSuccess")
    public String updateSuccess(@ModelAttribute("user") UserDTO user, Model model) {
        userDAO.save(user);
        return "user/updateSuccessPage";
    }

    @GetMapping("/delete")
    public String deleteUser(Model model, UserDTO user) {
        model.addAttribute("user", user);
        return "user/deletePage";
    }

    @PostMapping("/deleteSuccess")
    public String deleteSuccess(@ModelAttribute("user") UserDTO user, Model model) {
        Optional<UserDTO> userDTOOptional = userDAO.findById(user.getId());
        if (userDTOOptional.isPresent()) {
            userDAO.deleteById(user.getId());
        }
        return "user/deleteSuccessPage";
    }


}
