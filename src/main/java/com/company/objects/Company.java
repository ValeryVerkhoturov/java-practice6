package com.company.objects;

import java.util.List;
import java.util.Queue;

public record Company(List<Employee> employees, Queue<Task> tasks){}
