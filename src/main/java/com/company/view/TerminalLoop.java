package com.company.view;

import com.company.Resources;
import com.company.objects.*;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

public class TerminalLoop implements Runnable {

    private Company company = Factory.getRandomCompany();

    private final Logger logger = Logger.getLogger(TerminalLoop.class.getName());

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
        logger.setUseParentHandlers(false);

        SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
        FileHandler fileHandler = new FileHandler(Resources.getProperty("logPath") + format.format(Calendar.getInstance().getTime()) + ".log");
        fileHandler.setFormatter(new SimpleFormatter());

        logger.addHandler(fileHandler);
    }

    @SneakyThrows
    private void start() {
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
        logger.info(terminalCommand.name());
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
            logger.info("Введена команда: " + code);
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

        employeeBuilder.firstName(TerminalReader.readFirstName(logger));
        employeeBuilder.patronymic(TerminalReader.readPatronymic(logger));
        employeeBuilder.lastName(TerminalReader.readLastName(logger));
        employeeBuilder.birthDate(TerminalReader.readBirthDate(logger));
        employeeBuilder.city(TerminalReader.readCity(logger));
        employeeBuilder.position(TerminalReader.readPosition(logger));
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

    @SneakyThrows
    private synchronized void saveProgress() {
        File file = new File(Resources.getProperty("savePath"));
        while (!file.createNewFile())
            if (!file.delete()) {
                logger.severe("Не удалось сохранить прогресс.");
                System.out.println("Не удалось сохранить прогресс.");
                return;
            }

        @Cleanup FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        @Cleanup ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(company);
        objectOutputStream.flush();
        logger.info("Сохранение прошло успешно.");
        System.out.println("Сохранение прошло успешно.");
    }

    @SneakyThrows
    private synchronized void loadProgress() {
        File file = new File(Resources.getProperty("savePath"));
        if (!file.exists()){
            logger.info("Сохранение не существует.");
            System.out.println("Сохранение не существует.");
            return;
        }

        @Cleanup FileInputStream fileInputStream  = new FileInputStream(file);
        @Cleanup ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        company = (Company) objectInputStream.readObject();
        logger.info("Загрузка прошла успешно.");
        System.out.println("Загрузка прошла успешно.");
    }

    private void unknownCommand(){
        logger.info("Неизвестная команда");
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
        logger.info("Выход из цикла с командами");
    }
}
