package com.company;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Resources {

    Properties properties = new Properties();

    List<String> maleFirstNames;

    List<String> malePatronymics;

    List<String> maleLastNames;

    List<String> femaleFirstNames;

    List<String> femalePatronymics;

    List<String> femaleLastNames;

    List<String> cities;

    List<String> positions;

    List<String> tasks;

    static {
        try {
            @Cleanup
            FileReader fileReader = new FileReader("src/main/resources/conf.properties");
            properties.load(fileReader);

            readMaleFirstNameResources();
            readMalePatronymicResources();
            readMaleLastNameResources();

            readFemaleFirstNameResources();
            readFemalePatronymicResources();
            readFemaleLastNameResources();

            readCitiesResources();
            readPositionsResources();
            readTasksResources();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    @SneakyThrows
    private void readMaleFirstNameResources(){
        @Cleanup FileReader fileReader = new FileReader(properties.getProperty("maleFirstNameResourcesPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        maleFirstNames = bufferedReader.lines().toList();
    }

    @SneakyThrows
    private void readMalePatronymicResources(){
        @Cleanup FileReader fileReader = new FileReader(properties.getProperty("malePatronymicResourcesPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        malePatronymics = bufferedReader.lines().toList();
    }

    @SneakyThrows
    private void readMaleLastNameResources(){
        @Cleanup FileReader fileReader = new FileReader(properties.getProperty("maleLastNameResourcesPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        maleLastNames = bufferedReader.lines().toList();
    }

    @SneakyThrows
    private void readFemaleFirstNameResources(){
        @Cleanup FileReader fileReader = new FileReader(properties.getProperty("femaleFirstNameResourcesPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        femaleFirstNames = bufferedReader.lines().toList();
    }

    @SneakyThrows
    private void readFemalePatronymicResources(){
        @Cleanup FileReader fileReader = new FileReader(properties.getProperty("femalePatronymicResourcesPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        femalePatronymics = bufferedReader.lines().toList();
    }

    @SneakyThrows
    private void readFemaleLastNameResources(){
        @Cleanup FileReader fileReader = new FileReader(properties.getProperty("femaleLastNameResourcesPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        femaleLastNames = bufferedReader.lines().toList();
    }

    @SneakyThrows
    private void readCitiesResources(){
        @Cleanup FileReader fileReader = new FileReader(properties.getProperty("citiesResourcesPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        cities = bufferedReader.lines().toList();
    }

    @SneakyThrows
    private void readPositionsResources(){
        @Cleanup FileReader fileReader = new FileReader(properties.getProperty("positionsResourcesPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        positions = bufferedReader.lines().toList();
    }

    @SneakyThrows
    private void readTasksResources(){
        @Cleanup FileReader fileReader = new FileReader(properties.getProperty("tasksResourcesPath"));
        @Cleanup BufferedReader bufferedReader = new BufferedReader(fileReader);
        tasks = bufferedReader.lines().toList();
    }
    
    @SneakyThrows
    public String getRandomMaleFirstName(){
        return maleFirstNames.get(ThreadLocalRandom.current().nextInt(0, maleFirstNames.size()));
    }

    @SneakyThrows
    public String getRandomMalePatronymic(){
        return malePatronymics.get(ThreadLocalRandom.current().nextInt(0, malePatronymics.size()));
    }

    @SneakyThrows
    public String getRandomMaleLastName(){
        return maleLastNames.get(ThreadLocalRandom.current().nextInt(0, maleLastNames.size()));
    }

    @SneakyThrows
    public String getRandomFemaleFirstName(){
        return femaleFirstNames.get(ThreadLocalRandom.current().nextInt(0, femaleFirstNames.size()));
    }

    @SneakyThrows
    public String getRandomFemalePatronymic(){
        return femalePatronymics.get(ThreadLocalRandom.current().nextInt(0, femalePatronymics.size()));
    }

    @SneakyThrows
    public String getRandomFemaleLastName(){
        return femaleLastNames.get(ThreadLocalRandom.current().nextInt(0, femaleLastNames.size()));
    }

    @SneakyThrows
    public String getRandomCity(){
        return cities.get(ThreadLocalRandom.current().nextInt(0, cities.size()));
    }

    @SneakyThrows
    public String getRandomPosition(){
        return positions.get(ThreadLocalRandom.current().nextInt(0, positions.size()));
    }

    @SneakyThrows
    public String getRandomTask(){
        return tasks.get(ThreadLocalRandom.current().nextInt(0, tasks.size()));
    }
}
