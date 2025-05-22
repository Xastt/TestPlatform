package ru.xast.TestPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xast.TestPlatform.models.Test;

import java.util.Optional;
import java.util.UUID;

public interface TestRepository extends JpaRepository<Test, UUID> {

    Optional<Object> findWithQuestionsAndOptionsById(UUID testId);
}
