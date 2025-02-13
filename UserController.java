package org.example.Controllers;


import org.example.Models.User;
import org.example.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user_list";
    }


    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createUser";
        }
        userRepository.save(user);
        return "redirect:/users";
    }




    @GetMapping("/edit/{userId}")
    public String editUser(@PathVariable Long userId, Model model) {
        User userToEdit = userRepository.findById(userId).orElse(null);
        if (userToEdit != null) {
            model.addAttribute("user", userToEdit);
            return "user_edit";
        }

        return "redirect:/users";
    }


    @PostMapping("/edit/{userId}")
    public String saveEditedUser(@PathVariable Long userId,
                                 @Valid @ModelAttribute("user") User user,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "user_edit";
        }
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
        }
        return "redirect:/users";
    }


    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userRepository.deleteById(userId);
        return "redirect:/users";
    }
}

