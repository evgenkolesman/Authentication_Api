package ru.task.codemark.util;

/**
 * Класс для валидации нашего пароля
 * согласно заданию
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

public class Validate {
    public static boolean validate(String password) {
        return password.codePoints().filter(Character::isDigit)
                .findFirst().isPresent() && password.codePoints()
                .filter(Character::isUpperCase)
                .findFirst().isPresent();
    }
}
