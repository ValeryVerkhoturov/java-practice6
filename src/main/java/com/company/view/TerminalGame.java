package com.company.view;

import com.company.Resources;
import com.company.objects.Company;
import com.company.objects.Factory;
import com.company.objects.TaskStatus;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TerminalGame implements Runnable{

    private Company company = Factory.getRandomCompany();

    private static final String separator = Resources.getProperty("separator");

    private TerminalCommand terminalCommand = TerminalCommand.START;

    @Override
    public void run() {
        try{
            System.out.println(separator);
            setup();
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
    private void setup(){
        @Cleanup FileReader fileReader = new FileReader(Resources.getProperty("greetingsPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);

        System.out.printf(bufferedReader.lines().collect(Collectors.joining("\n")),
                Resources.getRandomMaleFirstName(), company.name());

        new Scanner(System.in).nextLine();
    }

    @SneakyThrows
    private TerminalCommand loop(){
        System.out.println(separator);
        executeCommand();
        if (terminalCommand != TerminalCommand.START)
            System.out.println(separator);

        printCompanyStatistics();
        System.out.println(separator);

        printCommands();

        return readTerminalCommand();
    }

    private void executeCommand(){
        switch (terminalCommand){
            case START -> {}

            case PASS_1_DAY -> pass1day();
            case PASS_15_DAYS -> pass15days();
            case PASS_30_DAYS -> pass30days();

            case ADD_NEW_EMPLOYEE_FROM_CONSOLE -> addNewEmployeeFromConsole();
            case ADD_NEW_RANDOM_EMPLOYEE -> addNewRandomEmployee();
            case ADD_NEW_RANDOM_TASK -> addNewRandomTask();

            case SHOW_EMLOYEE_LIST -> showEmployeeList();
            case SHOW_UNCOMPLETED_TASKS -> showUncompletedTasks();
            case SHOW_COMPLETED_TASKS -> showCompletedTasks();

            case SHOW_TOP_3_EFFECTIVE_EMPLOYEES -> showTop3EffectiveEmployees();
            case SHOW_TASK_WITH_TOP_PRICE -> showTaskWithTopPrice();

            case SAVE_PROGRESS -> saveProgress();
            case LOAD_PROGRESS -> loadProgress();

            default -> unknownCommand();
        }
    }

    private TerminalCommand readTerminalCommand(){
        try{
            System.out.print("> ");
            int code = new Scanner(System.in).nextInt();
            return Arrays.stream(TerminalCommand.values())
                    .filter(cmd -> cmd.getCode() == code)
                    .findFirst()
                    .orElse(TerminalCommand.UNKNOWN);
        } catch (InputMismatchException e){
            return TerminalCommand.UNKNOWN;
        }
    }

    private void pass1day() {
    }

    private void pass15days() {
    }

    private void pass30days() {
    }

    private void addNewEmployeeFromConsole() {
    }

    private void addNewRandomEmployee() {

    }

    private void addNewRandomTask() {
    }

    private void showEmployeeList() {
        CuteTable.printfEmployeeTable(company.employees());
    }

    private void showUncompletedTasks() {
        CuteTable.printfTaskTable(
                company.tasks().stream().filter(task -> task.status() != TaskStatus.IS_COMPLETED).toList());
    }

    private void showCompletedTasks() {
        CuteTable.printfTaskTable(
                company.tasks().stream().filter(task -> task.status() == TaskStatus.IS_COMPLETED).toList());
    }

    private void showTop3EffectiveEmployees() {
    }

    private void showTaskWithTopPrice() {

    }

    @SneakyThrows
    private synchronized void saveProgress() {
        File file = new File(Resources.getProperty("savePath"));
        if (file.exists())
            file.delete();
        file.createNewFile();
        @Cleanup FileOutputStream fileOutputStream = new FileOutputStream(file);
        @Cleanup ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(company);
        objectOutputStream.flush();
        System.out.println("Сохранение прошло успешно.");
    }

    @SneakyThrows
    private synchronized void loadProgress() {
        File file = new File(Resources.getProperty("savePath"));
        if (!file.exists()){
            System.out.println("Сохранение не существует.");
            return;
        }
        @Cleanup FileInputStream fileInputStream  = new FileInputStream(file);
        @Cleanup ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        company = (Company) objectInputStream.readObject();
        System.out.println("Загрузка прошла успешно.");
    }

    private void unknownCommand(){
        System.out.println("Сам понял, что написал?");
    }

    @SneakyThrows
    private synchronized void printCompanyStatistics(){
        @Cleanup FileReader fileReader = new FileReader(Resources.getProperty("companyStatisticsPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        System.out.printf(bufferedReader.lines().collect(Collectors.joining("\n")) + "\n",
                company.name(),
                company.employees().size(),
                company.tasks().size(),
                company.tasks().stream().filter(task -> task.status() == TaskStatus.WAITING).count(),
                company.tasks().stream().filter(task -> task.status() == TaskStatus.IN_PROGRESS).count(),
                company.tasks().stream().filter(task -> task.status() == TaskStatus.IS_COMPLETED).count()
        );
    }

    @SneakyThrows
    private synchronized void printCommands(){
        @Cleanup FileReader fileReader = new FileReader(Resources.getProperty("commandsPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        System.out.println(bufferedReader.lines().collect(Collectors.joining("\n")));
    }

    private void finish(){
    }
}
