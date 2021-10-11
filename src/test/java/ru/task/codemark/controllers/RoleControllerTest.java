package ru.task.codemark.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.task.codemark.CodemarkApplication;
import ru.task.codemark.model.Role;
import ru.task.codemark.repository.RoleRepository;
import ru.task.codemark.service.RoleService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Unit - тестирование с помощью библиотеки Mockito основного функционала
 * контроллера RoleController
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

@SpringBootTest(classes = CodemarkApplication.class)
@AutoConfigureMockMvc
class RoleControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    RoleRepository roles;
    @MockBean
    RoleService service;

    @Test
    void findAllRolesTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var role = Role.of("ADMIN");
        when(service.findAllRole()).thenReturn(List.of(role));
        String req = mapper.writer().writeValueAsString(role);
        this.mvc.perform(get("/role/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }


    @Test
    void createRoleTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var role = Role.of("ADMIN");
        when(service.saveRole(role)).thenReturn(role);
        String req = mapper.writer().writeValueAsString(role);
        this.mvc.perform(post("/role/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void deleteRoleTest() throws Exception {
        this.mvc.perform(delete("/role/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}