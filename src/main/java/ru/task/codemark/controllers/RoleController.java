package ru.task.codemark.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.task.codemark.model.Role;
import ru.task.codemark.service.RoleService;

/**
 * Дополнительный контроллер функционала приложения
 * обращаемся по дефолтному адресу
 * http://localhost:8080/role/
 *
 * Исполняет GET, POST, DELETE запросы
 * в ТЗ данных о внесении ролей в БД не было,
 * поэтому добавил реализацию на свое усмотрение
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

@RestController
@RequestMapping("/role/")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String findAll() {
        return service.findAllRole().toString();
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return new ResponseEntity<>(this.service.saveRole(role), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> delete (@PathVariable Long id) {
        this.service.deleteRole(service.findById(id));
        return ResponseEntity.ok().build();
    }
}
