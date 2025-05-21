package ru.xast.TestPlatform.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
public abstract class CRUDOperationService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public void save(T entity) {
        try {
            getRepository().save(entity);
            log.info("Entity saved, id: {}", getId(entity));
        } catch (Exception e) {
            log.error("Error saving entity: {}", e.getMessage());
            throw new RuntimeException("Failed to save entity: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public T findOne(ID id) {
        try {
            Optional<T> entity = getRepository().findById(id);
            if (entity.isPresent()) {
                log.info("Entity found, id: {}", id);
                return entity.get();
            } else {
                log.warn("Entity not found, id: {}", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error finding entity with id: {}, {}", id, e.getMessage());
            throw new RuntimeException("Failed to find entity with id: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        try {
            List<T> entities = getRepository().findAll();
            log.info("Found {} entities", entities.size());
            return entities;
        } catch (Exception e) {
            log.error("Error finding all entities", e);
            throw new RuntimeException("Failed to find all entities", e);
        }
    }

    public void update(ID id, T updatedEntity) {
        try {
            setId(updatedEntity, id);
            getRepository().save(updatedEntity);
            log.info("Entity updated successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Error updating entity with id: {}", id, e);
            throw new RuntimeException("Failed to update entity", e);
        }
    }

    public void delete(ID id) {
        try {
            getRepository().deleteById(id);
            log.info("Entity deleted successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Error deleting entity with id: {}", id, e);
            throw new RuntimeException("Failed to delete entity", e);
        }
    }

    protected abstract ID getId(T entity);

    protected abstract void setId(T entity, ID id);
}

