package ru.task.codemark.repository;

import org.springframework.data.repository.CrudRepository;
import ru.task.codemark.model.User;


public interface UsersRepository extends CrudRepository<User, String> {

    User findByLogin(String login);

}
