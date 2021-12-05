package com.company.objects;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.util.Date;
import java.util.Optional;

public record Employee(String firstName, String patronymic, String lastName, Date birthDate,
                       String city, String position, Optional<Task> task){

    public static class FieldNames {
        public final static String firstName = "Имя";
        public final static String patronymic = "Отчество";
        public final static String lastName = "Фамилия";
        public final static String birthDate = "Дата роджения";
        public final static String city = "Город";
        public final static String position = "Должность";
        public final static String task = "Задача";
    }
}
