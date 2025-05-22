package ru.xast.TestPlatform.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.xast.TestPlatform.models.Question;
import ru.xast.TestPlatform.services.QuestionService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping()
    public String index(Model model){
        try {
            model.addAttribute("question", questionService.findAll());
            return "questions/index";
        } catch (Exception e) {
            log.error("Error loading question index page", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {

        try {
            Question question = questionService.findOne(id);
            model.addAttribute("questions", question);
            return "questions/show";
        } catch (Exception e) {
            log.error("Error loading question with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/new")
    public String newQuestion(Model model) {
        try{
            model.addAttribute("question", new Question());
            return "questions/new";
        }catch (Exception e){
            log.error("Error loading question", e);
            return "redirect:/error/retry";
        }
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute("question") @Valid Question question,
            @RequestParam("optionContent") List<String> optionContent,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "questions/new";
            }
            questionService.saveQuestionWithOptions(question, optionContent);

            return "redirect:/question";
        } catch (Exception e) {
            log.error("Error creating question", e);
            return "redirect:/error/retry";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        try {
            questionService.delete(id);
            return "redirect:/questions";
        } catch (Exception e) {
            log.error("Error deleting question with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }
}
