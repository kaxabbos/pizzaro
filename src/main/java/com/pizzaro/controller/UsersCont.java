package com.pizzaro.controller;

import com.pizzaro.controller.main.Attributes;
import com.pizzaro.model.Stats;
import com.pizzaro.model.Users;
import com.pizzaro.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersCont extends Attributes {
    @GetMapping
    public String users(Model model) {
        AddAttributesUsers(model);
        return "users";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @RequestParam Role role) {
        Users user = usersRepo.getReferenceById(id);
        if (user.getRole() != Role.MANAGER && role == Role.MANAGER) {
            user.setStats(new Stats());
        }
        if (user.getRole() == Role.MANAGER && role != Role.MANAGER) {
            Long tempId = user.getStats().getId();
            user.setStats(null);
            statsRepo.deleteById(tempId);
        }
        user.setRole(role);
        usersRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(Model model, @PathVariable Long id) {
        Users user = usersRepo.getReferenceById(id);
        if (user == getUser()) {
            AddAttributesUsers(model);
            model.addAttribute("message", "Вы не можете удалить свой профиль!");
            return "users";
        }
        usersRepo.deleteById(id);
        return "redirect:/users";
    }
}
