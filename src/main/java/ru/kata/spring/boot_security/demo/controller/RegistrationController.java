package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.Utilizer;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.validation.Valid;

//Данный контроллер был перенесен из примера (после тестирования удалить за ненадобностью)

@Controller
public class RegistrationController {
    String registration = "/registration";
    UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new Utilizer());
        return registration;
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid Utilizer utilizerForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return registration;
        }
        if (!utilizerForm.getPassword().equals(utilizerForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароль введен не верно");
        }
        if (!userService.saveUser(utilizerForm)) {
            model.addAttribute("usernameError", "Пользователь с данным именем уже существует");
            return registration;
        }
        return "redirect:/";
    }
}
