package ru.task.codemark.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.task.codemark.CodemarkApplication;
import ru.task.codemark.model.Role;
import ru.task.codemark.model.User;
import ru.task.codemark.repository.RoleRepository;
import ru.task.codemark.repository.UsersRepository;
import ru.task.codemark.service.CommonService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    CommonService service;

    @Test
    void findAllTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var user = User.of("Vasya", "V2", "p1");
        var user1 = User.of("Vasya1", "V1", "p11");
        when(service.findAllUsers()).thenReturn(List.of(user, user1));
        String req = mapper.writer().writeValueAsString(List.of(user, user1));
        this.mvc.perform(get("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    void findByLoginTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var user = User.of("Vasya", "V2", "p1");
        when(service.findByLogin(user.getLogin())).thenReturn(user);
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(get("/user/p1").contentType("application/json")
                        .content(req).param("login", "p1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void createTestCorrectPassword() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Set<Role> set = Set.of(Role.of(1L, "user"), Role.of(2L, "admin"));
        var user = new User("Vasya", "Vasya1" , "P1", set);
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(post("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(service).saveUser(argument.capture());
        assertThat(argument.getValue().getLogin(), is("Vasya"));
        assertThat(argument.getValue().getName(), is("Vasya1"));
    }

    @Test
    void createTestCorrectPasswordWithNull() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        var user = new User("Vasya", "Vasya1" , "P1", null);
        when(service.findAllRole()).thenReturn(Collections.singletonList(Role.of("U")));
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(post("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(service).saveUser(argument.capture());
        assertThat(argument.getValue().getLogin(), is("Vasya"));
        assertThat(argument.getValue().getName(), is("Vasya1"));
    }

    @Test
    void createTestIncorrectPassword() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Set<Role> set = Set.of(Role.of(1L, "user"), Role.of(2L, "admin"));
        var user = new User("Vasya", "Vasya1" , "p1", set);
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(post("/user/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void updateTestCorrect() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Set<Role> set = Set.of(Role.of(1L, "user"), Role.of(2L, "admin"));
        var user = new User("Vasya", "Vasya1" , "P1", set);
        when(service.saveUser(new User())).thenReturn(user);
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(put("/user/").contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void updateTestNoUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Set<Role> set = Set.of(Role.of(1L, "user"), Role.of(2L, "admin"));
        var user = new User("Vasya", "Vasya1" , "P1", set);
        when(service.saveUser(new User())).thenReturn(null);
        String req = mapper.writer().writeValueAsString(user);
        this.mvc.perform(put("/user/").contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void deleteTest() throws Exception {
        Set<Role> set = Set.of(Role.of(1L, "user"), Role.of(2L, "admin"));
        var user = new User("a", "Vasya1" , "P1", set);

        this.mvc.perform(delete("/user/a"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}