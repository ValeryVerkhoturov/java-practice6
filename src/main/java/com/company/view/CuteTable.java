package com.company.view;

import com.company.objects.Employee;
import com.company.objects.NullEmployee;
import com.company.objects.NullTask;
import com.company.objects.Task;
import lombok.AccessLevel;
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
    int SPACE_TASK_STATUS = 16;
    int SPACE_TASK_EMPLOYEE = 15;

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
    String FORMAT_TASK_EMPLOYEE = "%-" + SPACE_TASK_EMPLOYEE + "s";

    String FORMAT_EMPLOYEE_TABLE = "%s%s%s%s%s%s%s".formatted(
            FORMAT_EMPLOYEE_FIRST_NAME, FORMAT_EMPLOYEE_PATRONYMIC, FORMAT_EMPLOYEE_LAST_NAME,
            FORMAT_EMPLOYEE_BIRTH_DATE, FORMAT_EMPLOYEE_CITY, FORMAT_EMPLOYEE_POSITION,
            FORMAT_EMPLOYEE_TASK);
    String FORMAT_TASK_TABLE = "%s%s%s%s%s".formatted(
            FORMAT_TASK_DESCRIPTION, FORMAT_TASK_PERIOD, FORMAT_TASK_PRICE,
            FORMAT_TASK_STATUS, FORMAT_TASK_EMPLOYEE);

    SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yyyy");

    public void printfEmployeeTable(List<Employee> employees) {
        printfEmployeeTableHead();
        employees.forEach(CuteTable::printfEmployeeTableRow);
    }

    public void printfEmployeeTable(Employee employee) {
        printfEmployeeTableHead();
        printfEmployeeTableRow(employee);
    }

    private void printfEmployeeTableHead() {
        System.out.printf(FORMAT_EMPLOYEE_TABLE + "\n",
                Employee.Fields.firstName, Employee.Fields.patronymic, Employee.Fields.lastName,
                Employee.Fields.birthDate, Employee.Fields.city, Employee.Fields.position,
                Employee.Fields.task);
    }

    private void printfEmployeeTableRow(Employee employee) {
        String task;
        if (employee.getTask() instanceof NullTask)
            task = "Задачи нет";
        else
            task = employee.getTask().getDescription();

        System.out.printf(FORMAT_EMPLOYEE_TABLE + "\n",
                cutLongOutput(employee.getFirstName(), SPACE_EMPLOYEE_FIRST_NAME),
                cutLongOutput(employee.getPatronymic(), SPACE_EMPLOYEE_PATRONYMIC),
                cutLongOutput(employee.getLastName(), SPACE_EMPLOYEE_LAST_NAME),
                cutLongOutput(FORMAT_DATE.format(employee.getBirthDate()), SPACE_EMPLOYEE_BIRTH_DATE),
                cutLongOutput(employee.getCity(), SPACE_EMPLOYEE_CITY),
                cutLongOutput(employee.getPosition(), SPACE_EMPLOYEE_POSITION),
                cutLongOutput(task, SPACE_EMPLOYEE_TASK));
    }

    public void printfTaskTable(List<Task> tasks) {
       printfTaskTableHead();
       tasks.forEach(CuteTable::printfTaskTableRow);
    }

    public void printfTaskTable(Task task) {
        printfTaskTableHead();
        printfTaskTableRow(task);
    }

    private void printfTaskTableHead() {
        System.out.printf(FORMAT_TASK_TABLE + "\n",
                Task.Fields.description, Task.Fields.period, Task.Fields.price,
                Task.Fields.status, Task.Fields.employee);
    }

    private void printfTaskTableRow(Task task) {
        String employee;
        if (task.getEmployee() instanceof NullEmployee)
            employee = "Не назначен";
        else
            employee = task.getEmployee().getLastName() + " " +
                    task.getEmployee().getFirstName().charAt(0) + ".";

        System.out.printf(FORMAT_TASK_TABLE + "\n",
                cutLongOutput(task.getDescription(), SPACE_TASK_DESCRIPTION),
                cutLongOutput(task.getPeriod().getDays() + " д.", SPACE_TASK_PERIOD),
                task.getPrice(),
                cutLongOutput(task.getStatus().getName(), SPACE_TASK_STATUS),
                cutLongOutput(employee, SPACE_TASK_EMPLOYEE)
        );
    }

    private String cutLongOutput(String source, int space) {
        if (source.length() <= space)
            return source;
        return source.substring(0, space - 3) + "..";
    }
}
