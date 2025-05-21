package ru.xast.TestPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xast.TestPlatform.models.Options;

import java.util.UUID;

@Repository
public interface OptionsRepository extends JpaRepository<Options, UUID> {
}
