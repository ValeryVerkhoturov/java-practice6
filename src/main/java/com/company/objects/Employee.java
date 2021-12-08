package com.company.objects;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Data
@Builder
@FieldNameConstants
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

    @EqualsAndHashCode.Exclude
    Task task;

    public Task getTask(){
        return Optional.ofNullable(task).orElse(NullTask.getInstance());
    }
}
