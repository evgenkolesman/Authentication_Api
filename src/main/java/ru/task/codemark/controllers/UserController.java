package ru.task.codemark.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.task.codemark.model.User;
import ru.task.codemark.service.CommonService;

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
                service.findByLogin(login) == null ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody User user) {
        if (service.findByLogin(user.getLogin()) == null) {
            return new ResponseEntity<User>(
                    service.saveUser(user) == user ?
                            HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        else return ResponseEntity.badRequest().build();
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody User user) {
        if (service.findByLogin(user.getLogin()) != null) {
            service.saveUser(user);
            return ResponseEntity.ok().build();
        }
        else return ResponseEntity.notFound().build();
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
