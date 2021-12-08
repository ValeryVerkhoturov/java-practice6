package com.company.view;

import com.company.objects.Employee;
import lombok.CustomLog;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

@UtilityClass
public class TerminalReader {

    private static final Scanner scanner = new Scanner(System.in);

    public String readFirstName(Logger logger){
        System.out.print(Employee.Fields.firstName + "> ");
        String firstName = scanner.nextLine();
        logger.info(Employee.Fields.firstName + "> " + firstName);
        return firstName;
    }

    public String readPatronymic(Logger logger){
        System.out.print(Employee.Fields.patronymic + "> ");
        String patronymic = scanner.nextLine();
        logger.info(Employee.Fields.patronymic + "> " + patronymic);
        return patronymic;
    }

    public String readLastName(Logger logger){
        System.out.print(Employee.Fields.lastName + "> ");
        String lastName = scanner.nextLine();
        logger.info(Employee.Fields.lastName + "> " + lastName);
        return lastName;
    }

    public Date readBirthDate(Logger logger){
        Date birthDate = null;
        String date = null;
        while (birthDate == null)
            try{
                System.out.println("Пример даты: ДД MM ГГГГ");
                System.out.print(Employee.Fields.birthDate + "> ");
                date = scanner.nextLine();
                birthDate = new SimpleDateFormat("dd MM yyyy").parse(date);
            } catch (ParseException e){
                logger.severe(Employee.Fields.birthDate + "> " + date);
            }
        logger.info(Employee.Fields.birthDate + "> " + date);
        return birthDate;
    }

    public String readCity(Logger logger){
        System.out.print(Employee.Fields.city + "> ");
        String city = scanner.nextLine();
        logger.info(Employee.Fields.city + "> " + city);
        return city;
    }

    public String readPosition(Logger logger){
        System.out.print(Employee.Fields.position + "> ");
        String position = scanner.nextLine();
        logger.info(Employee.Fields.position + "> " + position);
        return position;
    }
}
