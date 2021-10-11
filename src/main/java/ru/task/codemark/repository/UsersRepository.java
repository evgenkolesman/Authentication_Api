package ru.task.codemark.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.task.codemark.model.User;

import java.util.List;

/**
 * User репозиторием
 * для работы с таблицей users
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

public interface UsersRepository extends CrudRepository<User, String> {

    User findByLogin(String login);

}
