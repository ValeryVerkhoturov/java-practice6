package com.company.view;

import com.company.Resources;
import com.company.objects.Company;
import com.company.objects.Factory;
import com.company.objects.TaskStatus;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TerminalGame implements Runnable{

    private static String separator = Resources.getProperty("separator");

    private final Company company = Factory.getRandomCompany();

    @Override
    public void run() {
        try{
            System.out.println(separator);
            setup();
            do{
                System.out.println(separator);
                System.out.println("\n\n\n\n\n\n\n\n");
            } while (loop());
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
    private boolean loop(){
        printCompanyStatistics();
        new Scanner(System.in).nextLine();
        return true;
    }

    @SneakyThrows
    private void printCompanyStatistics(){
        @Cleanup FileReader fileReader = new FileReader(Resources.getProperty("companyStatisticsPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        System.out.printf(bufferedReader.lines().collect(Collectors.joining("\n")),
                company.name(),
                company.employees().size(),
                company.tasks().size(),
                company.tasks().stream().filter(task -> task.status() == TaskStatus.WAITING).count(),
                company.tasks().stream().filter(task -> task.status() == TaskStatus.IN_PROGRESS).count(),
                company.tasks().stream().filter(task -> task.status() == TaskStatus.IS_COMPLETED).count()
        );
    }

    private void finish(){
    }
}
