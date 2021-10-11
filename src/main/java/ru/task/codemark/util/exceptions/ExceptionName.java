package ru.task.codemark.util.exceptions;

/**
 * Перечисление ошибок
 * которые получаются при валидации элементов
 * в операциях create и update
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

public enum ExceptionName {

    SUCCESS("success: true"),

    FAILPASSWORD("success: false, errors:  Wrong password. " +
            "You should use minimum a digit and a capital letter! "),

    NOTUNIQUELOGIN("success: false , errors: " +
            "Wrong login. Please change it! "),

    NOLOGIN("success: false, errors: " +
            "Wrong login. Please register or insert it!");


    private final String message;

    ExceptionName(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
