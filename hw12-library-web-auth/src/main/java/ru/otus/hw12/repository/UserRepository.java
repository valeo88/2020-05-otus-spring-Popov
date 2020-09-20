package ru.otus.hw12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw12.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
