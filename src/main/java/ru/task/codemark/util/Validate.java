package ru.task.codemark.util;

public class Validate {
    public static boolean validate(String name) {
        return name.codePoints().filter(Character::isDigit)
                .findFirst().isPresent() && name.codePoints()
                .filter(Character::isUpperCase)
                .findFirst().isPresent();
    }
}
