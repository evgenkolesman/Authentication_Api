package ru.task.codemark.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.task.codemark.model.User;
import ru.task.codemark.service.UserService;
import ru.task.codemark.util.exceptions.Result;

import static org.springframework.http.ResponseEntity.ok;
import static ru.task.codemark.util.Validate.validate;
import static ru.task.codemark.util.exceptions.ExceptionName.*;

/**
 * Основной контроллер функционала приложения
 * обращаемся по дефолтному адресу
 * http://localhost:8080/user/
 *
 * Исполняет GET, PUT, POST, DELETE запросы
 * в зависимости от запроса свой функционал,
 * логика вынесена в контроллер с целью наиболее
 * простой передачи ответов из ТЗ
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {

    private final UserService service;

    @GetMapping("/")
    public String findAll() {
        return service.findAllUsers().toString();
    }

    @GetMapping("/{login}")
    public String findByLogin(@PathVariable String login) {
        return service.findByLogin(login).toString();
    }

    @PostMapping("/")
    public String create(@RequestBody User user) {
        final Result result;
        if (service.findByLogin(user.getLogin()) == null) {
            if (validate(user.getPassword())) {
                service.saveUser(user);
                result = new Result(SUCCESS);
            } else {
                result = new Result(FAILPASSWORD);
            }
        } else {
            result = new Result(NOTUNIQUELOGIN);
        }

        return result.sendMessage();
    }

    @PutMapping("/")
    public String update(@RequestBody User user) {
        final Result result;
        if (service.findByLogin(user.getLogin()) != null) {
            if (validate(user.getPassword())) {
                service.saveUser(user);
                result = new Result(SUCCESS);
            } else {
                result = new Result(FAILPASSWORD);
            }
        } else result = new Result(NOLOGIN);

        return result.sendMessage();
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<User> delete(@PathVariable String login) {
        User user = service.findByLogin(login);
        if (user != null) {
            service.deleteUser(user);
            return ok().build();
        } else return ResponseEntity.notFound().build();
    }

}

