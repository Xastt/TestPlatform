package ru.xast.TestPlatform.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.xast.TestPlatform.models.Options;
import ru.xast.TestPlatform.models.Question;
import ru.xast.TestPlatform.services.OptionsService;

@Slf4j
@Controller
@RequestMapping("/options")
public class OptionsController {

    private final OptionsService optionsService;

    @Autowired
    public OptionsController(OptionsService optionsService) {
        this.optionsService = optionsService;
    }

    @GetMapping("/new")
    public String newOption(@ModelAttribute("option") Options options) {
        return "questions/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("question") @Valid Question question, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "questions/new";
            }

            questionService.save(question);
            return "redirect:/questions";
        } catch (Exception e) {
            log.error("Error creating person", e);
            return "redirect:/error/retry";
        }
    }
}
