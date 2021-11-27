package com.company;

import com.company.objects.Factory;
import lombok.SneakyThrows;

import java.util.stream.IntStream;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
//        System.out.println(Resources.getRandomMaleFirstName());
//        System.out.println(Resources.getRandomMalePatronymic());
//        System.out.println(Resources.getRandomMaleLastName());
//        System.out.println(Resources.getRandomFemaleFirstName());
//        System.out.println(Resources.getRandomFemalePatronymic());
//        System.out.println(Resources.getRandomFemaleLastName());
//        System.out.println(Resources.getRandomCity());
//        System.out.println(Resources.getRandomPosition());
//        System.out.println(Resources.getRandomTask());
        IntStream.range(0, 10).forEach(i -> System.out.println(Factory.getRandomEmployee()));
    }
}
