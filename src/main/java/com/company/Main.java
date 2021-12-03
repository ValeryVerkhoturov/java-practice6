package com.company;

import com.company.view.TerminalGame;
import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        new TerminalGame().run();
    }
}
