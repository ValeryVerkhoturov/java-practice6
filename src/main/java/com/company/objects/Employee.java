package com.company.objects;

import java.util.Date;
import java.util.Optional;

public record Employee(String firstName,
                       String pathronymic,
                       String lastName,
                       Date birthDate,
                       String city,
                       String position,
                       Optional<Task> task){}
