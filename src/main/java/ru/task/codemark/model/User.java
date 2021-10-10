package ru.task.codemark.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Getter
    @NotNull
    private String login;

    @NotNull
    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @NotNull
    @Getter
    @Setter
    @Column(name = "password")
    private String password;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name="roles_users",
            joinColumns = {@JoinColumn(name="users_login")}, inverseJoinColumns = {@JoinColumn(name="roles_id")})
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<Role> roles;

    public static User of(String name, String password, String login) {
        User user = new User();
        user.login = login;
        user.name = name;
        user.password = password;
        return user;
    }

//    public static User of(String name, String password, String login, Set<Role> roles) {
//        User user = new User();
//        user.login = login;
//        user.name = name;
//        user.password = password;
//        user.roles = roles;
//        return user;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("login='").append(login).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", role=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
