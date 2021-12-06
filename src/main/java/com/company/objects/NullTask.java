package com.company.objects;

import java.time.Period;

public class NullTask extends Task{

    private static NullTask instance;

    private NullTask() {
        super("No task",
                Period.of(0, 0, 0),
                0,
                TaskStatus.NOT_EXISTS);
    }

    public static NullTask getInstance(){
        if (instance == null)
            instance = new NullTask();
        return instance;
    }
}
