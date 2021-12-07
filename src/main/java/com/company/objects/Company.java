package com.company.objects;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.time.Duration;
import java.time.Period;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Data
public class Company implements Serializable {

    @NonNull
    String name;

    @NonNull
    List<Employee> employees;

    @NonNull
    List<Task> tasks;

    Map<Employee, List<Duration>> employeesEfficiency = new HashMap<>();

    public void addEmployee(Employee employee) {
        if (!(employees instanceof ArrayList))
            employees = new ArrayList<>(employees);
        employees.add(employee);
    }

    public void addTask(Task task) {
        if (!(tasks instanceof ArrayList))
            tasks = new ArrayList<>(tasks);
        tasks.add(task);
    }

    public void passNDays(int days) {
        IntStream.range(0, days).forEach(i -> passOneDay());
    }

    private void passOneDay() {
        removeCompletedTasks();
        addTaskToFreeEmployee();
        minusTaskPeriod();
        minusEfficiencyDuration();
    }

    public Map<Employee, Integer> getEmloyeesEfficiencyLastNDays(int days) {
        HashMap<Employee, Integer> efficiency = new HashMap<>();
        for (Employee employee : employeesEfficiency.keySet())
            efficiency.put(employee, (int) employeesEfficiency.get(employee).stream()
                    .filter(duration -> duration.compareTo(Duration.ofDays(days)) <= 0).count());
        return efficiency;
    }

    private void removeCompletedTasks() {
        employees.stream().filter(employee -> !(employee.getTask() instanceof NullTask))
                .filter(employee -> employee.getTask().getPeriod().getDays() <= 0).forEach(
                        employee -> {
                            Task task = employee.getTask();
                            task.setStatus(TaskStatus.IS_COMPLETED);
                            employee.setTask(NullTask.getInstance());

                            List<Duration> employeeEfficiency = employeesEfficiency.getOrDefault(employee, new ArrayList<>());
                            employeeEfficiency.add(Duration.ZERO);
                            employeesEfficiency.put(employee, employeeEfficiency);
                        }
                );
    }

    private void addTaskToFreeEmployee() {
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

    private void minusTaskPeriod() {
        tasks.stream().filter(task -> !(task instanceof NullTask))
                .filter(task -> task.getStatus() != TaskStatus.IS_COMPLETED)
                .forEach(task -> task.setPeriod(task.getPeriod().minusDays(1)));
    }

    private void minusEfficiencyDuration() {
        for (Employee employee : employeesEfficiency.keySet())
            employeesEfficiency.get(employee).forEach(duration -> {
                employeesEfficiency.get(employee).remove(duration);
                employeesEfficiency.get(employee).add(duration.minusDays(1));
            });
    }
}
