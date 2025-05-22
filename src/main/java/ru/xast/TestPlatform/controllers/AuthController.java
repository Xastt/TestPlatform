package ru.xast.TestPlatform.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.xast.TestPlatform.models.Users;
import ru.xast.TestPlatform.services.PersonsService;
import ru.xast.TestPlatform.services.RegistrationService;
import ru.xast.TestPlatform.util.UsersValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final UsersValidator usersValidator;
    private final PersonsService personsService;

    @Autowired
    public AuthController(RegistrationService registrationService, UsersValidator usersValidator, PersonsService personsService) {
        this.registrationService = registrationService;
        this.usersValidator = usersValidator;
        this.personsService = personsService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("users") Users users) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("users") @Valid Users users, BindingResult bindingResult) {

        usersValidator.validate(users, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        registrationService.register(users);

        return "redirect:/auth/login";
    }
}
