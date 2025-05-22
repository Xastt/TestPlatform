package ru.xast.TestPlatform.services;

import org.springframework.stereotype.Service;
import ru.xast.TestPlatform.exceptions.ResourceNotFoundException;
import ru.xast.TestPlatform.models.*;
import ru.xast.TestPlatform.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final TestResultRepository testResultRepository;
    private final QuestionRepository questionRepository;
    private final OptionsRepository optionsRepository;

    public TestService(TestRepository testRepository, TestResultRepository testResultRepository, QuestionRepository questionRepository, OptionsRepository optionsRepository) {
        this.testRepository = testRepository;
        this.testResultRepository = testResultRepository;
        this.questionRepository = questionRepository;
        this.optionsRepository = optionsRepository;
    }

    public Test createTest(Test test) {
        test.setCreatedAt(LocalDateTime.now());
        return testRepository.save(test);
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Optional<Test> getTestById(UUID id) {
        return testRepository.findById(id);
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Options createOption(Options option) {
        return optionsRepository.save(option);
    }

    public Object getTestForTaking(UUID testId) throws ResourceNotFoundException {
        return testRepository.findWithQuestionsAndOptionsById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found"));
    }

    public TestResult submitTest(UUID testId, Map<UUID, UUID> answers, Persons person) throws ResourceNotFoundException {
        Test test = (Test) getTestForTaking(testId);

        int score = calculateScore(test, answers);
        int maxScore = calculateMaxScore(test);

        TestResult result = new TestResult();
        result.setPerson(person);
        result.setTest(test);
        result.setScore(score);
        result.setMaxPossibleScore(maxScore);
        result.setCompletedAt(LocalDateTime.now());

        return testResultRepository.save(result);
    }

    private int calculateScore(Test test, Map<UUID, UUID> answers) {
        return test.getQuestions().stream()
                .mapToInt(question -> {
                    UUID selectedOptionId = answers.get(question.getId());
                    if (selectedOptionId != null) {
                        Options selectedOption = question.getQuestionOptions().stream()
                                .filter(opt -> opt.getId().equals(selectedOptionId))
                                .findFirst()
                                .orElse(null);
                        return selectedOption != null && selectedOption.getIsCorrect() ? 1 : 0;
                    }
                    return 0;
                })
                .sum();
    }

    private int calculateMaxScore(Test test) {
        return test.getQuestions().size();
    }
}
