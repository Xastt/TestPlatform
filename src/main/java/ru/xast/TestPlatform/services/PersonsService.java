package ru.xast.TestPlatform.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.xast.TestPlatform.models.Persons;
import ru.xast.TestPlatform.repositories.PersonsRepository;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class PersonsService extends CRUDOperationService<Persons, UUID>{

    private final PersonsRepository personsRepository;

    @Autowired
    public PersonsService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    @Override
    protected JpaRepository<Persons, UUID> getRepository() {
        return personsRepository;
    }

    @Override
    protected UUID getId(Persons entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Persons entity, UUID uuid) {
        entity.setId(uuid);
    }
}
