package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.persist.ContactTypeEntity;

import java.util.Optional;

/**
 * @author Zverev Artem
 */


public interface ContactTypeRepository extends JpaRepository<ContactTypeEntity, Long> {

    Optional<ContactTypeEntity> findFirstByNameEquals(String name);
}