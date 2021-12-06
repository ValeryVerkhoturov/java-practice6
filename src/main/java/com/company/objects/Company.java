package com.company.objects;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.time.Period;
import java.util.List;

@Data
public class Company implements Serializable {

    @NonNull
    String name;

    @NonNull
    List<Employee> employees;

    @NonNull
    List<Task> tasks;
}
