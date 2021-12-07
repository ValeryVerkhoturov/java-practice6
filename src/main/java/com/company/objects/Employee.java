package com.company.objects;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

@Data
@FieldNameConstants
@Builder
public class Employee implements Serializable {

    @NonNull
    String firstName;

    @NonNull
    String patronymic;

    @NonNull
    String lastName;

    @NonNull
    Date birthDate;

    @NonNull
    String city;

    @NonNull
    String position;

    @NonNull
    Task task;
}
