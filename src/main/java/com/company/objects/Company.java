package com.company.objects;

import java.time.Period;
import java.util.List;

public record Company(String name, List<Employee> employees, List<Task> tasks){
    public void passTime(Period period){
    }
}
