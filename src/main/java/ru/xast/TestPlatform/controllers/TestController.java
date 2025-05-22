package ru.xast.TestPlatform.controllers;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xast.TestPlatform.models.Persons;
import ru.xast.TestPlatform.models.Test;
import ru.xast.TestPlatform.models.TestResult;
import ru.xast.TestPlatform.models.Users;
import ru.xast.TestPlatform.repositories.PersonsRepository;
import ru.xast.TestPlatform.repositories.TestRepository;
import ru.xast.TestPlatform.repositories.TestResultRepository;
import ru.xast.TestPlatform.repositories.UsersRepository;
import ru.xast.TestPlatform.exceptions.ResourceNotFoundException;
import ru.xast.TestPlatform.services.TestService;
import ru.xast.TestPlatform.services.UsersService;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tests")
public class TestController {

    private final TestService testService;
    private final TestResultRepository testResultRepository;
    private final PersonsRepository personsRepository;
    private final UsersRepository usersRepository;
    private final TestRepository testRepository;

    @Autowired
    public TestController(TestService testService, UsersService usersService, TestResultRepository testResultRepository, PersonsRepository personsRepository, UsersRepository usersRepository, TestRepository testRepository) {
        this.testService = testService;
        this.testResultRepository = testResultRepository;
        this.personsRepository = personsRepository;
        this.usersRepository = usersRepository;
        this.testRepository = testRepository;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("test", new Test());
        return "tests/create";
    }

    @PostMapping("/create")
    public String createTest(@ModelAttribute Test test) {
        testService.createTest(test);
        return "redirect:/tests";
    }

    @GetMapping
    public String listTests(Model model) {
        model.addAttribute("tests", testService.getAllTests());
        return "tests/list";
    }

    @GetMapping("/{id}")
    public String viewTest(@PathVariable UUID id, Model model) {
        testService.getTestById(id).ifPresent(test -> model.addAttribute("test", test));
        return "tests/view";
    }

    @GetMapping("/{id}/take")
    public String takeTest(@PathVariable UUID id, Model model) throws ResourceNotFoundException {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found"));

        Hibernate.initialize(test.getQuestions());
        test.getQuestions().forEach(question -> Hibernate.initialize(question.getQuestionOptions()));


        model.addAttribute("test", test);
        return "tests/take";
    }

    @PostMapping("/{id}/submit")
    public String submitTest(
            @PathVariable UUID id,
            @RequestParam Map<String, String> allParams,
            Principal principal) throws ResourceNotFoundException {

        Users user = usersRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Persons person = personsRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        Map<UUID, UUID> answers = allParams.entrySet().stream()
                .filter(e -> e.getKey().startsWith("question_"))
                .collect(Collectors.toMap(
                        e -> UUID.fromString(e.getKey().replace("question_", "")),
                        e -> UUID.fromString(e.getValue())
                ));

        testService.submitTest(id, answers, person);

        return "redirect:/tests/" + id + "/result";
    }

    @GetMapping("/{id}/result")
    public String viewResult(@PathVariable UUID id, Principal principal, Model model) throws ResourceNotFoundException {
        Users user = usersRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Persons person = personsRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        TestResult result = (TestResult) testResultRepository.findByPersonIdAndTestId(person.getId(), id)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found"));

        model.addAttribute("result", result);
        return "tests/result";
    }
}
