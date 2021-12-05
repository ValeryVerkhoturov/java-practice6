package com.company.objects;

import lombok.Getter;

import java.io.Serializable;

public enum TaskStatus implements Serializable {
    WAITING("Ожидание"),
    IN_PROGRESS("В процессе"),
    IS_COMPLETED("Завершено");

    @Getter
    private final String name;

    TaskStatus(String name){
        this.name = name;
    }
}
