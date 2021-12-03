package com.company.view;

import com.company.Resources;
import com.company.objects.Company;
import com.company.objects.Factory;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TerminalGame implements Runnable{

    private static String separator = Resources.getProperty("separator");

    private final Date date = new Date();

    private final Company company = Factory.getRandomCompany(date);

    @Override
    public void run() {
        try{
            System.out.println(separator);
            setup();
            System.out.println(separator);
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

    private boolean loop(){
        return true;
    }

    private void finish(){
    }
}
