package com.company.objects;

import com.company.Resources;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

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
                    Resources.getRandomMaleFirstName(),
                    Resources.getRandomMalePatronymic(),
                    Resources.getRandomMaleLastName(),
                    getRandomDate(),
                    Resources.getRandomPosition(),
                    new ArrayList<>());

        return new Employee(
                Resources.getRandomFemaleFirstName(),
                Resources.getRandomFemalePatronymic(),
                Resources.getRandomFemaleLastName(),
                getRandomDate(),
                Resources.getRandomPosition(),
                new ArrayList<>());
    }

    private Date getRandomTimeLimit(Date now){
        long delta = ThreadLocalRandom.current().nextLong(
                Integer.parseInt(Resources.getProperty("minDaysForTask")),
                Integer.parseInt(Resources.getProperty("maxDaysForTask"))) * 24 * 60 * 60 * 1000;
        return new Date(now.getTime() + delta);
    }

    private int getRandomPrice(){
        return ThreadLocalRandom.current().nextInt(
                Integer.parseInt(Resources.getProperty("minTaskPrice")),
                Integer.parseInt(Resources.getProperty("maxTaskPrice")));
    }

    public Task getRandomTask(Date now){
        long delta = ThreadLocalRandom.current().nextLong(1, 30) * 24 * 60 * 60 * 1000;
        return new Task(Resources.getRandomTask(), now, getRandomTimeLimit(now), getRandomPrice());
    }
}
