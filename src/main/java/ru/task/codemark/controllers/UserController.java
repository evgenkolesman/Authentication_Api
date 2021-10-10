package ru.task.codemark.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.task.codemark.model.User;
import ru.task.codemark.service.CommonService;
import ru.task.codemark.util.exeptions.Result;

import static ru.task.codemark.util.Validate.validate;

@RestController
@RequestMapping("/user/")
public class UserController {

    private final CommonService service;

    public UserController(CommonService service) {
        this.service = service;
    }


    @GetMapping("/")
    public ResponseEntity<User> findAll() {
        return new ResponseEntity<User>(
                service.findAllUsers().isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }

    @GetMapping("/{login}")
    public ResponseEntity<User> findByLogin(@PathVariable String login) {
        return new ResponseEntity<User>(
                service.findByLogin(login) != null ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public Result create(@RequestBody User user) {
        final Result result;
        if (service.findByLogin(user.getLogin()) == null) {
            if (validate(user.getPassword())) {
                service.saveUser(user);
                result = new Result("{success: true}");
            } else {
                result = new Result(String.format("success: false, errors: {Wrong password. " +
                        "You should use minimum a digit and a capital letter!}"));
            }
        } else {
            result = new Result(String.format("{success: false , errors:" +
                    " { Wrong login. Please change it! }}"));
        }

        return result;
    }

    @PutMapping("/")
    public Result update(@RequestBody User user) {
        final Result result;
        if (service.findByLogin(user.getLogin()) != null) {
            if (validate(user.getPassword())) {
                service.saveUser(user);
                result = new Result("{success: true}");
            } else {
                result = new Result(String.format("success: false, errors: {Wrong password. " +
                        "You should use minimum a digit and a capital letter!}"));
            }
        } else result = new Result(String.format("success: false, errors:" +
                " {Wrong login. Please insert it!}"));

        return result;
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> delete(@PathVariable String login) {
        User user = service.findByLogin(login);
        if (user != null) {
            service.deleteUser(user);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
    }

}

