package ru.xast.TestPlatform.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.TestPlatform.models.Question;
import ru.xast.TestPlatform.repositories.QuestionRepository;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class QuestionService extends CRUDOperationService<Question, UUID> {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
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
}
