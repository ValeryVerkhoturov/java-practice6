package com.company.objects;

import java.time.Period;
import java.util.Optional;

public record Task(String description,
                   Period period,
                   int price,
                   TaskStatus status,
                   Optional<Employee> employee){}
