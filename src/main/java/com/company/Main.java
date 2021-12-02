package com.company;

import com.company.objects.Factory;
import com.company.view.Terminal;
import lombok.CustomLog;
import lombok.SneakyThrows;

import java.util.stream.IntStream;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        new Terminal().run();
    }
}
