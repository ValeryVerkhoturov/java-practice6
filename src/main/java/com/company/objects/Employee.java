package com.company.objects;

import lombok.experimental.FieldNameConstants;

import java.util.Date;
import java.util.Optional;

public record Employee(String firstName, String patronymic, String lastName, Date birthDate,
                       String city, String position, Optional<Task> task){

    public static final class Fields{
        public static final String firstName = "Имя";
        public static final String patronymic = "Отчество";
        public static final String lastName = "Фамилия";
        public static final String birthDate = "Дата роджения";
        public static final String city = "Город";
        public static final String position = "Должность";
        public static final String task = "Задача";
    }
}
