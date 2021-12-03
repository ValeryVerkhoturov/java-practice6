package com.company.objects;

import java.util.Date;
import java.util.List;

public record Employee(String firstName,
                       String pathronymic,
                       String lastName,
                       Date birthDate,
                       String city,
                       String position,
                       List<Task> tasks){}
