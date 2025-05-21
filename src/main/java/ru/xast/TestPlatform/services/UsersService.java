package ru.xast.TestPlatform.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.TestPlatform.models.Users;
import ru.xast.TestPlatform.repositories.UsersRepository;
import ru.xast.TestPlatform.security.UsersDetails;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class UsersService extends CRUDOperationService<Users, UUID> implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    protected JpaRepository<Users, UUID> getRepository() {
        return usersRepository;
    }

    @Override
    protected UUID getId(Users entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Users entity, UUID uuid) {
        entity.setId(uuid);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findByUsername(username);

        if(users.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        return new UsersDetails(users.get());
    }

    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
