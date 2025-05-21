package ru.xast.TestPlatform.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.TestPlatform.models.Options;
import ru.xast.TestPlatform.repositories.OptionsRepository;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class OptionsService extends CRUDOperationService<Options, UUID> {

    private final OptionsRepository optionsRepository;

    @Autowired
    public OptionsService(OptionsRepository optionsRepository) {
        this.optionsRepository = optionsRepository;
    }

    @Override
    protected JpaRepository<Options, UUID> getRepository() {
        return optionsRepository;
    }

    @Override
    protected UUID getId(Options entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Options entity, UUID uuid) {
        entity.setId(uuid);
    }
}
