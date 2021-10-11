package ru.task.codemark.service;

import org.springframework.stereotype.Service;
import ru.task.codemark.model.Role;
import ru.task.codemark.repository.RoleRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

/**
 * Сервис для работы с Role репозиторием
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

@Service
public class RoleService {

    private final RoleRepository roles;

    public RoleService(RoleRepository roles) {
        this.roles = roles;
    }

    public List<Role> findAllRole() {
        return new LinkedList<>(stream(
                this.roles.findAll().spliterator(), false
        ).collect(Collectors.toList()));
    }

    public Role findById(Long id) {
        return roles.findById(id).get();
    }

    public Role saveRole(Role role) {
        return roles.save(role);
    }

    public void deleteRole(Role role) {
        roles.delete(role);
    }
}
