package ru.xast.TestPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xast.TestPlatform.models.Persons;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonsRepository extends JpaRepository<Persons, UUID> {
    Persons findPersInfByUserId(UUID userId);

    Optional<Persons> findByUserId(UUID userId);
}
