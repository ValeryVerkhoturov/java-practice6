package com.company.view;

import lombok.Getter;

public enum TerminalCommand {

    UNKNOWN(-2),
    START(-1),
    EXIT(0),

    PASS_1_DAY(1),
    PASS_15_DAYS(2),
    PASS_30_DAYS(3),

    ADD_NEW_EMPLOYEE_FROM_CONSOLE(4),
    ADD_NEW_RANDOM_EMPLOYEE(5),
    ADD_NEW_RANDOM_TASK(6),

    SHOW_EMLOYEE_LIST(7),
    SHOW_UNCOMPLETED_TASKS(8),
    SHOW_COMPLETED_TASKS(9),

    SHOW_TOP_3_EFFECTIVE_EMPLOYEES(10),
    SHOW_TASK_WITH_TOP_PRICE(11),

    SAVE_PROGRESS(12),
    LOAD_PROGRESS(13);

    @Getter
    private final int code;

    TerminalCommand(int code) {
        this.code = code;
    }
}
