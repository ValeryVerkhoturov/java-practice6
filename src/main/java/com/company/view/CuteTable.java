package com.company.view;

import com.company.objects.Employee;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

@UtilityClass
public class CuteTable {

    @SneakyThrows
    public void printfEmployeeTable(List<Employee> employees){
        final String format = "%-10s%-10s%-10s%-10s%-10s%-30s%-30s\n";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf(format,
                Employee.Fields.firstName, Employee.Fields.patronymic, Employee.Fields.lastName, Employee.Fields.birthDate,
                Employee.Fields.city, Employee.Fields.position, Employee.Fields.task);
        for (Employee employee : employees) {
            String task;
            if (employee.task().isEmpty())
                task = "Задания нет";
            else
                task = employee.task().get().description();
            System.out.printf(format,
                    employee.firstName(), employee.patronymic(), employee.lastName(),
                    dateFormat.parse(dateFormat.format(employee.birthDate())),
                    employee.city(), employee.position(), task);
        }
    }
}
