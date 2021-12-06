package com.company.objects;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Data
public class Company implements Serializable {

    @NonNull
    String name;

    @NonNull
    List<Employee> employees;

    @NonNull
    List<Task> tasks;

    public void addEmployee(Employee employee){
        if (!(employees instanceof ArrayList))
            employees = new ArrayList<>(employees);
        employees.add(employee);
    }

    public void addTask(Task task){
        if (!(tasks instanceof ArrayList))
            tasks = new ArrayList<>(tasks);
        tasks.add(task);
    }

    public void passNDays(int days){
        IntStream.range(0, days).forEach(i -> passOneDay());
    }

    private void passOneDay(){
        removeCompletedTasks();
        addTaskToFreeEmployee();
        minusTaskPeriod();
    }

    private void removeCompletedTasks(){
        employees.stream().filter(employee -> !(employee.getTask() instanceof NullTask))
                .filter(employee -> employee.getTask().getPeriod().getDays() == 0).forEach(
                        employee -> {
                            Task task = employee.getTask();
                            task.setStatus(TaskStatus.IS_COMPLETED);
                            employee.setTask(NullTask.getInstance());
                        }
                );
    }

    private void addTaskToFreeEmployee(){
        employees.stream().filter(employee -> employee.getTask() instanceof NullTask).forEach(
                employee -> {
                    Task task = tasks.stream()
                            .filter(t -> t.getStatus() == TaskStatus.WAITING)
                            .findFirst().orElse(NullTask.getInstance());
                    task.setStatus(TaskStatus.IN_PROGRESS);
                    employee.setTask(task);
                }
        );
    }

    private void minusTaskPeriod(){
        tasks.stream().filter(task -> !(task instanceof NullTask))
                .filter(task -> task.getStatus() != TaskStatus.IS_COMPLETED)
                .forEach(task -> task.setPeriod(task.getPeriod().minusDays(1)));
    }
}
