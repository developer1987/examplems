package ru.diasoft.micro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.persist.UserEntity;

/**
 * @author Zverev Artem
 */


public interface UserRepository extends JpaRepository<UserEntity, Long>{
}
