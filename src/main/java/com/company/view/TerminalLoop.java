package com.company.view;

import com.company.Resources;
import com.company.SaveLoadProgress;
import com.company.objects.*;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

@Log
public class TerminalLoop implements Runnable {

    private Company company = Factory.getRandomCompany();

    private static final String separator = Resources.getProperty("separator");

    private TerminalCommand terminalCommand = TerminalCommand.START;

    @Override
    public void run() {
        try{
            System.out.println(separator);
            setupLogger();
            start();
            do{
                System.out.println(separator);
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
                terminalCommand = loop();
            } while (terminalCommand != TerminalCommand.EXIT);
        }
        finally{
            finish();
        }
    }

    @SneakyThrows
    private void setupLogger(){
        log.setUseParentHandlers(false);

        SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
        if (new File(Resources.getProperty("logPath")).mkdirs())
            throw new Exception("Директория не создана");

        FileHandler fileHandler = new FileHandler(Resources.getProperty("logPath") + format.format(Calendar.getInstance().getTime()) + ".log");
        fileHandler.setFormatter(new SimpleFormatter());

        log.addHandler(fileHandler);
    }

    @SneakyThrows
    private synchronized void start() {
        @Cleanup FileReader fileReader = new FileReader(Resources.getProperty("greetingsPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);

        System.out.printf(bufferedReader.lines().collect(Collectors.joining("\n")),
                Resources.getRandomMaleFirstName(), company.getName());

        new Scanner(System.in).nextLine();
    }

    @SneakyThrows
    private TerminalCommand loop() {
        System.out.println(separator);
        executeCommand();
        if (terminalCommand != TerminalCommand.START)
            System.out.println(separator);

        printCompanyStatistics();
        System.out.println(separator);

        printCommands();

        return readTerminalCommand();
    }

    private void executeCommand() {
        log.info(terminalCommand.name());
        switch (terminalCommand){
            case START -> {}

            case PASS_1_DAY -> passNDays(1);
            case PASS_15_DAYS -> passNDays(15);
            case PASS_30_DAYS -> passNDays(30);

            case ADD_NEW_EMPLOYEE_FROM_CONSOLE -> addNewEmployeeFromConsole();
            case ADD_NEW_RANDOM_EMPLOYEE -> addNewRandomEmployee();
            case ADD_NEW_RANDOM_TASK -> addNewRandomTask();

            case SHOW_EMLOYEE_LIST -> showEmployeeList();
            case SHOW_UNCOMPLETED_TASKS -> showUncompletedTasks();
            case SHOW_COMPLETED_TASKS -> showCompletedTasks();

            case SHOW_TOP_3_EFFECTIVE_EMPLOYEES_LAST_30_DAYS -> showTopEffectiveEmployeesLastNDays(3, 30);
            case SHOW_TASK_WITH_TOP_PRICE -> showTaskWithTopPrice();

            case SAVE_PROGRESS -> saveProgress();
            case LOAD_PROGRESS -> loadProgress();

            default -> unknownCommand();
        }
    }

    private TerminalCommand readTerminalCommand() {
        try{
            System.out.print("> ");
            int code = new Scanner(System.in).nextInt();
            log.info("Введена команда: " + code);
            return Arrays.stream(TerminalCommand.values())
                    .filter(cmd -> cmd.getCode() == code)
                    .findFirst()
                    .orElse(TerminalCommand.UNKNOWN);
        } catch (InputMismatchException e){
            return TerminalCommand.UNKNOWN;
        }
    }

    private void passNDays(int days) {
        company.passNDays(days);
        System.out.println("Прошло дней: " + days);
    }

    private void addNewEmployeeFromConsole() {
        Employee.EmployeeBuilder employeeBuilder = Employee.builder();

        employeeBuilder.firstName(TerminalReader.readFirstName(log));
        employeeBuilder.patronymic(TerminalReader.readPatronymic(log));
        employeeBuilder.lastName(TerminalReader.readLastName(log));
        employeeBuilder.birthDate(TerminalReader.readBirthDate(log));
        employeeBuilder.city(TerminalReader.readCity(log));
        employeeBuilder.position(TerminalReader.readPosition(log));
        employeeBuilder.task(NullTask.getInstance());

        company.addEmployee(employeeBuilder.build());
    }

    private void addNewRandomEmployee() {
        System.out.println("Добавлен сотрудник со случайными полями.");
        company.addEmployee(Factory.getRandomEmployee());
    }

    private void addNewRandomTask() {
        System.out.println("Добавлена случайная задача.");
        company.addTask(Factory.getRandomTask());
    }

    private void showEmployeeList() {
        CuteTable.printfEmployeeTable(company.getEmployees());
    }

    private void showUncompletedTasks() {
        CuteTable.printfTaskTable(
                company.getTasks().stream().filter(task -> task.getStatus() != TaskStatus.IS_COMPLETED).toList());
    }

    private void showCompletedTasks() {
        CuteTable.printfTaskTable(
                company.getTasks().stream().filter(task -> task.getStatus() == TaskStatus.IS_COMPLETED).toList());
    }

    private void showTopEffectiveEmployeesLastNDays(int top, int days) {
        Map<Employee, Integer> efficiency = company.getEmloyeesEfficiencyLastNDays(days);
        CuteTable.printfEmployeeTable(
                efficiency.keySet().stream().sorted(Comparator.comparing(efficiency::get)).limit(top).toList());
    }

    private void showTaskWithTopPrice() {
        CuteTable.printfTaskTable(company.getTasks().stream().max(Comparator.comparing(Task::getPrice)).orElse(NullTask.getInstance()));
    }

    private void saveProgress() {
        SaveLoadProgress.saveProgress(company, log);
    }

    private void loadProgress() {
        SaveLoadProgress.loadProgress(log).ifPresent(c -> company = c);
    }

    private void unknownCommand(){
        log.severe("Неизвестная команда");
        System.out.println("Сам понял, что написал?");
    }

    @SneakyThrows
    private synchronized void printCompanyStatistics() {
        @Cleanup FileReader fileReader = new FileReader(Resources.getProperty("companyStatisticsPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        System.out.printf(bufferedReader.lines().collect(Collectors.joining("\n")) + "\n",
                company.getName(),
                company.getEmployees().size(),
                company.getTasks().size(),
                company.getTasks().stream().filter(task -> task.getStatus() == TaskStatus.WAITING).count(),
                company.getTasks().stream().filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS).count(),
                company.getTasks().stream().filter(task -> task.getStatus() == TaskStatus.IS_COMPLETED).count()
        );
    }

    @SneakyThrows
    private synchronized void printCommands() {
        @Cleanup FileReader fileReader = new FileReader(Resources.getProperty("commandsPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        System.out.println(bufferedReader.lines().collect(Collectors.joining("\n")));
    }

    private void finish() {
        log.info("Выход из цикла с командами");
    }
}
