package com.company.objects;

import java.util.List;

public record Company(String name, List<Employee> employees, List<Task> tasks){}
