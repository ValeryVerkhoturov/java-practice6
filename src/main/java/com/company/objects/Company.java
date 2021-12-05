package com.company.objects;

import java.io.Serializable;
import java.time.Period;
import java.util.List;

public record Company(String name, List<Employee> employees, List<Task> tasks) implements Serializable {
    public void passTime(Period period){
    }
}
