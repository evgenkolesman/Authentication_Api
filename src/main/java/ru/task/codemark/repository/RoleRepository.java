package ru.task.codemark.repository;

import org.springframework.data.repository.CrudRepository;
import ru.task.codemark.model.Role;

/**
 *  Role репозиторий для работы с таблицей
 *  roles
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

public interface RoleRepository extends CrudRepository<Role, Long> {
}
