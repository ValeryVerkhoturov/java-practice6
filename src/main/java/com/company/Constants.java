package com.company;

import lombok.Getter;

public enum Constants {
    MIN_DAYS_FOR_TASK(1),
    MAX_DAYS_FOR_TASK(30),

    MIN_TASK_PRICE(100),
    MAX_TASK_PRICE(1000),

    MIN_EMPLOYEE_AMMOUNT(5),
    MAX_EMPLOEE_AMMOUNT(30),

    MIN_TASK_AMMOUNT(10),
    MAX_TASK_AMMOUNT(40);

    @Getter
    private final int points;

    Constants(int points){
        this.points = points;
    }
}
