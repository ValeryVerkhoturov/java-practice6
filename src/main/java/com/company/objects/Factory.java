package com.company.objects;

import com.company.Constants;
import com.company.Resources;
import lombok.experimental.UtilityClass;

import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@UtilityClass
public class Factory {

    private Date getRandomDate(){
        return new Date(ThreadLocalRandom.current().nextLong(
                Long.parseLong(Resources.getProperty("earlyBirthDateInMilliseconds")),
                Long.parseLong(Resources.getProperty("lateBirthDateInMilliseconds"))));
    }

    public Employee getRandomEmployee(){
        if (ThreadLocalRandom.current().nextBoolean())
            return new Employee(
                    Resources.getRandomMaleFirstName(), Resources.getRandomMalePatronymic(),
                    Resources.getRandomMaleLastName(), getRandomDate(),
                    Resources.getRandomCity(), Resources.getRandomPosition(),
                    Optional.empty());

        return new Employee(
                Resources.getRandomFemaleFirstName(), Resources.getRandomFemalePatronymic(),
                Resources.getRandomFemaleLastName(), getRandomDate(),
                Resources.getRandomCity(), Resources.getRandomPosition(),
                Optional.empty());
    }

    public List<Employee> getRandomEmployees(){
    return IntStream
            .range(0, ThreadLocalRandom.current().nextInt(
                    Constants.MIN_EMPLOYEE_AMMOUNT.getPoints(),
                    Constants.MAX_EMPLOEE_AMMOUNT.getPoints()))
            .mapToObj(i -> getRandomEmployee())
            .toList();
    }

    private int getRandomPrice(){
        return ThreadLocalRandom.current().nextInt(
                Constants.MIN_TASK_PRICE.getPoints(),
                Constants.MAX_TASK_PRICE.getPoints());
    }

    private Period getRandomPeriod(){
        return Period.ofDays(ThreadLocalRandom.current().nextInt(
                Constants.MIN_DAYS_FOR_TASK.getPoints(),
                Constants.MAX_DAYS_FOR_TASK.getPoints()));
    }

    public Task getRandomTask(){
        return new Task(Resources.getRandomTask(), getRandomPeriod(),
                getRandomPrice(), TaskStatus.WAITING, Optional.empty());
    }

    public List<Task> getRandomTasks(){
        return IntStream
                .range(0, ThreadLocalRandom.current().nextInt(
                        Constants.MIN_TASK_AMMOUNT.getPoints(),
                        Constants.MAX_TASK_AMMOUNT.getPoints()))
                .mapToObj(i -> getRandomTask())
                .toList();
    }

    public Company getRandomCompany(){
        return new Company(Resources.getRandomCompanyName(), getRandomEmployees(), getRandomTasks());
    }
}
