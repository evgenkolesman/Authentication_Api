package ru.task.codemark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.task.codemark.model.User;
import ru.task.codemark.dta.UserReport;
import ru.task.codemark.repository.UsersRepository;

import java.util.LinkedList;
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
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository users;

    public List<UserReport> findAllUsers() {
        List<User> list = stream(
                this.users.findAll().spliterator(), false
        ).collect(Collectors.toList());
        return new LinkedList<>(list.stream().map(UserReport::of).collect(Collectors.toList()));

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
