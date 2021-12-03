package com.company.objects;

import com.company.Resources;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                    new ArrayList<>());

        return new Employee(
                Resources.getRandomFemaleFirstName(), Resources.getRandomFemalePatronymic(),
                Resources.getRandomFemaleLastName(), getRandomDate(),
                Resources.getRandomCity(), Resources.getRandomPosition(),
                new ArrayList<>());
    }

    public List<Employee> getRandomEmployees(){
        return IntStream
                .range(Integer.parseInt(Resources.getProperty("minEmployeesRandomAmmount")),
                        Integer.parseInt(Resources.getProperty("maxEmployeesRandomAmmount")))
                .mapToObj(i -> getRandomEmployee())
                .toList();
    }

    private Date getRandomTimeLimit(Date startDate){
        long delta = ThreadLocalRandom.current().nextLong(
                Integer.parseInt(Resources.getProperty("minDaysForTask")),
                Integer.parseInt(Resources.getProperty("maxDaysForTask"))) * 24 * 60 * 60 * 1000;
        return new Date(startDate.getTime() + delta);
    }

    private int getRandomPrice(){
        return ThreadLocalRandom.current().nextInt(
                Integer.parseInt(Resources.getProperty("minTaskPrice")),
                Integer.parseInt(Resources.getProperty("maxTaskPrice")));
    }

    public Task getRandomTask(Date startDate){
        return new Task(Resources.getRandomTask(), startDate, getRandomTimeLimit(startDate), getRandomPrice(), false);
    }

    public List<Task> getRandomTasks(Date startDate){
        return IntStream
                .range(Integer.parseInt(Resources.getProperty("minTasksRandomAmmount")),
                        Integer.parseInt(Resources.getProperty("maxTasksRandomAmmount")))
                .mapToObj(i -> getRandomTask(startDate))
                .toList();
    }

    public Company getRandomCompany(Date startDate){
        return new Company(Resources.getRandomCompanyName(), getRandomEmployees(), getRandomTasks(startDate));
    }
}
