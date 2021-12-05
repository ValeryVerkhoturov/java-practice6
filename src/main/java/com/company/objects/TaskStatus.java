package com.company.objects;

import lombok.Getter;

public enum TaskStatus {
    WAITING("Ожидание"),
    IN_PROGRESS("В процессе"),
    IS_COMPLETED("Завершено");

    @Getter
    private final String name;

    TaskStatus(String name){
        this.name = name;
    }
}
