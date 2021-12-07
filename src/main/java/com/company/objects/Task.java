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

    public Employee getEmployee() {
        if (employee == null)
            return NullEmployee.getInstance();
        return employee;
    }
}
