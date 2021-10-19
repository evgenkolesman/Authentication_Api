package ru.task.codemark.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.task.codemark.CodemarkApplication;
import ru.task.codemark.model.Role;
import ru.task.codemark.model.User;
import ru.task.codemark.dta.UserReport;
import ru.task.codemark.repository.RoleRepository;
import ru.task.codemark.repository.UsersRepository;
import ru.task.codemark.service.UserService;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.task.codemark.util.exceptions.ExceptionName.*;

/**
 * Unit - тестирование с помощью библиотеки Mockito основного функционала
 * контроллера UserController,
 * согласно ТЗ нам необходимо:
 * "Для функционала добавления, редактирования и удаления пользователя должны быть написаны unit тесты"
 *
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

@SpringBootTest(classes = CodemarkApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    UsersRepository repo;
    @MockBean
    RoleRepository roles;
    @MockBean
    UserService service;

        @Test
    void findAllTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var user = new User("Vasya", "V2", "p1", Set.of(Role.of("A")));
        var user1 = new User("Vasya1", "V1", "p11", Set.of(Role.of("A")));

        when(service.findAllUsers()).thenReturn(List.of(UserReport.of(user),UserReport.of(user1)));
        String req = mapper.writer().writeValueAsString(List.of(user, user1));
        this.mvc.perform(get("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(content().string(containsString("Vasya")))
                .andExpect(content().string(containsString("Vasya1")));

    }


    @Test
    void findByLoginTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var user = User.of("Vasya", "V2", "p1");

        when(service.findByLogin(user.getLogin())).thenReturn(user);
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(get("/user/p1").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(content().string(containsString("Vasya")));
    }

    @Test
    void createTestCorrectPassword() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Set<Role> set = Set.of(Role.of(1L, "user"), Role.of(2L, "admin"));
        var user = new User("Vasya", "Vasya1", "P1", set);
        when(service.saveUser(new User())).thenReturn(user);

        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(post("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(SUCCESS.getMessage())));

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(service).saveUser(argument.capture());
        assertThat(argument.getValue().getLogin(), is("Vasya"));
        assertThat(argument.getValue().getName(), is("Vasya1"));
    }

    @Test
    void createTestCorrectPasswordWithNullRole() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var user = new User("Vasya", "Vasya1", "P1", null);
        String req = mapper.writer().writeValueAsString(user);

        this.mvc.perform(post("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(SUCCESS.getMessage())));

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(service).saveUser(argument.capture());
        assertThat(argument.getValue().getLogin(), is("Vasya"));
        assertThat(argument.getValue().getName(), is("Vasya1"));
    }

    @Test
    void createTestDoubleUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var user = new User("Vasya", "Vasya1", "P1", Set.of(new Role()));
        String req = mapper.writer().writeValueAsString(user);
        when(service.findByLogin(user.getLogin())).thenReturn(user);
        when(service.saveUser(new User())).thenReturn(user);
        this.mvc.perform(post("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(content().string(containsString(NOTUNIQUELOGIN.getMessage())));
    }

    @Test
    void createTestIncorrectPassword() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Set<Role> set = Set.of(Role.of(1L, "user"), Role.of(2L, "admin"));
        var user = new User("Vasya", "Vasya1", "p1", set);
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(post("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(content().string(containsString(FAILPASSWORD.getMessage())));
    }

    @Test
    void updateTestCorrect() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Set<Role> set = Set.of(Role.of(1L, "user"), Role.of(2L, "admin"));
        var user = new User("Vasya", "Vasya1", "P1", set);
        when(service.findByLogin(user.getLogin())).thenReturn(user);
        when(service.saveUser(new User())).thenReturn(user);
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(put("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(SUCCESS.getMessage())));
    }

    @Test
    void updateTestNoUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Set<Role> set = Set.of(Role.of(1L, "user"), Role.of(2L, "admin"));
        var user = new User("Vasya", "Vasya1", "P1", set);
        when(service.saveUser(new User())).thenReturn(null);
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(put("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(NOLOGIN.getMessage())));
    }

    @Test
    void deleteUserTestWhenNotFound() throws Exception {
        this.mvc.perform(delete("/user/a"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUserTestWhenAllRight() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var user = new User("a", "Vasya1", "P1", null);
        when(service.findByLogin(user.getLogin())).thenReturn(user);
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(delete("/user/a").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().isOk());
    }

}