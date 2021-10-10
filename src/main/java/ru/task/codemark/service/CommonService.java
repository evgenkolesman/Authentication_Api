package ru.task.codemark.service;

import org.springframework.stereotype.Service;
import ru.task.codemark.model.Role;
import ru.task.codemark.model.User;
import ru.task.codemark.repository.RoleRepository;
import ru.task.codemark.repository.UsersRepository;
import ru.task.codemark.util.Validate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

@Service
public class CommonService {

    private final UsersRepository users;
    private final RoleRepository roles;

    public CommonService(UsersRepository users, RoleRepository roles) {
        this.users = users;
        this.roles = roles;
    }

    public List<User> findAllUsers() {
        return stream(
                this.users.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    public User findByLogin(String login) {
        return users.findByLogin(login);
    }

    public User saveUser(User user) {
           return users.save(user);
    }

    public void deleteUser(User user) {
        users.delete(user);
    }

    public List<Role> findAllRole() {
        return stream(
                this.roles.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }
}
