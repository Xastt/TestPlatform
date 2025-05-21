package ru.xast.TestPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xast.TestPlatform.models.Users;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByUsername(String username);

}
