package com.company.objects;

import lombok.Getter;

import java.io.Serializable;

public enum TaskStatus implements Serializable {
    NOT_EXISTS("Не существует"),
    WAITING("Ждет выполнения"),
    IN_PROGRESS("В процессе"),
    IS_COMPLETED("Завершена");

    @Getter
    private final String name;

    TaskStatus(String name) {
        this.name = name;
    }
}
