package ru.diasoft.micro.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.diasoft.micro.querydsl.DefaultQuerydslBinderCustomizer;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.micro.model.*;


@Repository
public interface TemplateRepository extends JpaRepository<Template, Long>, QuerydslPredicateExecutor<Template>,
        DefaultQuerydslBinderCustomizer<QTemplate> {
}