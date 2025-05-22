package ru.xast.TestPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xast.TestPlatform.models.TestResult;

import java.util.Optional;
import java.util.UUID;

public interface TestResultRepository extends JpaRepository<TestResult, UUID> {
    Optional<Object> findByPersonIdAndTestId(UUID id, UUID id1);
}
