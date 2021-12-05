package com.company.objects;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.Period;
import java.util.Optional;

public record Task(String description, Period period, int price,
                   TaskStatus status, Optional<Employee> employee){

    public static class FieldNames{
        public final static String description = "Описание";
        public final static String period = "Время выполнения";
        public final static String price = "Вознаграждение";
        public final static String status = "Статус";
        public final static String employee = "Прикрепленный работник";
    }
}
