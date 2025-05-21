package ru.xast.TestPlatform.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xast.TestPlatform.models.Options;
import ru.xast.TestPlatform.models.Question;
import ru.xast.TestPlatform.services.QuestionService;

import java.util.ArrayList;
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
            List<Options> options = new ArrayList<>();
            for(int i = 0; i<optionContent.size(); i++){
                Options option = new Options();
                option.setOption_content(optionContent.get(i));
                options.add(option);
            }
            question.setQuestionOptions(options);
            questionService.save(question);

            return "redirect:/question";
        } catch (Exception e) {
            log.error("Error creating question", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {
        try {
            Question question = questionService.findOne(id);
            model.addAttribute("question", question);
            return "questions/edit";
        } catch (Exception e) {
            log.error("Error loading edit page for question with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute("person") @Valid Question question, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "questions/edit";
            }
            questionService.update(id, question);
            return "redirect:/questions";
        } catch (Exception e) {
            log.error("Error updating person with id: {}", id, e);
            return "redirect:/error/retry";
        }

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        try {
            questionService.delete(id);
            return "redirect:/questions";
        } catch (Exception e) {
            log.error("Error deleting person with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }
}
