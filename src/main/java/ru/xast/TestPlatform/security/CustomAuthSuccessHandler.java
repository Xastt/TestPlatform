package ru.xast.TestPlatform.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.xast.TestPlatform.models.Persons;
import ru.xast.TestPlatform.models.Users;
import ru.xast.TestPlatform.repositories.PersonsRepository;
import ru.xast.TestPlatform.repositories.UsersRepository;

import java.io.IOException;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UsersRepository usersRepository;
    private final PersonsRepository persInfRepository;

    @Autowired
    public CustomAuthSuccessHandler( UsersRepository usersRepository, PersonsRepository persInfRepository) {
        this.usersRepository = usersRepository;
        this.persInfRepository = persInfRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        Users user = usersRepository.findByUsername(username).orElse(null);
        if(user == null) {
            response.sendRedirect("auth/login");
        }
        Persons persInf = persInfRepository.findPersInfByUserId(user.getId());
        if(persInf != null) {
            response.sendRedirect("/person");
        }else{
            response.sendRedirect("/person/new");
        }

    }
}
