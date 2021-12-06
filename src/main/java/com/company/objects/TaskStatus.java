package com.company.objects;

import java.io.Serializable;

public enum TaskStatus implements Serializable {
    NOT_EXISTS(),
    WAITING(),
    IN_PROGRESS(),
    IS_COMPLETED()
}
