package com.company.objects;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.Period;

@Data
@FieldNameConstants
public class Task implements Serializable {

    @NonNull
    String description;

    @NonNull
    Period period;

    @NonNull
    int price;

    @NonNull
    TaskStatus status;

    Employee employee;

    public Employee getEmployee(){
        if (employee == null)
            return NullEmployee.getInstance();
        return employee;
    }
}
//public record Task(String description, Period period, int price,
//                   TaskStatus status, Employee employee) implements Serializable {
//
//    public static class FieldNames{
//        public final static String description = "Описание";
//        public final static String period = "Время выполнения";
//        public final static String price = "Вознаграждение";
//        public final static String status = "Статус";
//        public final static String employee = "Прикрепленный работник";
//    }
//}
