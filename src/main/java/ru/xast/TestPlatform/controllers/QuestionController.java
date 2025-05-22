package ru.xast.TestPlatform.controllers;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.xast.TestPlatform.models.Options;
import ru.xast.TestPlatform.models.Question;
import ru.xast.TestPlatform.models.Test;
import ru.xast.TestPlatform.services.QuestionService;
import ru.xast.TestPlatform.services.TestService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/question")
public class QuestionController {

    /*private final QuestionService questionService;
    private final TestService testService;

    @Autowired
    public QuestionController(QuestionService questionService, TestService testService) {
        this.questionService = questionService;
        this.testService = testService;
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

    /*@PostMapping("/create")
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
    }*/

    /*@PostMapping("/create")
    public String createTest(@ModelAttribute Test test,
                             @RequestParam("questions[0].content") List<String> questionContents,
                             @RequestParam("questions[0].options[0].content") List<List<String>> optionContents) {

        Test newTest = new Test();
        newTest.setTitle(test.getTitle());
        newTest.setCreatedAt(LocalDateTime.now());

        for (int i = 0; i < questionContents.size(); i++) {
            Question question = new Question();
            question.setContent(questionContents.get(i));

            for (int j = 0; j < optionContents.get(i).size(); j++) {
                Options option = new Options();
                option.setOption_content(optionContents.get(i).get(j));
                question.addOption(option);
            }

            newTest.addQuestion(question);
        }

        testService.createTest(newTest);
        return "redirect:/tests";
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

    @GetMapping("/testForm")
    public String showTestForm(Model model) {
        model.addAttribute("testForm", new TestForm());
        return "questions/testForm";
    }

    @PostMapping("/submitTest")
    public String submitTest(@ModelAttribute TestForm testForm) {

        for (QuestionForm questionForm : testForm.getQuestions()) {
            Question question = new Question();
            question.setContent(questionForm.getContent());

            List<Options> options = new ArrayList<>();
            for (String optionContent : questionForm.getOptions()) {
                Options option = new Options(optionContent);
                option.setQuest(question);
                options.add(option);
            }

            question.setQuestionOptions(options);
            questionService.save(question);
        }
        return "redirect:/question";
    }

    @Getter
    @Setter
    public static class TestForm {
        private List<QuestionForm> questions = new ArrayList<>();

    }

    @Getter
    @Setter
    public static class QuestionForm {
        private String content;
        private List<String> options = new ArrayList<>();

    }*/
}
