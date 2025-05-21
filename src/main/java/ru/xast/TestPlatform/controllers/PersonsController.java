package ru.xast.TestPlatform.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xast.TestPlatform.models.Persons;
import ru.xast.TestPlatform.models.Users;
import ru.xast.TestPlatform.services.PersonsService;
import ru.xast.TestPlatform.services.UsersService;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/persons")
public class PersonsController {

    private final PersonsService personsService;
    private final UsersService usersService;

    public PersonsController(PersonsService personsService, UsersService usersService) {
        this.personsService = personsService;
        this.usersService = usersService;
    }

    @GetMapping()
    public String index(Model model){
        try {
            model.addAttribute("person", personsService.findAll());
            return "persons/index";
        } catch (Exception e) {
            log.error("Error loading persInf index page", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {

        try {
            Users currentUser = usersService.getCurrentUser();
            Persons existingPersInf = personsService.findOne(id);

            if (!existingPersInf.getUser().getId().equals(currentUser.getId())) {
                return "redirect:/error/mismatchid";
            }

            model.addAttribute("person", existingPersInf);
            return "persons/show";
        } catch (Exception e) {
            log.error("Error loading persInf with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Persons person) {
        return "persons/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Persons person, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "persons/new";
            }

            Users user = usersService.getCurrentUser();
            person.setUser(user);

            personsService.save(person);
            return "redirect:/persons";
        } catch (Exception e) {
            log.error("Error creating person", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {
        try {
            Persons existingPerson = personsService.findOne(id);
            model.addAttribute("person", existingPerson);
            return "persons/edit";
        } catch (Exception e) {
            log.error("Error loading edit page for person with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute("person") @Valid Persons person, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "persons/edit";
            }

            Users user = usersService.getCurrentUser();
            person.setUser(user);

            personsService.update(id, person);
            return "redirect:/persons";
        } catch (Exception e) {
            log.error("Error updating person with id: {}", id, e);
            return "redirect:/error/retry";
        }

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        try {
            Users currentUser = usersService.getCurrentUser();
            Persons existingPerson = personsService.findOne(id);

            if (!existingPerson.getUser().getId().equals(currentUser.getId())) {
                return "redirect:/error/mismatchid";
            }

            personsService.delete(id);
            return "redirect:/persons";
        } catch (Exception e) {
            log.error("Error deleting person with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

}
