package ru.task.codemark.util.exceptions;

/**
 *
 * Класс необходим для отправки сообщений
 * нашим пользователям с ошибками валидации
 *
 * @author Evgeniy Kolesnikov
 * telegram 89616927595
 * email evgeniysanich@mail.ru
 */

public class Result {
    private final ExceptionName value;

    public Result(ExceptionName value) {
        this.value = value;
    }

    public ExceptionName getValue() {
        return value;
    }

    public String sendMessage() {
        return value.getMessage();
    }
}
