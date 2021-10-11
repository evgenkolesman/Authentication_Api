package ru.task.codemark.service;

import org.springframework.stereotype.Service;
import ru.task.codemark.model.User;
import ru.task.codemark.model.UserReport;
import ru.task.codemark.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

/**
 * Сервис для работы с User репозиторием
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

@Service
public class UserService {

    private static UsersRepository users;


    public UserService(UsersRepository users) {
        this.users = users;
    }

    public List<UserReport> findAllUsers() {
        List<User> list = stream(
                this.users.findAll().spliterator(), false
        ).collect(Collectors.toList());
        return list.stream().map(UserReport::of).collect(Collectors.toList());

    }

    public User findByLogin(String login) {
        return users.findByLogin(login);
    }

    public User saveUser(User user) {
        return users.save(user);
    }

    public User deleteUser(User user) {
        users.delete(user);
        return user;
    }

}
