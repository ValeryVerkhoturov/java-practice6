package com.company.view;

import com.company.objects.Employee;
import com.company.objects.NullEmployee;
import com.company.objects.NullTask;
import com.company.objects.Task;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.List;

@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CuteTable {

    int SPACE_EMPLOYEE_FIRST_NAME = 10;
    int SPACE_EMPLOYEE_PATRONYMIC = 15;
    int SPACE_EMPLOYEE_LAST_NAME = 15;
    int SPACE_EMPLOYEE_BIRTH_DATE = 14;
    int SPACE_EMPLOYEE_CITY = 15;
    int SPACE_EMPLOYEE_POSITION = 40;
    int SPACE_EMPLOYEE_TASK = 40;

    int SPACE_TASK_DESCRIPTION = 50;
    int SPACE_TASK_PERIOD = 7;
    int SPACE_TASK_PRICE = 6;
    int SPACE_TASK_STATUS = 10;
    int SAPCE_TASK_EMPLOYEE = 15;


    String FORMAT_EMPLOYEE_FIRST_NAME = "%-" + SPACE_EMPLOYEE_FIRST_NAME + "s";
    String FORMAT_EMPLOYEE_PATRONYMIC = "%-" + SPACE_EMPLOYEE_PATRONYMIC + "s";
    String FORMAT_EMPLOYEE_LAST_NAME = "%-" + SPACE_EMPLOYEE_LAST_NAME + "s";
    String FORMAT_EMPLOYEE_BIRTH_DATE = "%-" + SPACE_EMPLOYEE_BIRTH_DATE + "s";
    String FORMAT_EMPLOYEE_CITY = "%-" + SPACE_EMPLOYEE_CITY + "s";
    String FORMAT_EMPLOYEE_POSITION = "%-" + SPACE_EMPLOYEE_POSITION + "s";
    String FORMAT_EMPLOYEE_TASK = "%-" + SPACE_EMPLOYEE_TASK + "s";

    String FORMAT_TASK_DESCRIPTION = "%-" + SPACE_TASK_DESCRIPTION + "s";
    String FORMAT_TASK_PERIOD = "%-" + SPACE_TASK_PERIOD + "s";
    String FORMAT_TASK_PRICE = "%-" + SPACE_TASK_PRICE + "s";
    String FORMAT_TASK_STATUS = "%-" + SPACE_TASK_STATUS + "s";
    String FORMAT_TASK_EMPLOYEE = "%-" + SAPCE_TASK_EMPLOYEE + "s";


    @SneakyThrows
    public void printfEmployeeTable(List<Employee> employees){
        final String format = "%s%s%s%s%s%s%s".formatted(
                FORMAT_EMPLOYEE_FIRST_NAME, FORMAT_EMPLOYEE_PATRONYMIC, FORMAT_EMPLOYEE_LAST_NAME,
                FORMAT_EMPLOYEE_BIRTH_DATE, FORMAT_EMPLOYEE_CITY, FORMAT_EMPLOYEE_POSITION,
                FORMAT_EMPLOYEE_TASK);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        printfEmployeeTableHead(format);
        printfEmployeeTableTail(employees, format, dateFormat);
    }

    private void printfEmployeeTableHead(String format){
        System.out.printf((format) + "\n",
                Employee.Fields.firstName, Employee.Fields.patronymic, Employee.Fields.lastName,
                Employee.Fields.birthDate, Employee.Fields.city, Employee.Fields.position,
                Employee.Fields.task);
    }

    private void printfEmployeeTableTail(List<Employee> employees, String format, SimpleDateFormat dateFormat){
        for (Employee employee : employees) {
            String task;
            if (employee.getTask() instanceof NullTask)
                task = "Задания нет";
            else
                task = employee.getTask().getDescription();

            System.out.printf(format + "\n",
                    cutLongOutput(employee.getFirstName(), SPACE_EMPLOYEE_FIRST_NAME),
                    cutLongOutput(employee.getPatronymic(), SPACE_EMPLOYEE_PATRONYMIC),
                    cutLongOutput(employee.getLastName(), SPACE_EMPLOYEE_LAST_NAME),
                    cutLongOutput(dateFormat.format(employee.getBirthDate()), SPACE_EMPLOYEE_BIRTH_DATE),
                    cutLongOutput(employee.getCity(), SPACE_EMPLOYEE_CITY),
                    cutLongOutput(employee.getPosition(), SPACE_EMPLOYEE_POSITION),
                    cutLongOutput(task, SPACE_EMPLOYEE_TASK));
        }
    }

    public void printfTaskTable(List<Task> tasks){
        final String format = "%s%s%s%s%s".formatted(
                FORMAT_TASK_DESCRIPTION, FORMAT_TASK_PERIOD, FORMAT_TASK_PRICE,
                FORMAT_TASK_STATUS, FORMAT_TASK_EMPLOYEE);

       printfTaskTableHead(format);
       printfTaskTableTail(tasks, format);
    }

    private void printfTaskTableHead(String format){
        System.out.printf((format) + "\n",
                Task.Fields.description, Task.Fields.period, Task.Fields.price,
                Task.Fields.status, Task.Fields.employee);
    }

    private void printfTaskTableTail(List<Task> tasks, String format){
        for (Task task : tasks) {
            String employee;
            if (task.getEmployee() instanceof NullEmployee)
                employee = "Не назначен";
            else
                employee = task.getEmployee().getLastName() + " " +
                        task.getEmployee().getFirstName().charAt(0) + ".";

            System.out.printf(format + "\n",
                    cutLongOutput(task.getDescription(), SPACE_TASK_DESCRIPTION),
                    cutLongOutput(task.getPeriod().getDays() + " д.", SPACE_TASK_PERIOD),
                    task.getPrice(),
                    cutLongOutput(task.getStatus().name(), SPACE_TASK_STATUS),
                    cutLongOutput(employee, SAPCE_TASK_EMPLOYEE)
            );
        }
    }

    private String cutLongOutput(String source, int space){
        if (source.length() <= space)
            return source;
        return source.substring(0, space - 3) + "..";
    }
}
