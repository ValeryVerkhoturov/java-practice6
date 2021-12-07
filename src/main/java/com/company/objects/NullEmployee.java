package com.company.objects;

import java.util.Date;

public class NullEmployee extends Employee{

    private static NullEmployee instance;

    private NullEmployee() {
        super("No name", "No patronymic", "No last name", new Date(),
                "No city", "No position", NullTask.getInstance());
    }

    public static NullEmployee getInstance(){
        if (instance == null)
            instance = new NullEmployee();
        return instance;
    }
}
