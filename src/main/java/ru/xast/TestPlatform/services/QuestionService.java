package ru.xast.TestPlatform.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.TestPlatform.models.Options;
import ru.xast.TestPlatform.models.Question;
import ru.xast.TestPlatform.repositories.OptionsRepository;
import ru.xast.TestPlatform.repositories.QuestionRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class QuestionService extends CRUDOperationService<Question, UUID> {

    private final QuestionRepository questionRepository;
    private final OptionsRepository optionsRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, OptionsRepository optionsRepository) {
        this.questionRepository = questionRepository;
        this.optionsRepository = optionsRepository;
    }

    @Override
    protected JpaRepository<Question, UUID> getRepository() {
        return questionRepository;
    }

    @Override
    protected UUID getId(Question entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Question entity, UUID uuid) {
        entity.setId(uuid);
    }

    public Question saveQuestionWithOptions(Question question, List<String> optionContents) {
        // Сначала сохраняем вопрос, чтобы получить ID
        Question savedQuestion = questionRepository.save(question);

        // Затем сохраняем варианты с установленной связью
        for (String content : optionContents) {
            Options option = new Options(content);
            option.setQuest(savedQuestion);
            optionsRepository.save(option);
        }

        return savedQuestion;
    }
}
