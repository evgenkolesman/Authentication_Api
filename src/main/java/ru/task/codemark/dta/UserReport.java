package ru.task.codemark.dta;


import ru.task.codemark.model.User;

/**
 * Класс для отчета
 * в необходимой форме,
 * с возможностью получить объект типа User
 * и получить необходимые нам поля
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */


public class UserReport {

    private String login;

    private String name;

    private String password;

    public UserReport() {
    }

    public static UserReport of(User user) {
        UserReport report = new UserReport();
        report.login = user.getLogin();
        report.name = user.getName();
        report.password = user.getPassword();
        return report;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserReport{");
        sb.append("login='").append(login).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
